package com.client;

import java.util.ArrayList;
import java.util.TreeMap;

import com.classes.Codes;
import com.classes.RequestPack;
import com.classes.ResponsePack;
import com.classes.User;

/********************************************************
 *                                                      *
 *                    *
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

     // proccess all the responses from the server
    public static void proccessResponse(TreeMap<String, Object> responseData){
        boolean isValidResponse = ResponsePack.isValidResponse(responseData);
        int responseCode;

        // not valid response format
        if(!isValidResponse){
            return;
        }

        // valid data. can be unpacked safetly

        responseCode = (int)responseData.get("response_code");

        switch(responseCode){
            case Codes.LOGIN_FAILED_RESPONSE:
                message = "Not valid username or password";
                break;

            case Codes.LOGIN_SUCCESS_RESPONSE:
                proccessLoginSuccessResponse(responseData);
                break;

            case Codes.GET_USERS_FAIL_RESPONSE:
                message = "Couldn't retrieve users from the server";
                break;

            case Codes.GET_USERS_SUCCESS_RESPONSE:
                proccessGetUsersSuccessResponse(responseData);
                break;

            case Codes.NOT_VALID_REQUEST:
                message = "Server error: not a valid request";
                break;

            case Codes.UNKNOWN_REQUEST:
                message = "Server error: unknown request";
                break;

            default:
                message = "Server error: unknown response";
                break;
        }
    }

    private static void proccessLoginSuccessResponse(TreeMap<String, Object> response){
        String firstName = (String)response.get("first_name");
        String lastName = (String)response.get("last_name");

        Controller.getMe().setFirstName(firstName);
        Controller.getMe().setLastName(lastName);

        System.out.println("Welcome " + firstName + " " + lastName);

        Controller.getClient().proccessRequest2(Codes.GET_USERS_REQUEST);

        Controller._mainWindow.showPostLoginDashboardWindow();
    }

    private static void proccessGetUsersSuccessResponse(TreeMap<String, Object> response){
        ArrayList<User> users = (ArrayList<User>) response.get("users_list");

        if(users != null){
            Controller.setUsers(users);
            Controller._chatDashboard.setListOfUsers();
        }

        else{
            System.err.println("Unable to retrieve list of users");
        }
    }
}
