package com.genomic.Fibersmart.service;

import com.genomic.Fibersmart.dto.UserDTO;
import com.genomic.Fibersmart.model.User;

import java.util.List;

/**
 * UserService interface.
 */
public interface IUserService {

    /**
     * Registers a new {@link User}.
     *
     * @param user the {@link User} to be registered
     * @return The {@link User} saved
     */
    User createUser(final User user);

    /**
     * Deletes the {@link User} associated with the given id.
     *
     * @param userId id of the {@link User} to be deleted
     */
    void deleteUser(final Long userId);

    /**
     * Retrieves all users.
     *
     * @return The list of all users.
     */
    List<User> getAllUser();

    /**
     * Updates the {@link User} associated with the given id.
     *
     * @return The {@link User} updated.
     */
    User updateUser(final User user);

}
