module com.example.hotelpro {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jdi;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.hotelpro to javafx.fxml;
    exports com.example.hotelpro;
    exports controller;
    opens model to javafx.base;
    exports model;
    opens controller.manager.employee to javafx.fxml;
}