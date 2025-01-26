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
    public static TreeMap<String, Object> proccessRequest(int requestCode){        
        switch(requestCode){
            case Codes.LOGIN_REQUEST:
                if(Controller.getMe() == null){
                    System.err.println("Me is not initialized");
                    return null;
                }
                
                return loginRequest();

            case Codes.GET_USERS_REQUEST:
                if(Controller.getMe() == null){
                    System.err.println("Me is not initialized");
                    return null;
                }
                
                return getUsersRequest();

            default:
                System.err.println("Client error: Unkown request");
                return null;
        }
    }

    private static TreeMap<String, Object> loginRequest(){

        String username = Controller.getMe().getUsername();
        String password = Controller.getMe().getPassword();

        return RequestPack.pack(Codes.LOGIN_REQUEST, username, password);
    }

    private static TreeMap<String, Object> getUsersRequest(){
        String username = Controller.getMe().getUsername();
        return RequestPack.pack(Codes.GET_USERS_REQUEST, username);
    }
}
