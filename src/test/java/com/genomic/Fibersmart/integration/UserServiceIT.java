package com.genomic.Fibersmart.integration;

import com.genomic.Fibersmart.exception.DataAlreadyRegisteredException;
import com.genomic.Fibersmart.model.*;
import com.genomic.Fibersmart.service.IUserService;
import com.genomic.Fibersmart.util.DTOConverter;
import com.genomic.Fibersmart.util.ModelConverter;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({"/application-test.properties"})
@Sql(scripts = "/data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserServiceIT {

    @Autowired
    private IUserService userService;

    @Test
    @Tag("CreateUser")
    @DisplayName("If user is not registered, when createUser, then return user saved")
    public void givenAnUnRegisteredUser_whenCreateUser_thenUserSavedShouldBeReturned() {
        User user = new User("Laura", "Laura", "Laura", "12345",
                null, null);

        User userSaved = userService.createUser(user);

        assertThat(userSaved.getUsername()).isEqualTo("Laura");
    }

    @Test(expected = DataAlreadyRegisteredException.class)
    @Tag("CreateUser")
    @DisplayName("If given username is already used, when createUser, then throw DataAlreadyRegisteredException")
    public void givenAnAlreadyUsedUsername_whenRegisterUser_thenDataAlreadyRegisteredExceptionIsThrown() {
        User user = new User("admin", "admin", "admin", "admin",
                null, null);

        userService.createUser(user);
    }

    @Test
    @Tag("UpdateUser")
    @DisplayName("Given an user to update, when updateUser, then return user updated")
    public void givenAnUserToUpdate_whenUpdateUser_thenUserUpdatedShouldBeReturned() {
        User userToUpdate = new User(1L, "adminUpdated", "admin", "admin",
                "admin", null, null);

        User userUpdated = userService.updateUser(userToUpdate);

        assertNotNull(userUpdated);
        assertThat(userUpdated.getUsername()).isEqualTo("adminUpdated");
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("UpdateUser")
    @DisplayName("If user id cant be found, when updateUser, then throw ResourceNotFoundException")
    public void givenUnFoundUserId_whenUpdateUser_thenResourceNotFoundExceptionIsThrown() {
        User userToUpdate = new User( 6L, "adminUpdated", "admin", "admin",
                "admin", null, null);

        userService.updateUser(userToUpdate);
    }

    @Test
    @Tag("DeleteUser")
    @DisplayName("Given an user to delete, when deleteUser, then user should be delete successfully")
    public void givenAnUserId_whenDeleteUser_thenUserShouldBeDeleteSuccessfully() {
        userService.deleteUser(2L);

        assertThrows(ResourceNotFoundException.class, () -> { userService.deleteUser(2L);
        });
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteUser")
    @DisplayName("If user id cant be found, when deleteUser, then throw ResourceNotFoundException")
    public void givenUnFoundUserId_whenDeleteUser_thenResourceNotFoundExceptionIsThrown() {
        userService.deleteUser(6L);
    }

    @Test
    @Tag("GetAllUser")
    @DisplayName("When getAllUser, then user list should be returned")
    public void whenGetAllUser_thenUserListShouldBeReturned() {
        List<User> users = userService.getAllUser();

        assertNotNull(users);
        assertThat(users.size()).isEqualTo(2);
    }
}
