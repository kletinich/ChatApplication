package com.classes;

import java.util.ArrayList;
import java.util.TreeMap;

/*
 * Packs and unpacks responses
 */
public abstract class ResponsePack {

    //pack a general resonse. Send to the right pack functions. 
    public static TreeMap<String, Object> pack(int responseCode, Object... args){
        try{
            switch(responseCode){
                // login success. Pack first and last name of the user
                case Codes.LOGIN_SUCCESS_RESPONSE:
                    if(args.length != 2 || !(args[0] instanceof String) || !(args[1] instanceof String)){
                        throw new IllegalArgumentException("Invalid arguments sent for login request. Excpected: (String, String).");
                    }

                    return packLoginSuccessResponse(responseCode, (String)args[0], (String)args[1]);

                case Codes.LOGIN_FAILED_RESPONSE:
                    if(args.length != 0){
                        throw new IllegalArgumentException("Invalid arguments sent for login request. Excpected: None.");
                    }

                    return packLoginFailureResponse(responseCode);

                default:
                    throw new IllegalArgumentException("Invalid response code: " + responseCode);
                    
            }
        }catch(IllegalArgumentException e){
            System.err.println(e);
            return null;
        }
    }

    private static TreeMap<String, Object> packLoginSuccessResponse(int responseCode, String firstName, String lastName){
        TreeMap<String, Object> response = new TreeMap<>();

        response.put("response_code", responseCode);
        response.put("first_name", firstName);
        response.put("last_name", lastName);

        return response;
    }

    private static TreeMap<String, Object> packLoginFailureResponse(int responseCode){
        TreeMap<String, Object> response = new TreeMap<>();

        response.put("response_code", responseCode);

        return response;
    }

    /*********************************************************
    *                                                        * 
    *                                                        *
    *                  VALIDATION_FUNCTIONS                  *
    *                                                        *
    *                                                        *
    *********************************************************/

    public static boolean isValidResponse(TreeMap<String, Object> response){
        int responseCode;
        Object check = response.get("response_code");

        if(check == null || !(check instanceof Integer)){
            System.err.println("Not a valid response");
            return false;
        }

        responseCode = (int) check;

        // to do: add functions
        switch(responseCode){
            case Codes.LOGIN_FAILED_RESPONSE:
            case Codes.LOGIN_SUCCESS_RESPONSE:
                return isValidLoginResponse(responseCode, response);

            case Codes.GET_USERS_FAIL_RESPONSE:
            case Codes.GET_USERS_SUCCESS_RESPONSE:
                return isValidGetUsersResponse(responseCode, response);
        }

        return true;
    }

    // check if a given login response is valid
    private static boolean isValidLoginResponse(int responseCode, TreeMap<String, Object> response){

        // failed to login - no data
        if(responseCode == Codes.LOGIN_FAILED_RESPONSE){
            if(response.size() != 1){
                return false;
            }

            return true;
        }

        // check if returned first and last names
        else if(responseCode == Codes.LOGIN_SUCCESS_RESPONSE){
            Object check = response.get("first_name");

            if(check == null){
                System.err.println("No first name was sent");
                return false;
            }

            if(!(check instanceof String)){
                System.err.println("Not a valid first name");
                return false;
            }

            check = response.get("last_name");

            if(check == null){
                System.err.println("No last name was sent");
                return false;
            }

            if(!(check instanceof String)){
                System.err.println("Not a valid last name");
                return false;
            }

            return true;
        }

        // should not get here because this function is calles with login codes
        return false;
    }

    // check if a given login response is valid
    private static boolean isValidGetUsersResponse(int responseCode, TreeMap<String, Object> response){
        if(responseCode == Codes.GET_USERS_FAIL_RESPONSE){
            if(response.size() != 1){
                return false;
            }

            return true;
        }

        // check if retrieved a valid user list
        else if(responseCode == Codes.GET_USERS_SUCCESS_RESPONSE){
            Object check = response.get("users_list");

            if(check == null){
                System.err.println("No list of users was sent");
                return false;
            }

            if(!(check instanceof ArrayList)){
                return false;
            }

            for(Object obj: (ArrayList<?>)response.get("users_list")){
                if(!(obj instanceof User)){
                    return false;
                }
            }

            return true;
        }

        // should not get here because this function is calles with getUsers codes
        return false;
    }
}
