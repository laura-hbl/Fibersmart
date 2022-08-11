package com.genomic.Fibersmart.security;

import com.genomic.Fibersmart.model.RolePermission;
import com.genomic.Fibersmart.model.User;
import com.genomic.Fibersmart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyUserDetailsService.class);
    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("Inside MyUserDetailsService.loadUserByUsername for username : " + username);

        User user = userRepository.findByUsername(username);

        if (user == null) {
            LOGGER.error("Request - FAILED: Invalid username or password");
            throw new UsernameNotFoundException("Invalid username or password");
        }

        List<SimpleGrantedAuthority> authorities = user.getUserRoles().stream()
                .flatMap(userRole -> userRole.getPermissions().stream())
                .map(RolePermission::getPermission)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
