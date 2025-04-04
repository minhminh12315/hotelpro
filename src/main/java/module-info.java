module com.example.hotelpro {
    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    // Hibernate and Jakarta modules
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;

    // JDK modules
    requires jdk.jdi;
    requires java.desktop;

    // Exported packages
    exports com.example.hotelpro;
    exports controller;
    exports controller.manager.product;
    exports controller.manager.customer;
    exports model;
    exports model.additional;

    // Open specific packages for reflection (JavaFX FXML)
    opens com.example.hotelpro to javafx.fxml;
    opens controller to javafx.fxml;
    opens controller.manager to javafx.fxml;
    opens controller.manager.product to javafx.fxml;
    opens controller.manager.employee to javafx.fxml;
    opens controller.manager.customer to javafx.fxml;
    opens controller.manager.service to javafx.fxml;
    opens controller.manager.booking to javafx.fxml;
    opens controller.manager.room to javafx.fxml;
    opens model to javafx.base;
    opens model.additional to javafx.base;
}
