package com.client;

import com.classes.ThisUser;

/*
 * A parent class to all controllers.
 * Stores the Client class for communication with the server.
 */
public class Controller {
    protected static Client _client;
    protected static ThisUser _me;


    public static void initClient(){
        _client = new Client();
        _me = null;
    }

    public static Client getClient(){
        return _client;
    }

    public static ThisUser getMe(){
        return _me;
    }
}
