package com.genomic.Fibersmart.dto;

import com.genomic.Fibersmart.constants.AuthorityConstants;
import com.genomic.Fibersmart.model.RolePermission;

import java.util.ArrayList;
import java.util.List;

public class RoleDTO {

    private Long id;

    private AuthorityConstants.RoleType roleType;

    private List<RolePermission> permissions = new ArrayList<>();

    public RoleDTO(final Long id, final AuthorityConstants.RoleType roleType, final List<RolePermission> permissions) {
        this.id = id;
        this.roleType = roleType;
        this.permissions = permissions;
    }

    public RoleDTO() {
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
