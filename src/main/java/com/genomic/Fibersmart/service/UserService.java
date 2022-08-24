package com.genomic.Fibersmart.service;

import com.genomic.Fibersmart.exception.DataAlreadyRegisteredException;
import com.genomic.Fibersmart.model.User;
import com.genomic.Fibersmart.model.UserRole;
import com.genomic.Fibersmart.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

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
    public User createUser (final User userData) {

        // the username must be unique for each user
        User userFound = userRepository.findByUsername(userData.getUsername());

        if (userFound != null) {
            throw new DataAlreadyRegisteredException("The username provided may be registered already");
        }

        List<UserRole> userRoles = userData.getUserRoles();
        userData.setUserRoles(userRoles);
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

    @Override
    public User updateUser(final User user) {

        userRepository.findById(user.getId()).orElseThrow(() ->
                new ResourceNotFoundException("No user registered with this id"));

        return userRepository.save(user);

    }
}
