package com.client;

import java.util.ArrayList;

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

    public static Client getClient(){
        return _client;
    }
    
    public static ThisUser getMe(){
        return _me;
    }
}
