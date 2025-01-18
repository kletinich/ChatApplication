package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

// A controller for the login phase of the chat app
public class LoginController {
    @FXML
    private TextField username_login_text_field;

    @FXML
    private TextField password_login_text_field;

    @FXML
    private Button connect_btn;

    @FXML
    private Label forgot_password_label;

    @FXML
    private Label register_label;

    @FXML
    public void initialize(){
    }

    @FXML
    public void login(){
        if(username_login_text_field.getText().trim().isEmpty() || 
            password_login_text_field.getText().trim().isEmpty()){
            System.err.println("Empty textField");
        }

        else{
            System.out.println("OK");
        }
    }
}
