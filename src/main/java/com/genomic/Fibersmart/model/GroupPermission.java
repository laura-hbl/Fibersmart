package com.genomic.Fibersmart.model;

import javax.persistence.*;

/**
 * Every {@link User} can belong to one {@link UserGroup userGroups}, and each {@code UserGroup} may have many
 * {@code permissions}. {@code permissions} allow us to identify whether or not {@code User} has access to
 * different parts of the application.
 */
@Entity
@Table(name = "grouppermissions")
public class GroupPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "permission", nullable = false)
    private String permission;

    public GroupPermission(final Long id, final String permission) {
        this.id = id;
        this.permission = permission;
    }

    public GroupPermission() {

    }

    public GroupPermission(final String permission) {
        this.permission = permission;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(final String permission) {
        this.permission = permission;
    }
}
