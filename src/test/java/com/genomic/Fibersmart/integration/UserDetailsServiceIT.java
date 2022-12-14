package com.genomic.Fibersmart.integration;

import com.genomic.Fibersmart.security.UserDetailsService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({"/application-test.properties"})
@Sql(scripts = "/data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserDetailsServiceIT {

    @Autowired
    private UserDetailsService userDetailsService;

    @Test
    @Tag("LoadUserByUsername")
    @DisplayName("Given an user, when loadUserByUsername, then return correct user details")
    public void givenAnUser_whenLoadUserByUsername_thenReturnCorrectUserDetails() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");

        assertThat(userDetails.getUsername()).isEqualTo("admin");

    }

    @Test(expected = UsernameNotFoundException.class)
    @Tag("LoadUserByUsername")
    @DisplayName("If user is not registered, when loadUserByUsername, then throw UsernameNotFoundException")
    public void givenAnUnFoundUser_whenLoadUserByUsername_thenUsernameNotFoundExceptionIsThrown() {
        userDetailsService.loadUserByUsername("foo");
    }
}
