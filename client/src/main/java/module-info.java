module com.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires classes;

    opens com.client to javafx.fxml;
    exports com.client;
}
