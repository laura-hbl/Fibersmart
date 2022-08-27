package com.genomic.Fibersmart.service;

import com.genomic.Fibersmart.constants.AuthorityConstants;
import com.genomic.Fibersmart.dto.PasswordDTO;
import com.genomic.Fibersmart.exception.DataAlreadyRegisteredException;
import com.genomic.Fibersmart.exception.EntityDoesNotExistException;
import com.genomic.Fibersmart.exception.InvalidPasswordException;
import com.genomic.Fibersmart.model.RolePermission;
import com.genomic.Fibersmart.model.User;
import com.genomic.Fibersmart.model.Role;
import com.genomic.Fibersmart.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains methods that allow interaction between {@link User} business logic and user repository.
 */
@Service
public class UserService implements IUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final UserRepository userRepository, final BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Checks if username is associated with a registered {@link User}, if so DataAlreadyRegisteredException is thrown.
     * Else, encrypts the password and saves the user.
     *
     * @param userData the {@link User} to be registered
     * @return The {@link User} saved
     */
    @Override
    public User createUser(final User userData) {

        // the username must be unique for each user
        User userFound = userRepository.findByUsername(userData.getUsername());

        if (userFound != null) {
            throw new DataAlreadyRegisteredException("The username provided may be registered already");
        }

        List<Role> roles = userData.getUserRoles();
        userData.setUserRoles(roles);
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        User userSaved = userRepository.save(userData);

        return userSaved;
    }

    /**
     * Checks if the given id is associated with a registered {@link User}, if so user is deleted.
     * Else, ResourceNotFoundException is thrown.
     *
     * @param userId id of the {@link User} to be deleted
     */
    @Override
    public void deleteUser(final Long userId) {

        userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("No user registered with this id"));

        userRepository.deleteById(userId);
    }

    /**
     * Retrieves all users.
     */
    @Override
    public List<User> getAllUser() {

        return userRepository.findAll();
    }

    /**
     * Checks if the given id is associated with a registered {@link User}, if so user is updated.
     * Else, ResourceNotFoundException is thrown.
     *
     * @param user {@link User} to be updated
     * @return the updated {@link User}
     */
    @Override
    public User updateUser(final User user) {

        userRepository.findById(user.getId()).orElseThrow(() ->
                new ResourceNotFoundException("No user registered with this id"));

        return userRepository.save(user);

    }

    /**
     * Retrieves user permissions by Looping through all {@link RolePermission permissions} in all {@link Role userRoles}
     * and flattens it to a list containing the string representation of the enum.
     *
     * @param user {@link User} user whose permissions are being retrieved
     * @return list of {@code AuthorityConstants.Permission} in string format.
     */
    @Override
    public List<AuthorityConstants.Permission> getUserPermissions(final User user) {
        return user.getUserRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(RolePermission::getPermission)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves the logg in user.
     *
     * @return The connected {@link User}
     */
    @Override
    public User getLoggedInUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();

            return userRepository.findByUsername(username);
        }

        throw new EntityDoesNotExistException();
    }

    @Override
    public User changePassword(final Long userId, final PasswordDTO password) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("No user registered with this id"));

        if (!passwordEncoder.matches(password.getCurrentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("The password you entered is incorrect.");
        }

        if (!password.getNewPassword().equals(password.getConfirmNewPassword())) {
            throw new InvalidPasswordException("The new password and confirmation password does not match");
        }

        user.setPassword(passwordEncoder.encode(password.getNewPassword()));

        return userRepository.save(user);
    }
}
