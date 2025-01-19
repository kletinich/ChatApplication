package com.classes;

public class ThisUser extends User{
    private String _password;

    public ThisUser(String username, String password){
        super(username);
        this._password = password;
    }

    public ThisUser(String username, String password, String firstName, String lastName){
        super(firstName, lastName, username);
        this._password = password;
    }

    public String getPassword(){
        return this._password;
    }

    public void setUsername(String username){
        this._username = username;
    }

    public void setPassword(String password){
        this._password = password;
    }
}
