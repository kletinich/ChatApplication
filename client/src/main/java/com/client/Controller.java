package com.client;

import java.util.ArrayList;
import java.util.TreeMap;

import com.classes.Codes;
import com.classes.ThisUser;
import com.classes.User;

/*
 * A parent class to all controllers.
 * Stores the Client class for communication with the server.
 */
public class Controller {
    protected static Client _client;
    protected static ThisUser _me;
    protected static MainWindowController _mainWindow;
    protected static ChatDashboardController _chatDashboard;
    protected static ArrayList<User> _users;

    public static void initClient(){
        _client = new Client();
        _me = null;
        _users = null;
    }

    public static void initMe(String username, String password, String firstName, String lastName){
        _me = new ThisUser(username, password, firstName, lastName);
    }

    public static void setMainWindow(MainWindowController mainWindow){
        _mainWindow = mainWindow;
    }

    public static void setChatDashboardWindow(ChatDashboardController chatDashboard){
        _chatDashboard = chatDashboard;
    }

    // retrieve list of users from the server
    public static void getUsersFromServer(){
        _client.proccessRequest(Codes.GET_USERS_REQUEST);
        TreeMap<String, Object> response = _client.receiveResponse();

        _users = (ArrayList<User>) response.get("users_list");

        _chatDashboard.setListOfUsers();
        
    }

    public static Client getClient(){
        return _client;
    }
    
    public static ThisUser getMe(){
        return _me;
    }
}
