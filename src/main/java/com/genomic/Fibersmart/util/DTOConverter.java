package com.genomic.Fibersmart.util;

import com.genomic.Fibersmart.dto.UserDTO;
import com.genomic.Fibersmart.model.User;
import org.springframework.stereotype.Component;

/**
 * Allows the conversion of Model class to DTO class.
 */
@Component
public class DTOConverter {

    /**
     * Converts {@link User} to {@link UserDTO}.
     *
     * @param user {@link User} object to convert
     * @return The {@link UserDTO} object
     */
    public UserDTO toUserDTO(final User user) {

     return new UserDTO(user.getId(), user.getUsername() ,user.getLastName(), user.getFirstName(),
             user.getPassword(), user.getUserRoles(), user.getUserGroups());
    }
}
