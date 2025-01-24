package com.client;

import java.util.TreeMap;

import com.classes.Codes;
import com.classes.ThisUser;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

// A controller for the login phase of the chat app
public class LoginController extends Controller{
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

    private static TreeMap<String, Object> _windows;

    @FXML
    public void initialize(){
    }

    @FXML
    public void login(){

        // TODO: write a better code, now the code is for testing

        if(username_login_text_field.getText().trim().isEmpty() || 
            password_login_text_field.getText().trim().isEmpty()){
            System.err.println("Empty textField");
        }

        else{
            _me = new ThisUser(username_login_text_field.getText(), password_login_text_field.getText() ,"", "");
            _client.proccessRequest(Codes.LOGIN_REQUEST);
            TreeMap<String, Object> response = _client.receiveResponse();

            if((int)response.get("response_code") == Codes.LOGIN_SUCCESS_RESPONSE){
                String firstName = (String)response.get("first_name");
                String lastName = (String)response.get("last_name");
                
                _me.setFirstName(firstName);
                _me.setLastName(lastName);

                System.out.println("Welcome " + firstName + " " + lastName);

                _mainWindow.showPostLoginDashboardWindow();
            }

            else if((int)response.get("response_code") == Codes.LOGIN_FAILED_RESPONSE){
                System.out.println("Login fail");
            }
        }
    }
}
