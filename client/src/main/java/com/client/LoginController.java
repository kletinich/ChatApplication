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
            this.username_error_label.setText("");
            this.password_error_label.setText("");

            // process the request and receive response from the server
            _me = new ThisUser(username_login_text_field.getText(), password_login_text_field.getText() ,"", "");
            _client.proccessRequest(Codes.LOGIN_REQUEST);

            // to do: rceive response in client class, not here
            TreeMap<String, Object> response = _client.receiveResponse();
            int responseCode = (int)response.get("response_code");
            
            switch(responseCode){

                // Login success
                case Codes.LOGIN_SUCCESS_RESPONSE:
                    String firstName = (String)response.get("first_name");
                    String lastName = (String)response.get("last_name");
                    
                    _me.setFirstName(firstName);
                    _me.setLastName(lastName);

                    System.out.println("Welcome " + firstName + " " + lastName);

                    // retrieve list of users for future chats
                    Controller.getUsersFromServer();

                    // switch to postLoginDasboard window
                    _mainWindow.showPostLoginDashboardWindow();
                    break;  
                    
                // Login failed - not valid username or password
                case Codes.LOGIN_FAILED_RESPONSE:
                    this.login_error_label.setText("Not valid username or password");
                    break;

                // Login failed - not a valid request format
                case Codes.NOT_VALID_REQUEST:
                    this.login_error_label.setText("Communication error: not a valid request"); 
                    break;

                // Login failed - unknown request
                case Codes.UNKNOWN_REQUEST:
                    this.login_error_label.setText("Communication error: unknown request");
                    break;

                // Login failed - unknown erro
                default:
                    this.login_error_label.setText("Communication error: unknown error"); 
                    break;
            }
        }
    }
}
