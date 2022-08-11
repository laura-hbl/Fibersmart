package com.genomic.Fibersmart.constants;

import java.io.Serializable;

public class AuthorityConstants {

    public static final String PERM_ALL = "PERM_ALL";
    public static final String LOGIN = "LOGIN";
    public static final String CREATE_USER = "CREATE_USER";
    public static final String VIEW_SLIDES = "VIEW_SLIDES";


    public enum RoleType implements Serializable {
        ROLE_ADMIN,
        ROLE_CLIENT,
        ROLE_EXPERT,
        ROLE_STANDARD
    }
}
