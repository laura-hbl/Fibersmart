package com.genomic.Fibersmart.constants;

import com.genomic.Fibersmart.model.Role;

import java.io.Serializable;


public class AuthorityConstants {

    /**
     * The placeholder enum name for the {@link com.genomic.Fibersmart.model.RolePermission} entity. It is used in the
     * controller classes for restricting access to api calls.
     */
    public enum Permission implements Serializable {
        MANAGE_USER,
        MANAGE_ROLE,
        MANAGE_GROUP,
        SEE_ALL_SLIDES,
        DOWNLOAD_JSON
    }

    /**
     * The placeholder enum name for the {@link Role} entity. It is used as a lookup
     * id for retrieving the userRole, which itself contains a collection of {@link
     * com.genomic.Fibersmart.model.RolePermission}.
     */
    public enum RoleType implements Serializable {
        ROLE_ADMIN,
        ROLE_CLIENT,
        ROLE_EXPERT,
        ROLE_STANDARD
    }



}
