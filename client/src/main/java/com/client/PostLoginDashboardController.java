package com.client;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

/*
 * A controller for the post login phase of the chat app.
 * Controlls navigation through the app
 */
public class PostLoginDashboardController {
    
    @FXML
    private ImageView chat_icon;

    @FXML
    private ImageView quit_icon;

    @FXML
    public void initialize(){

    }

    @FXML
    public void quitIconClicked(){
        System.out.println("Quitting app");
        System.exit(0);
    }

    @FXML
    public void chatIconClicked(){
        System.out.println("Opening chats view");

        /*
         * to do:
         * 1. set the chats view to visible.
         * 2. set all the other views to unvisible
         */
    }
}
