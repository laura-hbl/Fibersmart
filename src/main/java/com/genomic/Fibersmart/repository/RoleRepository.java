package com.genomic.Fibersmart.repository;

import com.genomic.Fibersmart.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Retrieves the {@link com.genomic.Fibersmart.model.Role} associated with the given role type.
     *
     * @param roleType type of the {@link com.genomic.Fibersmart.model.Role}
     * @return The {@link com.genomic.Fibersmart.model.Role} found
     */
    Role findByRoleType(final String roleType);
}
