package com.client;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/*
 * A controller for the main app window that controls all the sub-windows
 */
public class ChatWindowController extends Controller{

    @FXML
    private VBox loginBox;

    @FXML
    private HBox postLogingDashboard;

    @FXML
    public void initialize(){

        // temp because no login phase for this version
        this.loginBox.setVisible(false);
        //this.postLogingDashboard.setVisible(true);
    }
 
   
}
