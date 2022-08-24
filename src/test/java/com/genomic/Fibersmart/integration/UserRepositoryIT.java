package com.genomic.Fibersmart.integration;

import com.genomic.Fibersmart.model.User;
import com.genomic.Fibersmart.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({"/application-test.properties"})
@Sql(scripts = "/data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Tag("FindByUsername")
    public void givenARegisteredUserName_whenFindByUsername_thenReturnTheRegisteredUser() {
        User user = userRepository.findByUsername("standard");

        assertThat(user.getLastName()).isEqualTo("standard");
        assertThat(user.getUserRoles().contains("ROLE_STANDARD"));
    }

    @Test
    @Tag("FindByUserName - Exception")
    public void givenAnUnRegisteredUserUserName_whenFindByUserName_thenReturnNull() {
        User user = userRepository.findByUsername("foo");

        assertThat(user).isEqualTo(null);
    }
}
