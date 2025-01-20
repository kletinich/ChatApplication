package com.client;

/*
 * A parent class to all controllers.
 * Stores the Client class for communication with the server.
 */
public class Controller {
    protected static Client _client;


    public static void initClient(){
        _client = new Client();
    }

    public static Client getClient(){
        return _client;
    }
}
