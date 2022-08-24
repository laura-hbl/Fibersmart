package com.genomic.Fibersmart.constants;

import com.genomic.Fibersmart.model.User;

import java.io.Serializable;

/**
 * Static final permissions strings to be used in the controller classes for restricting access to api calls.
 */
public class AuthorityConstants {

    //Creates, updates, deletes, gets all.
    public static final String MANAGE_USER = "MANAGE_USER";

    //Creates, updates, deletes, gets all.
    public static final String MANAGE_USER_GROUP = "MANAGE_USER_GROUP";

    //Creates, updates, deletes, gets all.
    public static final String MANAGE_COVERSLIP = "MANAGE_COVERSLIP";

    //Creates, updates, deletes, gets all.
    public static final String MANAGE_SLIDE = "MANAGE_SLIDE";


    //Group permissions.
    public static final String SEE_ALL_SLIDES = "SEE_ALL_SLIDES";
    public static final String DOWNLOAD_JSON = "DOWNLOAD_JSON";


    /**
     * The placeholder enum name for the {@link com.genomic.Fibersmart.model.UserRole} entity. It is used as a lookup
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
