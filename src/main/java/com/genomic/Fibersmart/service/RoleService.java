package com.genomic.Fibersmart.service;

import com.genomic.Fibersmart.exception.DataAlreadyRegisteredException;
import com.genomic.Fibersmart.model.Role;
import com.genomic.Fibersmart.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RoleService interface.
 */
@Service
public class RoleService implements IRoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleService(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Registers a new {@link com.genomic.Fibersmart.model.Role}.
     *
     * @param role the {@link com.genomic.Fibersmart.model.Role} to be registered
     * @return The {@link Role} saved
     */
    @Override
    public Role createRole(final Role role) {
        Role roleFound = roleRepository.findByRoleType(role.getRoleType().name());

        if (roleFound != null) {
            throw new DataAlreadyRegisteredException("The role provided may be registered already");
        }

        Role roleSaved = roleRepository.save(role);

        return roleSaved;
    }

    /**
     * Checks if the given id is associated with a registered {@link Role}, if so role is updated.
     * Else, ResourceNotFoundException is thrown.
     *
     * @param roleToUpdate {@link Role} to be updated
     * @return the updated {@link Role}
     */
    @Override
    public Role updateRole(final Role roleToUpdate) {
        roleRepository.findById(roleToUpdate.getId()).orElseThrow(() ->
                new ResourceNotFoundException("No role registered with this id"));

        return roleRepository.save(roleToUpdate);
    }

    /**
     * Checks if the given id is associated with a registered {@link Role}, if so role is deleted.
     * Else, ResourceNotFoundException is thrown.
     *
     * @param roleId id of the {@link Role} to be deleted
     */
    @Override
    public void deleteRole(final Long roleId) {

        roleRepository.findById(roleId).orElseThrow(() ->
                new ResourceNotFoundException("No role registered with this id"));

        roleRepository.deleteById(roleId);

    }

    /**
     * Retrieves all roles.
     */
    @Override
    public List<Role> getAllRole() {

        return roleRepository.findAll();
    }
}
