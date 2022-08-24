package com.genomic.Fibersmart.repository;

import com.genomic.Fibersmart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link User} repository interface which provides methods that permit interaction with database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves the {@link User} associated with the given username.
     *
     * @param username username of the {@link User}
     * @return The {@link User} found
     */
    User findByUsername(final String username);

}
