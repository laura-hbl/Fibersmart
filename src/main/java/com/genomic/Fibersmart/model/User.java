package com.genomic.Fibersmart.model;

import javax.persistence.*;
import java.util.List;

/**
 * Permits the storage and retrieving data of a {@code User}.
 */
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * {@code User} username, which must be unique. This is used in many areas to find {@code User} in the database.
     */
    @Column(name = "username")
    private String username;

    /**
     * The user first name.
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * The user last name.
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * The user password which is fully encrypted.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * {@code users} are granted access to different areas of the application depending on their assigned
     * {@link UserRole userRoles}. These are loaded each time a user logs in.
     *
     * @see com.genomic.Fibersmart.security.MyUserDetailsService#loadUserByUsername(String)
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="users_userroles",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name="userrole_id", referencedColumnName="id", nullable = false)})
    private List<UserRole> userRoles;

    /**
     * {@code users} are granted access to different areas of the application depending on their assigned
     * {@link UserGroup userGroup}. These are loaded each time a user logs in.
     */
    @ManyToMany()
    @JoinTable(name="users_usergroups",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name="usergroup_id", referencedColumnName="id", nullable = false)})
    private List<UserGroup> userGroups;


    public User(final Long id, final String username, final String firstName, final String lastName,
                final String password, final List<UserRole> userRoles, final List<UserGroup> userGroups) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userRoles = userRoles;
        this.userGroups = userGroups;
    }

    public User(final String username, final String firstName, final String lastName, final String password,
                final List<UserRole> userRoles, final List<UserGroup> userGroups) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userRoles = userRoles;
        this.userGroups = userGroups;
    }

    public User() {
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
