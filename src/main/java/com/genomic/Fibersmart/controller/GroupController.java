package com.genomic.Fibersmart.controller;

import com.genomic.Fibersmart.dto.GroupDTO;
import com.genomic.Fibersmart.model.Group;
import com.genomic.Fibersmart.service.IGroupService;
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
@RequestMapping("/groups")
public class GroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    private IGroupService groupService;

    private DTOConverter dtoConverter;

    private ModelConverter modelConverter;


    @Autowired
    public GroupController(final IGroupService groupService, final DTOConverter dtoConverter,
                           final ModelConverter modelConverter) {
        this.groupService = groupService;
        this.dtoConverter = dtoConverter;
        this.modelConverter = modelConverter;
    }

    /**
     * Registers a new {@link Group}.
     *
     * @param groupDTO
     * @return ResponseEntity object which holds the created group and Http status generated.
     */
    @PreAuthorize("hasAuthority('MANAGE_GROUP')")
    @PostMapping
    public ResponseEntity<GroupDTO> createGroup(@RequestBody final GroupDTO groupDTO) {
        LOGGER.debug("POST Request on /groups with name {}", groupDTO.getGroupName());

        Group group = groupService.createGroup(modelConverter.toGroup(groupDTO));
        GroupDTO createdGroup = dtoConverter.toGroupDTO(group);

        return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
    }

    /**
     * Deletes a {@link Group}.
     *
     * @param groupId id of the {@link Group} to delete.
     */
    @PreAuthorize("hasAuthority('MANAGE_GROUP')")
    @DeleteMapping("/{id}")
    public void deleteGroup(@PathVariable("id") final Long groupId) {
        LOGGER.debug("DELETE Request on /groups/{id} with id : {}", groupId);

        groupService.deleteGroup(groupId);
    }

    /**
     * Updates a registered {@link Group}.
     *
     * @param groupId  id of the group to be updated
     * @param groupDTO the group to be updated
     * @return ResponseEntity object which holds the updated group and Http status generated.
     */
    @PreAuthorize("hasAuthority('MANAGE_GROUP')")
    @PutMapping("/{id}")
    public ResponseEntity<GroupDTO> updateGroup(@PathVariable("id") final Long groupId,
                                                @RequestBody final GroupDTO groupDTO) {
        LOGGER.debug("PUT Request on /groups/{id} with id : {}", groupId);

        Group groupToUpdate = modelConverter.toGroup(groupDTO);
        groupToUpdate.setId(groupId);

        Group groupUpdated = groupService.updateGroup(groupToUpdate);

        return new ResponseEntity<>(dtoConverter.toGroupDTO(groupUpdated), HttpStatus.OK);
    }

    /**
     * Retrieves all groups.
     *
     * @return ResponseEntity object which holds the group list and Http status generated.
     */
    @PreAuthorize("hasAuthority('MANAGE_GROUP')")
    @GetMapping
    public ResponseEntity<List<GroupDTO>> getAllGroup() {
        LOGGER.debug("GET Request on /groups");

        List<GroupDTO> groups = groupService.getAllGroup().stream()
                .map(group -> dtoConverter.toGroupDTO(group))
                .collect(Collectors.toList());

        return new ResponseEntity<>(groups, HttpStatus.OK);
    }
}
