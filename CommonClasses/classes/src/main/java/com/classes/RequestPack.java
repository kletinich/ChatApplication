package com.classes;

import java.util.TreeMap;

/*
 * Packs and unpacks requests
 */
public abstract class RequestPack {

    //pack a general request. Send to the right pack functions. 
    public static TreeMap<String, Object> pack(int requestCode, Object... args){

        try{
            switch(requestCode){

                // user wants to register to the server
                case Codes.REGISTER_REQUEST:
                    if(args.length != 4 || !(args[0] instanceof String) || !(args[1] instanceof String || !(args[2] instanceof String) || !(args[3] instanceof String))){
                        throw new IllegalArgumentException("Invalid arguments sent for login request. Excpected: username - String, password - String, first name - String, last name = String");
                    } 

                    return packRegisterRequest(requestCode, (String)args[0], (String)args[1], (String)args[2], (String)args[3]);

                // user wants to login to the server
                case Codes.LOGIN_REQUEST:
                    if(args.length != 2 || !(args[0] instanceof String) || !(args[1] instanceof String)){
                        throw new IllegalArgumentException("Invalid arguments sent for login request. Excpected: username - String, password - String.");
                    }

                    return packLoginRequest(requestCode, (String)args[0], (String)args[1]);

                // user wants to get list of other users from the server
                case Codes.GET_USERS_REQUEST:
                    if(args.length != 1 || !(args[0] instanceof String)){
                        throw new IllegalArgumentException("Invalid arguments sent for login request. Excpected: username - String.");
                    }

                    return packGetUsersRequest(requestCode, (String) args[0]);

                default:
                    throw new IllegalArgumentException("Invalid request code: " + requestCode);
                    
            }
        }catch(IllegalArgumentException e){
            System.err.println(e);
            return null;
        }
    }

     /*********************************************************
     *                                                        * 
     *                                                        *
     *                  VALIDATION_FUNCTIONS                  *
     *                                                        *
     *                                                        *
     *********************************************************/ 
    
    // check if a packed general request is valid
    public static boolean isValidRequest(TreeMap<String, Object> request){
        int requestCode;
        Object check = request.get("request_code");

        if(check == null || !(check instanceof Integer)){
            System.err.println("Not a valid request");
            return false;
        }

        requestCode = (int) check;

        switch(requestCode){
            case Codes.REGISTER_REQUEST:
                return isValidRegisterRequest(request);

            case Codes.LOGIN_REQUEST:
                return isValidLoginRequest(request);

            case Codes.GET_USERS_REQUEST:
                return isValidGetUsersRequest(request);

            default:
                System.err.println("Not a valid request");
                return false;
        }
    }

    // check if a packed register request is valid // will be changed and more data will be added
    private static boolean isValidRegisterRequest(TreeMap<String, Object> request){
        if(request.size() != 5){
            System.err.println("Not a valid request");
            return false;
        }

        Object check =  request.get("username");

        if(check == null){
            System.err.println("No username was sent");
            return false;
        }

        check = request.get("password");

        if(check == null){
            System.err.println("No password was sent");
            return false;
        }

        if(!(check instanceof String)){
            System.err.println("Not a valid password");
            return false;
        }

        check = request.get("first_name");

        if(check == null){
            System.err.println("No first name was sent");
            return false;
        }

        if(!(check instanceof String)){
            System.err.println("Not a valid first name");
            return false;
        }

        check = request.get("last_name");

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

    // check if a packed login request is valid
    private static boolean isValidLoginRequest(TreeMap<String, Object> request){

        if(request.size() != 3){
            System.err.println("Not a valid request");
            return false;
        }

        Object check = request.get("username");

        if(check == null){
            System.err.println("No username was sent");
            return false;
        }

        if(!(check instanceof String)){
            System.err.println("Not a valid username");
            return false;
        }

        check = request.get("password");

        if(check == null){
            System.err.println("No password was sent");
            return false;
        }

        if(!(check instanceof String)){
            System.err.println("Not a valid password");
            return false;
        }

        return true;
    }

    // check if a packed get users request is valid
    private static boolean isValidGetUsersRequest(TreeMap<String, Object> request){
        if(request.size() != 2){
            System.err.println("Not a valid request");
            return false;
        }

        Object check = request.get("username");

        if(check == null){
            System.err.println("No username was sent");
            return false;
        }

        if(!(check instanceof String)){
            System.err.println("Not a valid username");
            return false;
        }

        return true;
     }


    /**********************************************************
     *                                                        * 
     *                                                        *
     *                      PACK FUNCTIONS                    *
     *                                                        *
     *                                                        *
     *********************************************************/

    // pack a register request
    private static TreeMap<String, Object> packRegisterRequest(int requestCode, String username, String password, String firstName, String lastName){
        TreeMap<String, Object> request = new TreeMap<>();

        request.put("request_code", requestCode);
        request.put("username", username);
        request.put("password", password);
        request.put("first_name", firstName);
        request.put("last_name", lastName);

        return request;
    }

    // Pack a login request
    private  static TreeMap<String, Object> packLoginRequest(int requestCode, String username, String password){
        TreeMap<String, Object> request = new TreeMap<>();

        request.put("request_code", requestCode);
        request.put("username", username);
        request.put("password", password);

        return request;
    }

    // Pack get users request
    private  static TreeMap<String, Object> packGetUsersRequest(int requestCode, String username){
        TreeMap<String, Object> request = new TreeMap<>();

        request.put("request_code", requestCode);
        request.put("username", username);

        return request;
    }
}
