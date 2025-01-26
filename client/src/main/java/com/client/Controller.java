package com.client;

import java.util.ArrayList;
import java.util.TreeMap;

import com.classes.Codes;
import com.classes.ThisUser;
import com.classes.User;

/*
 * A parent class to all controllers.
 * Stores variables to be used by the controllers
 */
public class Controller {
    protected static Client _client = null;            // client for communication
    protected static ThisUser _me = null;              // data of the user
    protected static ArrayList<User> _users = null;    // data of all the other users
    
    protected static MainWindowController _mainWindow = null;          // main window controller
    protected static ChatDashboardController _chatDashboard = null;    // chat dashboard controller

    // init the client 
    public static void initClient(){
        _client = new Client();
    }

    // init the user with the given data
    public static void initMe(String username, String password, String firstName, String lastName){
        _me = new ThisUser(username, password, firstName, lastName);
    }

    // set the main window controller to be accessed by other controllers
    public static void setMainWindow(MainWindowController mainWindow){
        _mainWindow = mainWindow;
    }

    // set the chat dahsboard controller to be accessed by other controllers
    public static void setChatDashboardWindow(ChatDashboardController chatDashboard){
        _chatDashboard = chatDashboard;
    }

    // retrieve list of users from the server
    public static void getUsersFromServer(){
        _client.proccessRequest(Codes.GET_USERS_REQUEST);
        TreeMap<String, Object> response = _client.receiveResponse();

        _users = (ArrayList<User>) response.get("users_list");

        if(_users != null){
            _chatDashboard.setListOfUsers();
        }

        else{
            // to do: write a check response in responsePack class
            System.err.println("Unable to retrieve list of users");
        }
        
    }

    public static Client getClient(){
        return _client;
    }
    
    @SuppressWarnings("exports")
    public static ThisUser getMe(){
        return _me;
    }
}
