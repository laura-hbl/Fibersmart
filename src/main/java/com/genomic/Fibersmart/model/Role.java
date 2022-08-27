package com.genomic.Fibersmart.model;

import com.genomic.Fibersmart.constants.AuthorityConstants;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * {@code roles} have a {@code @ManyToMany} mapping with {@code users}. {@code roles} are made up of a
 * {@code @ManyToMany} mapping of {@link RolePermission permissions}, which define {@code User} access rights to
 * different areas of the application via the controllers.
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /**
     * The type name of this {@link Role}. Must be unique - used to look up the {@code Role} in the database.
     */
    @Column(name = "role_type", unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private AuthorityConstants.RoleType roleType;

    /**
     * {@link Role roles} are made up of a {@code @ManyToMany} mapping of {@link RolePermission permissions}.
     * Different {@code roles} can share the same predefined {@code permissions}. {@code permissions} are used
     * internally by spring.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_rolepermissions",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "rolepermission_id", referencedColumnName = "id")})
    private List<RolePermission> permissions = new ArrayList<>();

    public Role() {
    }

    public Role(final Long id, final AuthorityConstants.RoleType roleType, final List<RolePermission> permissions) {
        this.id = id;
        this.roleType = roleType;
        this.permissions = permissions;
    }

    public Role(final AuthorityConstants.RoleType roleType, final List<RolePermission> permissions) {
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
