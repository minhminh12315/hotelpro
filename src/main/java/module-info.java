module com.example.hotelpro {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.hotelpro to javafx.fxml;
    exports com.example.hotelpro;
}