package com.genomic.Fibersmart.util;

import com.genomic.Fibersmart.dto.GroupDTO;
import com.genomic.Fibersmart.dto.RoleDTO;
import com.genomic.Fibersmart.dto.UserDTO;
import com.genomic.Fibersmart.model.Group;
import com.genomic.Fibersmart.model.Role;
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
             user.getPassword(), user.getUserRoles(), user.getUserGroups(), user.getEnabled());
    }

    /**
     * Converts {@link Group} to {@link GroupDTO}.
     *
     * @param group {@link Group} object to convert
     * @return The {@link GroupDTO} object
     */
    public GroupDTO toGroupDTO(final Group group) {
        return new GroupDTO(group.getId(), group.getGroupName(), group.getActivated(), group.getGroupPermissions());
    }

    /**
     * Converts {@link Role} to {@link RoleDTO}.
     *
     * @param role {@link Role} object to convert
     * @return The {@link RoleDTO} object
     */
    public RoleDTO toRoleDTO(final Role role) {
        return new RoleDTO(role.getId(), role.getRoleType(), role.getPermissions());
    }
}
