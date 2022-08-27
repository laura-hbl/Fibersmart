package com.genomic.Fibersmart.model;

import com.genomic.Fibersmart.security.UserDetailsService;

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
     * {@link Role roles}. These are loaded each time a user logs in.
     *
     * @see UserDetailsService#loadUserByUsername(String)
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="users_roles",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id", nullable = false)})
    private List<Role> roles;

    /**
     * {@code users} are granted access to different areas of the application depending on their assigned
     * {@link Group groups}. These are loaded each time a user logs in.
     */
    @ManyToMany()
    @JoinTable(name="users_groups",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name="group_id", referencedColumnName="id", nullable = false)})
    private List<Group> groups;


    /**
     * Indicates whether the user is enabled or not.
     */
    @Column(name = "enabled")
    private Boolean enabled;


    public User(final Long id, final String username, final String firstName, final String lastName,
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

    public User(final String username, final String firstName, final String lastName, final String password,
                final List<Role> roles, final List<Group> groups, final Boolean enabled) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.roles = roles;
        this.groups = groups;
        this.enabled = enabled;
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
