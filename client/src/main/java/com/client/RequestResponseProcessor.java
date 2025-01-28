package com.client;

import java.util.ArrayList;
import java.util.TreeMap;

import com.classes.Codes;
import com.classes.RequestPack;
import com.classes.ResponsePack;
import com.classes.User;

/********************************************************
 *                                                      *
 *           Procceses request to the server            *
 *           Procceses responses from the server        *
 *                                                      *
 *******************************************************/



public abstract class RequestResponseProcessor {

    private static String message = "";     // message to be sent to the classes to be displayed

    public static String getMessage(){
        return message;
    }


    /*************************************
     *                                   *
     *          process requests         *
     *                                   *
     ************************************/

    // proccess a general request later to be sent to the server
    public static TreeMap<String, Object> proccessRequest(int requestCode){        
        switch(requestCode){

            case Codes.REGISTER_REQUEST:
                if(Controller.getMe() == null){
                    System.err.println("Me is not initialized");
                    return null;
                }

                return proccessRegisterRequest();

            // user wants to login
            case Codes.LOGIN_REQUEST:
                if(Controller.getMe() == null){
                    System.err.println("Me is not initialized");
                    return null;
                }
                
                return proccessLoginRequest();

            // user wants to get list of users 
            case Codes.GET_USERS_REQUEST:
                if(Controller.getMe() == null){
                    message = "Client error: Me is not initialized";
                    return null;
                }
                
                return proccessGetUsersRequest();

            // unknown request. should not get here but just in case
            default:
                message = "Client error: Unknown request";
                return null;
        }
    }

    private static TreeMap<String, Object> proccessRegisterRequest(){
        String firstName = Controller.getMe().getFirstName();
        String lastName = Controller.getMe().getLastName();
        String username = Controller.getMe().getUsername();
        String password = Controller.getMe().getPassword();

        return RequestPack.pack(Codes.REGISTER_REQUEST, username, password, firstName, lastName);
    }

    // proccess a request to login
    private static TreeMap<String, Object> proccessLoginRequest(){

        String username = Controller.getMe().getUsername();
        String password = Controller.getMe().getPassword();

        return RequestPack.pack(Codes.LOGIN_REQUEST, username, password);
    }

    // proccess a request to get list of users
    private static TreeMap<String, Object> proccessGetUsersRequest(){
        String username = Controller.getMe().getUsername();
        return RequestPack.pack(Codes.GET_USERS_REQUEST, username);
    }


    /*************************************
     *                                   *
     *         process responses         *
     *                                   *
     ************************************/

    // proccess a general response from the server
    public static void proccessResponse(TreeMap<String, Object> responseData){
        boolean isValidResponse = ResponsePack.isValidResponse(responseData);
        int responseCode;

        // not valid response format
        if(!isValidResponse){
            message = "General error: not a valid response";
            return;
        }

        // valid data. can be unpacked safetly
        responseCode = (int)responseData.get("response_code");

        switch(responseCode){

            // could not register because user with the same username exists
            case Codes.REGISTER_FAILED_RESPONSE:
                message = "User already exists";
                break;

            // registered successfully
            case Codes.REGISTER_SUCCESS_RESPONSE:
                proccessRegisterSuccessResponse();
                break;

            // could not login because of incorrect username or password
            case Codes.LOGIN_FAILED_RESPONSE:
                message = "Not valid username or password";
                break;

            // logged in successfully
            case Codes.LOGIN_SUCCESS_RESPONSE:
                proccessLoginSuccessResponse(responseData);
                break;

            // could not retrieve users from the server
            case Codes.GET_USERS_FAIL_RESPONSE:
                message = "Server error: Couldn't retrieve users from the server";
                break;

            // retrieved list of users successfully
            case Codes.GET_USERS_SUCCESS_RESPONSE:
                proccessGetUsersSuccessResponse(responseData);
                break;

            // not a valid request format was sent to the server
            case Codes.NOT_VALID_REQUEST:
                message = "Server error: not a valid request";
                break;

            // unknown request was sent to the server
            case Codes.UNKNOWN_REQUEST:
                message = "Server error: unknown request";
                break;

            // Unknown response from the server. Should not get here but just in case.
            default:
                message = "Server error: unknown response";
                break;
        }
    }

    // proccess a successfull register response
    private static void proccessRegisterSuccessResponse(){
        System.out.println("Register successfull, now login");

        Controller._mainWindow.showLoginWindow();
    }

    // proccess a successfull login response
    private static void proccessLoginSuccessResponse(TreeMap<String, Object> response){
        String firstName = (String)response.get("first_name");
        String lastName = (String)response.get("last_name");

        Controller.getMe().setFirstName(firstName);
        Controller.getMe().setLastName(lastName);

        System.out.println("Welcome " + firstName + " " + lastName);

        Controller.getClient().proccessRequest(Codes.GET_USERS_REQUEST);

        Controller._mainWindow.showPostLoginDashboardWindow();
    }

    // proccess a successfull get list of users response
    private static void proccessGetUsersSuccessResponse(TreeMap<String, Object> response){
        @SuppressWarnings("unchecked")
        ArrayList<User> users = (ArrayList<User>) response.get("users_list");

        if(users != null){
            Controller.setUsers(users);
            Controller._chatDashboard.setListOfUsers();
        }

        else{
            message = "Unable to retrieve list of users";
            System.err.println("Unable to retrieve list of users");
        }
    }
}
