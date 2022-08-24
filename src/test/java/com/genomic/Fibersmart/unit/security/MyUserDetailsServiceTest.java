package com.genomic.Fibersmart.unit.security;

import com.genomic.Fibersmart.constants.AuthorityConstants;
import com.genomic.Fibersmart.model.*;
import com.genomic.Fibersmart.repository.UserRepository;
import com.genomic.Fibersmart.security.MyUserDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MyUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MyUserDetailsService myUserDetailsService;

    private User user;

    @Before
    public void setUp() {
        UserRole userRole = new UserRole(1L, AuthorityConstants.RoleType.ROLE_ADMIN,
                Arrays.asList(new RolePermission(1L, "ROLE_ADMIN")));
        UserGroup userGroup = new UserGroup(1L, "GV", true,
                Arrays.asList(new GroupPermission(1L, "SEE_ALL_SLIDE")));
        user = new User("JeanGV", "Jean", "Dupont", "password123",
                Arrays.asList(userRole), Arrays.asList(userGroup));
    }

    @Test
    @Tag("LoadUserByUsername")
    @DisplayName("Given an user, when loadUserByUsername, then return correct user details")
    public void givenAnUser_whenLoadUserByUsername_thenUserDetailsShouldBeReturnedCorrectly() {
        when(userRepository.findByUsername(anyString())).thenReturn(user);

        UserDetails userDetails = myUserDetailsService.loadUserByUsername("Laura");

        assertThat(userDetails.getUsername()).isEqualTo("Laura");
        assertThat(userDetails.getPassword()).isEqualTo("password123");
        assertThat(userDetails.getAuthorities()).toString().contains("ROLE_ADMIN");
        verify(userRepository).findByUsername(anyString());
    }

    @Test(expected = UsernameNotFoundException.class)
    @Tag("LoadUserByUsername")
    @DisplayName("If user is not registered, when loadUserByUsername, then throw UsernameNotFoundException")
    public void givenAnUnFoundUser_whenLoadUserByUsername_thenUsernameNotFoundExceptionIsThrown() {
        when(userRepository.findByUsername(anyString())).thenReturn(null);

        myUserDetailsService.loadUserByUsername("Laura");
    }



}
