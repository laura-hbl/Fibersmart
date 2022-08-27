package com.genomic.Fibersmart.service;

import com.genomic.Fibersmart.exception.DataAlreadyRegisteredException;
import com.genomic.Fibersmart.model.Group;
import com.genomic.Fibersmart.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService implements IGroupService {

    private GroupRepository groupRepository;


    @Autowired
    public GroupService(final GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Registers a new {@link com.genomic.Fibersmart.model.Group}.
     *
     * @param group the {@link com.genomic.Fibersmart.model.Group} to be registered
     * @return The {@link Group} saved
     */
    public Group createGroup(final Group group) {

        Group groupFound = groupRepository.findByGroupName(group.getGroupName());

        if (groupFound != null) {
            throw new DataAlreadyRegisteredException("The group provided may be registered already");
        }

        Group groupSaved = groupRepository.save(group);

        return groupSaved;
    }

    /**
     * Checks if the given id is associated with a registered {@link Group}, if so group is updated.
     * Else, ResourceNotFoundException is thrown.
     *
     * @param group {@link Group} to be updated
     * @return the updated {@link Group}
     */
    @Override
    public Group updateGroup(final Group group) {

        groupRepository.findById(group.getId()).orElseThrow(() ->
                new ResourceNotFoundException("No group registered with this id"));

        return groupRepository.save(group);
    }

    /**
     * Checks if the given id is associated with a registered {@link Group}, if so group is deleted.
     * Else, ResourceNotFoundException is thrown.
     *
     * @param groupId id of the {@link Group} to be deleted
     */
    @Override
    public void deleteGroup(final Long groupId) {

        groupRepository.findById(groupId).orElseThrow(() ->
                new ResourceNotFoundException("No group registered with this id"));

        groupRepository.deleteById(groupId);
    }

    /**
     * Retrieves all groups.
     */
    @Override
    public List<Group> getAllGroup() {

        return groupRepository.findAll();
    }

}
