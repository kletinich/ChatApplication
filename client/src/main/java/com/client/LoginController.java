package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
        if(this.username_login_text_field.getText().isEmpty() || 
            this.password_login_text_field.getText().isEmpty()){
                System.out.println("Error");
            }

        else{
            System.out.println("Success");
        }
    }
}
