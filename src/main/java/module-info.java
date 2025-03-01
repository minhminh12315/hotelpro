module com.example.hotelpro {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jdi;

    opens controller.manager to javafx.fxml;
    opens com.example.hotelpro.manager to javafx.fxml;


    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens com.example.hotelpro to javafx.fxml;
    opens controller to javafx.fxml;

    exports com.example.hotelpro;
    exports controller;
    // Dương Minh Thêm
    opens model to javafx.base;
    exports model;
    opens controller.manager.employee to javafx.fxml;
}