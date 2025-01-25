package com.classes;

/*
 * An abstract class that holds request codes from user
 * and response codes from client.
 */
public abstract class Codes {

    /*
     * Request codes
     */


    public static final int LOGIN_REQUEST = 20;

    public static final int GET_USERS_REQUEST = 30;


    /*
     * Response codes
     */

    public static final int NOT_VALID_REQUEST = 0;
    public static final int UNKNOWN_REQUEST = 1;

    public static final int LOGIN_SUCCESS_RESPONSE = 21;
    public static final int LOGIN_FAILED_RESPONSE = 22;

    public static final int GET_USERS_SUCCESS_RESPONSE = 31;
    public static final int GET_USERS_FAIL_RESPONSE = 32;

}
