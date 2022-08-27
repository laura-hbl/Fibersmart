package com.genomic.Fibersmart.model;

import com.genomic.Fibersmart.constants.AuthorityConstants;

import javax.persistence.*;

/**
 * Every {@link User} can have a one or many {@link Role roles}, and each {@code Role} may have many
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
    @Enumerated(EnumType.STRING)
    private AuthorityConstants.Permission permission;

    public RolePermission(final Long id, final AuthorityConstants.Permission permission) {
        this.id = id;
        this.permission = permission;
    }

    public RolePermission() {

    }

    public RolePermission(final AuthorityConstants.Permission permission) {
        this.permission = permission;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPermission(final AuthorityConstants.Permission permission) {
        this.permission = permission;
    }

    public AuthorityConstants.Permission getPermission() {
        return this.permission;
    }
}
