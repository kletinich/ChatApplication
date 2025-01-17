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
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("How you doin'?");
        stage.setScene(scene);
        this._stage = stage;
        stage.show();
    }
    public static void main(String[] args) {
        //Client client = new Client();
        //client.connectToServer();

        launch(args);
    }

}