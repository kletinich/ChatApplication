package com.client;

import com.classes.Codes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController extends Controller {

    @FXML
    private TextField username_text_field;

    @FXML
    private PasswordField password_text_field;

    @FXML
    private PasswordField confirm_password_text_field;

    @FXML
    private TextField first_name_text_field;

    @FXML
    private TextField last_name_text_field;

    @FXML
    private Button register_button;

    @FXML
    private Label username_error_label;

    @FXML
    private Label password_error_label;

    @FXML
    private Label confirm_password_error_label;

    @FXML
    private Label first_name_error_label;

    @FXML
    private Label last_name_error_label;

    @FXML
    private Label register_error_label;


    @FXML
    public void initialize(){

    }

    @FXML 
    public void moveToLoginWindow(){
        _mainWindow.showLoginWindow();
    }

    @FXML
    public void register(){
        this.username_error_label.setText("");
        this.password_error_label.setText("");
        this.confirm_password_error_label.setText("");
        this.first_name_error_label.setText("");
        this.last_name_error_label.setText("");

        register_error_label.setText("");

        if(this.username_text_field.getText().trim().isEmpty()){
            this.username_error_label.setText("Empty field");
        }

        if(this.password_text_field.getText().trim().isEmpty()){
            this.password_error_label.setText("Empty field");
        }

        if(this.confirm_password_text_field.getText().trim().isEmpty()){
            this.confirm_password_error_label.setText("Empty field");
        }

        if(this.first_name_text_field.getText().trim().isEmpty()){
            this.first_name_error_label.setText("Empty field");
        }

        if(this.last_name_text_field.getText().trim().isEmpty()){
            this.last_name_error_label.setText("Empty field");
        }

        if(!this.password_text_field.getText().trim().equals(this.confirm_password_text_field.getText().trim())){
            this.confirm_password_error_label.setText("Passwords don't match");
        }

        // can process the register request
        if(!username_text_field.getText().trim().isEmpty() && !password_text_field.getText().trim().isEmpty() && 
            !confirm_password_text_field.getText().trim().isEmpty() && !first_name_text_field.getText().trim().isEmpty() &&
            !last_name_text_field.getText().trim().isEmpty()){

            _me.setFirstName(first_name_text_field.getText().trim());
            _me.setLastName(last_name_text_field.getText().trim());
            _me.setUsername(username_text_field.getText().trim());
            _me.setPassword(password_text_field.getText().trim());
            
            // send the request to the server and proccess the response
            int connectStatus = _client.proccessRequest(Codes.REGISTER_REQUEST);

            // didn't connect to the server
            if(connectStatus == Codes.CONNECTION_ERROR){
                this.register_error_label.setText("Connection error: can't connect to the server");
            }

            // for potential error messages
            else{
                this.register_error_label.setText(RequestResponseProcessor.getMessage());
            }

        }
    }

}
