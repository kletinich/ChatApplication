package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/*
 * A controller for viewing all the open chats, and chat with one of the chats
 */
public class ChatDashboardController {
    
    @FXML
    private ListView listOfChats;

    @FXML
    private ScrollPane chatScroller;

    @FXML
    private ImageView search_icon;

    @FXML
    private TextField searchBar;

    @FXML
    private VBox chatBox;

    public void initialize(){
    }
}
