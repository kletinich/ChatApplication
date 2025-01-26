package com.client;

import java.util.TreeMap;

import com.classes.Codes;
import com.classes.ThisUser;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

// A controller for the login phase of the chat app
public class LoginController extends Controller{
    @FXML
    private TextField username_login_text_field;

    @FXML
    private PasswordField password_login_text_field;

    @FXML
    private Button connect_btn;

    @FXML
    private Label forgot_password_label;

    @FXML
    private Label register_label;

    @FXML
    private Label username_error_label;

    @FXML
    private Label password_error_label;

    @FXML
    private Label login_error_label;

    @FXML
    public void initialize(){
    }

    @FXML
    public void login(){
        if(username_login_text_field.getText().trim().isEmpty()){
            this.username_error_label.setText("Empty field");
        }

        if(password_login_text_field.getText().trim().isEmpty()){
            this.password_error_label.setText("Empty field");
        }

        // can process the login request
        if(!username_login_text_field.getText().trim().isEmpty() && !username_login_text_field.getText().trim().isEmpty()){
            _me = new ThisUser(username_login_text_field.getText(), password_login_text_field.getText() ,"", "");
            _client.proccessRequest(Codes.LOGIN_REQUEST);
            this.login_error_label.setText(RequestResponseProcessor.getMessage());
        }
    }
}
