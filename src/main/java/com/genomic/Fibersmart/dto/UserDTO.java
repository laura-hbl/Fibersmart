package com.genomic.Fibersmart.dto;

import com.genomic.Fibersmart.model.Group;
import com.genomic.Fibersmart.model.Role;

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

    private List<Role> roles;

    private List<Group> groups;

    private Boolean enabled;

    public UserDTO(final Long id, final String username, final String firstName, final String lastName,
                   final String password, final List<Role> roles, final List<Group> groups,
                   final Boolean enabled) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roles = roles;
        this.groups = groups;
        this.enabled = enabled;
    }

    public UserDTO(final String username, final String firstName, final String lastName,
                   final String password, final List<Role> roles, final List<Group> groups,
                   final Boolean enabled) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roles = roles;
        this.groups = groups;
        this.enabled = enabled;
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

    public List<Role> getUserRoles() {
        return roles;
    }

    public void setUserRoles(final List<Role> roles) {
        this.roles = roles;
    }

    public List<Group> getUserGroups() {
        return groups;
    }

    public void setUserGroups(final List<Group> groups) {
        this.groups = groups;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(final Boolean enabled) {
        this.enabled = enabled;
    }
}
