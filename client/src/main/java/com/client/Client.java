package com.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import com.classes.*; // bug: cant access elements even though i have the pom


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
    }

    public void connectToServer(){
                try{
            this._socket = new Socket(this._ip, this._port);
            System.out.println("Connected to server");

            this._out = new ObjectOutputStream(this._socket.getOutputStream());
            this._in = new ObjectInputStream(this._socket.getInputStream());
            getUsersListFromServer();

        }catch(IOException e){
            System.err.println("Can't connect to server");
            System.exit(1);
        }
    }

    @SuppressWarnings("unchecked")
    public void getUsersListFromServer(){
        try{
            Object data = this._in.readObject();
            if(data instanceof User){
                this._users = (ArrayList<User>)data;

                for(User user:_users){
                    System.out.print(user.getFirstName() + " ");
                    System.out.print(user.getLastName() + " ");
                    System.out.println(user.getUsername());
                }
            }

            else{
                System.err.println("Object received from server is not of type 'User'");
            }
        }catch(Exception e){
            System.err.println("Error receiving data from server");
            System.err.println(e);
        }
    }
}
