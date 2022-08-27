package com.genomic.Fibersmart.repository;

import com.genomic.Fibersmart.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    /**
     * Retrieves the {@link com.genomic.Fibersmart.model.Group} associated with the given group name.
     *
     * @param groupName group name of the {@link com.genomic.Fibersmart.model.Group}
     * @return The {@link com.genomic.Fibersmart.model.Group} found
     */
    Group findByGroupName(final String groupName);
}
