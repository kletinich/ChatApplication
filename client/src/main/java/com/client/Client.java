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
        TreeMap<String, Object> data;
        
        connectToServer();
        data = RequestResponseProcessor.proccessRequest(requestCode);
        sendRequest(data);
    }

    // Send the packed request data to the server
    public void sendRequest(TreeMap<String, Object> data){
        try {
            this._out.writeObject(data);
        } catch (IOException e) {
            System.out.println("Error sending data to server");
        }
    }

    // receive a response from the server
    public TreeMap<String, Object> receiveResponse(){
        try {
            TreeMap<String, Object> responseData = (TreeMap<String, Object>) this._in.readObject();
            return responseData;
        } catch (IOException e) {
            System.out.println("Error receiving data from server");
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found");
        }

        return null;
    }

    public ArrayList<User> getUsers(){
        return this._users;
    }
}
