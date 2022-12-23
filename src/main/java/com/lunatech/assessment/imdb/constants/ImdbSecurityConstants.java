package com.lunatech.assessment.imdb.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImdbSecurityConstants {

    public static final String ROLE_TITLE = "TITLE";
    public static final String ROLE_NAME = "NAME";
    public static final String ROLE_DEGREE = "DEGREE";
    public static final String ROLE_NO = "NOROLE";
    
    
    //default credentials 
    public static final String USER_ROLE_TITLE = "userT";
    public static final String PASS_ROLE_TITLE = "passwordT";
    public static final String USER_ROLE_NAME = "userN";
    public static final String PASS_ROLE_NAME = "passwordN";
    public static final String USER_ROLE_DEGREE = "userD";
    public static final String PASS_ROLE_DEGREE = "passwordD";
    public static final String USER_ROLE_TITLE_NAME = "userTN";
    public static final String PASS_ROLE_TITLE_NAME = "passwordTN";
    public static final String USER_ROLE_TITLE_NAME_DEGREE = "userTND";
    public static final String PASS_ROLE_TITLE_NAME_DEGREE = "passwordTND";

    public static final String USER_ROLE_NO = "usernr";
    public static final String PASS_ROLE_NO = "passwordnr";

}

