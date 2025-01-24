package com.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    public static Stage _stage = null;

    @Override
    public void start(Stage stage) throws Exception {
        /*Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("How you doin'?");
        stage.setScene(scene);
        Main._stage = stage;
        stage.show();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root = loader.load();

        // Get the controller
        MainWindowController mainWindowController = loader.getController();

        // Send the controller to the Main class
        Controller.setMainWindow(mainWindowController);

        // Set up the scene and stage
        Scene scene = new Scene(root);
        stage.setTitle("How you doin'?");
        stage.setScene(scene);
        Main._stage = stage;
        stage.show();
    }
    public static void main(String[] args) {
        // init the client to be accessed through all controllers
        Controller.initClient();
        
        // launch the main window
        launch(args);
    }

}