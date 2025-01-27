package com.classes;

/*
 * An abstract class that holds request codes from user
 * and response codes from client.
 */
public abstract class Codes {

    /*
     *  General codes
     */

    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;

    public static final int CONNECTION_ERROR = 3;
    public static final int CONNECTION_SUCCESS = 4;

    /*
     * Request codes
    */

    public static final int REGISTER_REQUEST = 10;

    public static final int LOGIN_REQUEST = 20;

    public static final int GET_USERS_REQUEST = 30;


    /*
     * Response codes
    */

    public static final int NOT_VALID_REQUEST = 0;
    public static final int UNKNOWN_REQUEST = 1;

    public static final int REGISTER_SUCCESS_RESPONSE = 11;
    public static final int REGISTER_FAILED_RESPONSE = 12;

    public static final int LOGIN_SUCCESS_RESPONSE = 21;
    public static final int LOGIN_FAILED_RESPONSE = 22;

    public static final int GET_USERS_SUCCESS_RESPONSE = 31;
    public static final int GET_USERS_FAIL_RESPONSE = 32;

}
