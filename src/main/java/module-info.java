module com.example.hotelpro {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens com.example.hotelpro to javafx.fxml;
    opens controller to javafx.fxml;

    exports com.example.hotelpro;
    exports controller;
}