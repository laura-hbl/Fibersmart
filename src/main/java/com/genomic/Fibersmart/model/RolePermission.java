package com.genomic.Fibersmart.model;

import javax.persistence.*;

/**
 * Every {@link User} can have a one or many {@link UserRole userRoles}, and each {@code UserRole} may have many
 * {@code permissions}. {@code permissions} allow us to identify whether or not {@code User} has access to
 * different parts of the application.
 */
@Entity
@Table(name = "rolepermissions")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "permission", nullable = false)
    private String permission;

    public RolePermission(final Long id, final String permission) {
        this.id = id;
        this.permission = permission;
    }

    public RolePermission() {

    }

    public RolePermission(final String permission) {
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPermission(final String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
