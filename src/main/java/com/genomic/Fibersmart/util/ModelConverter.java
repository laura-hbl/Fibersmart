package com.genomic.Fibersmart.util;

import com.genomic.Fibersmart.dto.GroupDTO;
import com.genomic.Fibersmart.dto.RoleDTO;
import com.genomic.Fibersmart.dto.UserDTO;
import com.genomic.Fibersmart.model.Group;
import com.genomic.Fibersmart.model.Role;
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
                userDTO.getUserRoles(), userDTO.getUserGroups(), userDTO.getEnabled());

    }

    /**
     * Converts {@link GroupDTO} to {@link Group}.
     *
     * @param group {@link GroupDTO} object to convert
     * @return The {@link Group} object
     */
    public Group toGroup(final GroupDTO group) {
        return new Group(group.getId(), group.getGroupName(), group.getActivated(), group.getGroupPermissions());
    }

    /**
     * Converts {@link RoleDTO} to {@link Role}.
     *
     * @param role {@link RoleDTO} object to convert
     * @return The {@link Role} object
     */
    public Role toRole(final RoleDTO role) {
        return new Role(role.getId(), role.getRoleType(), role.getPermissions());
    }
}
