package com.client;

import com.classes.ThisUser;

/*
 * A parent class to all controllers.
 * Stores the Client class for communication with the server.
 */
public class Controller {
    protected static Client _client;
    protected static ThisUser _me;
    protected static MainWindowController _mainWindow;

    public static void initClient(){
        _client = new Client();
        _me = null;
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
