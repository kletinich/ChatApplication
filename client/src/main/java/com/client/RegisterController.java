package com.client;

import javafx.fxml.FXML;

public class RegisterController extends Controller {

    @FXML
    public void initialize(){

    }

    @FXML 
    public void moveToLoginWindow(){
        _mainWindow.showLoginWindow();
    }

}
