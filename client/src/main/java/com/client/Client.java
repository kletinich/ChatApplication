package com.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;

import com.classes.*; 


public class Client {
    public static String DEFAULT_IP = "127.0.0.1";
    public static int DEFAULT_PORT = 8080;

    private String _ip;
    private int _port;

    private Socket _socket;

    private ObjectInputStream _in;
    private ObjectOutputStream _out;

    private ArrayList<User> _users;

    public Client(){
        this(DEFAULT_IP, DEFAULT_PORT);
    }

    public Client(String ip, int port){
        this._ip = ip;
        this._port = port;

        this._users = null;

        System.out.println("---------------------------------------------");
        System.out.println("Initialized client. Will try to connect to: ");
        System.out.println("IP Address: " + this._ip.toString() + ", Port: " + this._port);
        System.out.println("---------------------------------------------");
    }

    public void connectToServer(){
        try{
            this._socket = new Socket(this._ip, this._port);
            System.out.println("Connected to server");

            this._out = new ObjectOutputStream(this._socket.getOutputStream());
            this._in = new ObjectInputStream(this._socket.getInputStream());

        }catch(IOException e){
            System.err.println("Can't connect to server");
            System.exit(1);
        }
    }

    /*
     * Send a request to the server
     */
    public void proccessRequest(int requestCode){
        int responseCode = 0;
        TreeMap<String, Object> data;
        
        connectToServer();
        
        switch(requestCode){
            
            case Codes.LOGIN_REQUEST:
                if(Controller.getMe() == null){
                    System.err.println("Me is not initialized");
                    return;
                }
                
                data = registerRequest(Controller.getMe());
                sendRequest(data);

                break;
        }
    }

    /*
     * Send register request to the server
     */
    public TreeMap<String, Object> registerRequest(ThisUser me){

        String username = me.getUsername();
        String password = me.getPassword();

        return RequestPack.pack(Codes.LOGIN_REQUEST, username, password);
    }

    // Send the packed request data to the server
    public void sendRequest(TreeMap<String, Object> data){
        try {
            this._out.writeObject(data);
        } catch (IOException e) {
            System.out.println("Error sending data to server");
        }
    }

    @SuppressWarnings("unchecked")
    public void getUsersListFromServer(){
        try{
            Object data = this._in.readObject();
            if(data instanceof ArrayList){
                this._users = (ArrayList<User>)data;
            }

            else{
                System.err.println("Object received from server is not of type 'User List'");
            }
        }catch(Exception e){
            System.err.println("Error receiving data from server");
            System.err.println(e);
        }
    }

    public ArrayList<User> getUsers(){
        return this._users;
    }
}
