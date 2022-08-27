package com.genomic.Fibersmart.service;

import com.genomic.Fibersmart.model.Group;

import java.util.List;

/**
 * GroupService interface.
 */
public interface IGroupService {

    /**
     * Registers a new {@link com.genomic.Fibersmart.model.Group}.
     *
     * @param group the {@link com.genomic.Fibersmart.model.Group} to be registered
     * @return The {@link Group} saved
     */
    Group createGroup(final Group group);

    /**
     * Updates a {@link Group}.
     *
     * @return The updated {@link Group}
     */
    Group updateGroup(Group group);

    /**
     * Deletes a {@link Group}.
     *
     * @param groupId id of the {@link Group} to be deleted
     */
    void deleteGroup(Long groupId);

    /**
     * Retrieves all {@link Group}.
     *
     * @return list of all {@link Group}
     */
    List<Group> getAllGroup();
}
