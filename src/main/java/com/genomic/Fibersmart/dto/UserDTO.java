package com.genomic.Fibersmart.dto;

import com.genomic.Fibersmart.model.UserGroup;
import com.genomic.Fibersmart.model.UserRole;

import java.util.List;

/**
 * Permits the storage and retrieving data of a user.
 */
public class UserDTO {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String password;

    private List<UserRole> userRoles;

    private List<UserGroup> userGroups;

    public UserDTO(final Long id, final String username, final String firstName, final String lastName,
                   final String password, final List<UserRole> userRoles, final List<UserGroup> userGroups) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userRoles = userRoles;
        this.userGroups = userGroups;
    }

    public UserDTO(final String username, final String firstName, final String lastName,
                   final String password, final List<UserRole> userRoles, final List<UserGroup> userGroups) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userRoles = userRoles;
        this.userGroups = userGroups;
    }

    public UserDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(final List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(final List<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }
}
