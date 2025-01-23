package com.classes;

import java.util.TreeMap;

/*
 * Packs and unpacks responses
 */
abstract class ResponsePack {

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
}
