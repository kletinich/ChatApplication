package com.client;

import com.classes.User;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

/*
 * A controller for viewing all the open chats, and chat with one of the chats
 */
public class ChatDashboardController extends Controller {
    
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
        _chatDashboard = this;

        listOfChats.setOnMouseClicked((MouseEvent event) -> {
            // Check if an item is selected
            Label selectedLabel = (Label) listOfChats.getSelectionModel().getSelectedItem();
            if (selectedLabel != null) {

                // to do: add click event to open chatbox
                // Trigger an action, such as showing the chat
                String selectedChat = selectedLabel.getText();
                System.out.println("Selected Chat: " + selectedChat);

            }
        });
    }

    public void setListOfUsers(){
        for(User user: _users){
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String username = user.getUsername();


            if(!_me.getUsername().equals(username)){
                this.listOfChats.getItems().add(new Label(firstName + " " + lastName));
            }
        }
    }
}
