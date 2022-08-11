package com.genomic.Fibersmart.model;

import javax.persistence.*;

@Entity
@Table(name = "permissions")
public class RolePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "permission", nullable = false)
    private String permission;

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getPermission() {
        return permission;
    }
}
