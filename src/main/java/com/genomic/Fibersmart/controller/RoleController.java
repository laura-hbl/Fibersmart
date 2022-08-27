package com.genomic.Fibersmart.controller;

import com.genomic.Fibersmart.dto.RoleDTO;
import com.genomic.Fibersmart.model.Role;
import com.genomic.Fibersmart.service.IRoleService;
import com.genomic.Fibersmart.util.DTOConverter;
import com.genomic.Fibersmart.util.ModelConverter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/roles")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    private IRoleService roleService;

    private DTOConverter dtoConverter;

    private ModelConverter modelConverter;


    @Autowired
    public RoleController(final IRoleService roleService, final DTOConverter dtoConverter,
                          final ModelConverter modelConverter) {
        this.roleService = roleService;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    /**
     * Registers a new {@link Role}.
     *
     * @param roleDTO
     * @return ResponseEntity object which holds the created role and Http status generated.
     */
    @PreAuthorize("hasAuthority('MANAGE_ROLE')")
    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody final RoleDTO roleDTO) {
        LOGGER.debug("POST Request on /roles with name {}", roleDTO.getRoleType().name());

        Role role = roleService.createRole(modelConverter.toRole(roleDTO));
        RoleDTO createdRole = dtoConverter.toRoleDTO(role);

        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    /**
     * Deletes a {@link Role}.
     *
     * @param roleId id of the {@link Role} to delete.
     */
    @PreAuthorize("hasAuthority('MANAGE_ROLE')")
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable("id") final Long roleId) {
        LOGGER.debug("DELETE Request on /roles/{id} with id : {}", roleId);

        roleService.deleteRole(roleId);
    }

    /**
     * Updates a registered {@link Role}.
     *
     * @param roleId  id of the role to be updated
     * @param roleDTO the role to be updated
     * @return ResponseEntity object which holds the updated role and Http status generated.
     */
    @PreAuthorize("hasAuthority('MANAGE_role')")
    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable("id") final Long roleId,
                                              @RequestBody final RoleDTO roleDTO) {
        LOGGER.debug("PUT Request on /roles/{id} with id : {}", roleId);

        Role roleToUpdate = modelConverter.toRole(roleDTO);
        roleToUpdate.setId(roleId);

        Role roleUpdated = roleService.updateRole(roleToUpdate);

        return new ResponseEntity<>(dtoConverter.toRoleDTO(roleUpdated), HttpStatus.OK);
    }

    /**
     * Retrieves all roles.
     *
     * @return ResponseEntity object which holds the role list and Http status generated.
     */
    @PreAuthorize("hasAuthority('MANAGE_ROLE')")
    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRole() {
        LOGGER.debug("GET Request on /roles");

        List<RoleDTO> roles = roleService.getAllRole().stream()
                .map(role -> dtoConverter.toRoleDTO(role))
                .collect(Collectors.toList());

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
