package com.genomic.Fibersmart.service;
import com.genomic.Fibersmart.model.Role;

import java.util.List;


/**
 * RoleService interface.
 */
public interface IRoleService {

    /**
     * Registers a new {@link com.genomic.Fibersmart.model.Role}.
     *
     * @param role the {@link com.genomic.Fibersmart.model.Role} to be registered
     * @return The {@link Role} saved
     */
    Role createRole(Role role);

    /**
     * Updates a {@link Role}.
     *
     * @return The updated {@link Role}
     */
    Role updateRole(Role roleToUpdate);

    /**
     * Deletes a {@linkRole}.
     *
     * @param roleId id of the {@link Role} to be deleted
     */
    void deleteRole(Long roleId);

    /**
     * Retrieves all {@link Role}.
     *
     * @return list of all {@link Role}
     */
    List<Role> getAllRole();
}
