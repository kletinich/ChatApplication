package com.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow extends Application{

    public static Stage _stage = null;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("src\\main\\resources\\com\\client\\ChatWindow.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        this._stage = stage;
        stage.show();
    }
    
}
