package com.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.TreeMap;

import com.classes.*;


public class Client implements Runnable{
    public static String DEFAULT_IP = "127.0.0.1";
    public static int DEFAULT_PORT = 8080;

    private String _ip;
    private int _port;

    private Socket _socket;

    private ObjectInputStream _in;
    private ObjectOutputStream _out;

    private ArrayList<User> _users;

    private int _requestCodeToRun;
    private int _runStatus;

    public Client(){
        this(DEFAULT_IP, DEFAULT_PORT);
    }

    public Client(String ip, int port){
        this._ip = ip;
        this._port = port;

        this._users = null;

        this._runStatus = 0;
        this._requestCodeToRun = 0;

        System.out.println("---------------------------------------------");
        System.out.println("Initialized client. Will try to connect to: ");
        System.out.println("IP Address: " + this._ip.toString() + ", Port: " + this._port);
        System.out.println("---------------------------------------------");
    }

    @SuppressWarnings("exports")
    public ArrayList<User> getUsers(){
        return this._users;
    }

    /*
     * Proccess requests of the client:
     * connect to server
     * proccess the request
     * pack the request
     * receive response from the server
     * proccess the response
     */
    public int proccessRequest(int requestCode){
        TreeMap<String, Object> data;
        
        // connect and send the request to the server
        int connectStatus = connectToServer();

        // connected successfully to the server
        if(connectStatus == Codes.CONNECTION_SUCCESS){
            data = RequestResponseProcessor.proccessRequest(requestCode);

            if(data != null){
                sendRequest(data);
            }

            // receive and proccess the response
            data = receiveResponse();

            // close connection before proccessing the data
            closeConnection();

            // proccess the data received from the server
            RequestResponseProcessor.proccessResponse(data);

            return Codes.CONNECTION_SUCCESS;
        }

        // didn't connect to the server
        return Codes.CONNECTION_ERROR;
    }

    // connect to the server 
    public int connectToServer(){
        try{
            this._socket = new Socket(this._ip, this._port);
            System.out.println("Connected to server");

            this._out = new ObjectOutputStream(this._socket.getOutputStream());
            this._in = new ObjectInputStream(this._socket.getInputStream());

            return Codes.CONNECTION_SUCCESS;
            
        }catch(IOException e){
            System.err.println("Can't connect to server");
            return Codes.CONNECTION_ERROR;
        }
    }

    public void closeConnection(){
        try {
            this._socket.close();
        } catch (IOException e) {
            System.out.println("Closed connection with the server");
        }
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
            @SuppressWarnings("unchecked")
            TreeMap<String, Object> responseData = (TreeMap<String, Object>) this._in.readObject();
            return responseData;
        } catch (IOException e) {
            System.out.println("Error receiving data from server");
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found");
        }

        return null;
    }

    @Override
    public void run() {
        this._runStatus = this.proccessRequest(this._requestCodeToRun);
    }

    public void setRequestCodeToRun(int code){
        this._requestCodeToRun = code;
    }

    public int getRunStatus(){
        return this._runStatus;
    }
}
