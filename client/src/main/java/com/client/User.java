package com.client;

import java.io.Serializable;

/*
 * A user class for storing user data for the chats
 */
public class User implements Serializable{
    protected String _firstName;  // real first name
    protected String _lastName;   // real last name

    protected String _username;   // username to be displayed

    public User(String username){
        this("", "", username);
    }

    public User(String firstName, String lastName, String username){
        this._firstName = firstName;
        this._lastName = lastName;
        this._username = username;
    }

    public String getFirstName(){
        return this._firstName;
    }

    public String getLastName(){
        return this._lastName;
    }

    public String getUsername(){
        return this._username;
    }

    public void setFirstName(String firstName){
        this._firstName = firstName;
    }

    public void setLastName(String lastName){
        this._lastName = lastName;
    }
}
