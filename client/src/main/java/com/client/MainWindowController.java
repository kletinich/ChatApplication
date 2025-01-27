package com.client;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/*
 * A controller for the main app window that controls all the sub-windows
 */
public class MainWindowController extends Controller{

    @FXML
    private VBox loginBox;

    @FXML
    private VBox registerBox; 

    @FXML
    private HBox postLoginDashboard;


    @FXML
    public void initialize(){
        loginBox.setVisible(true);
        registerBox.setVisible(false);
        postLoginDashboard.setVisible(false);
    }

    public void showLoginWindow(){
        loginBox.setVisible(true);
        registerBox.setVisible(false);
        postLoginDashboard.setVisible(false);
    }

    public void showRegisterWindow(){
        loginBox.setVisible(false);
        registerBox.setVisible(true);
        postLoginDashboard.setVisible(false);
    }

    public void showPostLoginDashboardWindow(){
        loginBox.setVisible(false);
        registerBox.setVisible(false);
        postLoginDashboard.setVisible(true);
    }

}
