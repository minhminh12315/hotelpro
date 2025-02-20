module com.example.hotelpro {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.hotelpro to javafx.fxml;
    opens controller to javafx.fxml;

    exports com.example.hotelpro;
    exports controller;
}