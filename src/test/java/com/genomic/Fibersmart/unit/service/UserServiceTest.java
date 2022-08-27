package com.genomic.Fibersmart.unit.service;

import com.genomic.Fibersmart.constants.AuthorityConstants;
import com.genomic.Fibersmart.exception.DataAlreadyRegisteredException;
import com.genomic.Fibersmart.model.*;
import com.genomic.Fibersmart.repository.UserRepository;
import com.genomic.Fibersmart.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private static User user;

    private static Role role;

    private static Group group;


    public UserServiceTest() {
    }

    @Before
    public void setUp() {
        role = new Role(1L, AuthorityConstants.RoleType.ROLE_ADMIN,
                Arrays.asList(new RolePermission(1L, AuthorityConstants.Permission.MANAGE_USER)));
        group = new Group(1L, "GV", true,
                Arrays.asList(new GroupPermission(1L, "SEE_ALL_SLIDE")));
        user = new User(1L, "JeanGV", "Jean", "Dupont", "password123",
                Arrays.asList(role), Arrays.asList(group), true);
    }

    @Test
    @Tag("CreateUser")
    @DisplayName("If user is not registered, when createUser, then user should be saved correctly")
    public void givenAnUnRegisteredUser_whenAddUser_thenUserShouldBeSavedCorrectly() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("HjuIY9jk5op&tc");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User userSaved = userService.createUser(user);

        assertThat(userSaved.getPassword()).isEqualTo("HjuIY9jk5op&tc");
        assertThat(userSaved.getUsername()).isEqualTo("JeanGV");

        InOrder inOrder = inOrder(userRepository, passwordEncoder);
        inOrder.verify(userRepository).findByUsername(anyString());
        inOrder.verify(passwordEncoder).encode(anyString());
        inOrder.verify(userRepository).save(any(User.class));
    }

    @Test(expected = DataAlreadyRegisteredException.class)
    @Tag("CreateUser")
    @DisplayName("If user is already registered, when createUser, then throw DataAlreadyRegisteredException")
    public void givenARegisteredUser_whenCreateUser_thenDataAlreadyRegisteredExceptionIsThrown() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        userService.createUser(user);
    }

    @Test
    @Tag("UpdateUser")
    @DisplayName("Given a registered User, when updateUser, then User should be updated correctly")
    public void givenARegisteredUser_whenUpdateUser_thenUserShouldBeUpdateCorrectly() {

        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(user);

        assertThat(result).isEqualTo(user);
        InOrder inOrder = inOrder(userRepository);
        inOrder.verify(userRepository).findById(anyLong());
        inOrder.verify(userRepository).save(any(User.class));
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("UpdateUser")
    @DisplayName("If User cant be found, when updateUser, then throw ResourceNotFoundException")
    public void givenUnFoundUser_whenUpdateUser_thenResourceNotFoundExceptionIsThrown() {
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        userService.updateUser(user);
    }

    @Test
    @Tag("DeleteUser")
    @DisplayName("Given User Id, when deleteUser, then delete process should be done in correct order")
    public void givenAUserId_whenDeleteUser_thenDeletingShouldBeDoneInCorrectOrder() {
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(user));

        userService.deleteUser(anyLong());

        InOrder inOrder = inOrder(userRepository);
        inOrder.verify(userRepository).findById(anyLong());
        inOrder.verify(userRepository).deleteById(anyLong());
    }

    @Test(expected = ResourceNotFoundException.class)
    @Tag("DeleteUser")
    @DisplayName("If User can't be found, when deleteUser, then throw ResourceNotFoundException")
    public void givenUnFoundUser_whenDeleteUser_thenResourceNotFoundExceptionIsThrown() {
        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.empty());

        userService.deleteUser(anyLong());
    }

    @Test
    @Tag("GetAllUser")
    @DisplayName("Given an user list, when getAllUser, then result should match expected user list")
    public void givenAnUserList_whenGetAllUser_thenReturnExpectedUserList() {
        Role role = new Role(1L, AuthorityConstants.RoleType.ROLE_ADMIN,
                Arrays.asList(new RolePermission(1L, AuthorityConstants.Permission.MANAGE_USER)));
        Group group = new Group(1L, "GV", true,
                Arrays.asList(new GroupPermission(1L, "SEE_ALL_SLIDE")));
        User user2 = new User("JeanGV", "Jean", "Dupont", "password123",
                Arrays.asList(role), Arrays.asList(group), true);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user, user2));

        List<User> result = userService.getAllUser();

        assertThat(result).contains(user, user2);
        verify(userRepository).findAll();
    }
}
