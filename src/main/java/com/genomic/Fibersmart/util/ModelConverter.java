package com.genomic.Fibersmart.util;

import com.genomic.Fibersmart.dto.UserDTO;
import com.genomic.Fibersmart.model.User;
import org.springframework.stereotype.Component;

/**
 * Allows the conversion of DTO class to Model class.
 */
@Component
public class ModelConverter {

    /**
     * Converts {@link UserDTO} to {@link User}.
     *
     * @param userDTO {@link UserDTO} object to convert
     * @return The {@link User} object
     */
    public User toUser(final UserDTO userDTO) {

        return new User(userDTO.getUsername(), userDTO.getLastName(), userDTO.getFirstName(), userDTO.getPassword(),
                userDTO.getUserRoles(), userDTO.getUserGroups());

    }
}
