package com.client;

import java.util.TreeMap;

import com.classes.Codes;
import com.classes.RequestPack;

/********************************************************
 *                                                      *
 *                    *
 *                                                      *
 *******************************************************/



public abstract class RequestResponseProcessor {

    /*************************************
     *                                   *
     *          process requests         *
     *                                   *
     ************************************/

    public static TreeMap<String, Object> proccessRequest(int requestCode){        
        switch(requestCode){
            case Codes.LOGIN_REQUEST:
                if(Controller.getMe() == null){
                    System.err.println("Me is not initialized");
                    return null;
                }
                
                return processLoginRequest();

            case Codes.GET_USERS_REQUEST:
                if(Controller.getMe() == null){
                    System.err.println("Me is not initialized");
                    return null;
                }
                
                return processGetUsersRequest();

            default:
                System.err.println("Client error: Unkown request");
                return null;
        }
    }

    private static TreeMap<String, Object> processLoginRequest(){

        String username = Controller.getMe().getUsername();
        String password = Controller.getMe().getPassword();

        return RequestPack.pack(Codes.LOGIN_REQUEST, username, password);
    }

    private static TreeMap<String, Object> processGetUsersRequest(){
        String username = Controller.getMe().getUsername();
        return RequestPack.pack(Codes.GET_USERS_REQUEST, username);
    }


    /*************************************
     *                                   *
     *         process responses         *
     *                                   *
     ************************************/

    public static void proccessResponse(TreeMap<String, Object> responseData){
        
    }
}
