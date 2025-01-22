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

                // user wants to login to the server
                case Codes.LOGIN_REQUEST:
                    if(args.length != 2 || !(args[0] instanceof String) || !(args[1] instanceof String)){
                        throw new IllegalArgumentException("Invalid arguments sent for login request. Excpected: (String, String).");
                    }

                    return packLoginRequest(requestCode, (String)args[1], (String)args[2]);

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
            case Codes.LOGIN_REQUEST:
                return isValidLoginRequest(request);

            default:
                System.err.println("Not a valid request");
                return false;
        }
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


    /**********************************************************
     *                                                        * 
     *                                                        *
     *                      PACK FUNCTIONS                    *
     *                                                        *
     *                                                        *
     *********************************************************/

    /*
     * Pack a login request
     */
    private  static TreeMap<String, Object> packLoginRequest(int requestCode, String username, String password){
        TreeMap<String, Object> request = new TreeMap<>();

        request.put("request_code", requestCode);
        request.put("username", username);
        request.put("password", password);

        return request;
    }
}
