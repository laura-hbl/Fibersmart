package com.genomic.Fibersmart.security;

import com.genomic.Fibersmart.model.RolePermission;
import com.genomic.Fibersmart.model.User;
import com.genomic.Fibersmart.model.UserRole;
import com.genomic.Fibersmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Constructs the {@link User} that is to be managed by spring. All {@link UserRole userRoles} and {@link RolePermission
 * rolePermissions} are loaded and the {@code permissions} are placed in the user granted authorities, which is used
 * internally by spring.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailsService.class);

    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * All permissions are placed in the user granted authorities, which is used internally by spring.
     *
     * @param permissions list of {@code RolePermission} in string format.
     * @return list of the user granted authorities
     */
    private List<SimpleGrantedAuthority> getGrantedAuthorities(final List<String> permissions) {
        return permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    /**
     * Loops through all {@link RolePermission permissions} in all {@link UserRole userRoles} and flattens it to a
     * list containing the string representation of the enum.
     *
     * @param roles list of {@link UserRole userRoles}.
     * @return list of {@code RolePermission} in string format.
     */
    private List<String> getRolesAndPermissions(final Collection<UserRole> roles) {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(RolePermission::getPermission)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the {@link User} associated with the given username, and if it exists, loads user details and wrap
     * it into a UserDetails object. Else, UsernameNotFoundException is thrown.
     *
     * @param username the user's username
     * @return UserDetails instance
     */
    @Override
    public UserDetails loadUserByUsername(final String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            LOGGER.error("Request - FAILED: Invalid username or password");
            throw new UsernameNotFoundException("Invalid username or password");
        }

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
                getGrantedAuthorities(getRolesAndPermissions(user.getUserRoles())));
    }
}
