package com.genomic.Fibersmart.model;

import com.genomic.Fibersmart.constants.AuthorityConstants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * {@code userRoles} have a {@code @ManyToMany} mapping with {@code users}. {@code userRoles} are made up of a
 * {@code @ManyToMany} mapping of {@link RolePermission permissions}, which define {@code User} access rights to
 * different areas of the application via the controllers.
 */
@Entity
@Table(name = "userroles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /**
     * The type name of this {@link UserRole}. Must be unique - used to look up the {@code UserRole} in the database.
     */
    @Column(name = "role_type", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityConstants.RoleType roleType;

    /**
     * {@link UserRole userRoles} are made up of a {@code @ManyToMany} mapping of {@link RolePermission permissions}.
     * Different {@code userRoles} can share the same predefined {@code permissions}. {@code permissions} are used
     * internally by spring.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "userroles_rolepermissions",
            joinColumns = {@JoinColumn(name = "userrole_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "rolepermission_id", referencedColumnName = "id")})
    private List<RolePermission> permissions = new ArrayList<>();

    public UserRole() {
    }

    public UserRole(final Long id, final AuthorityConstants.RoleType roleType, final List<RolePermission> permissions) {
        this.id = id;
        this.roleType = roleType;
        this.permissions = permissions;
    }

    public UserRole(final AuthorityConstants.RoleType roleType, final List<RolePermission> permissions) {
        this.roleType = roleType;
        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public AuthorityConstants.RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(final AuthorityConstants.RoleType roleType) {
        this.roleType = roleType;
    }

    public List<RolePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(final List<RolePermission> permissions) {
        this.permissions = permissions;
    }
}
