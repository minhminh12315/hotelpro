package com.example.hotelpro;

import connect.Connect;
import connect.Migration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Connection connection = Connect.connection();
        if (connection == null) {
            System.out.println("Không tìm thấy cơ sở dữ liệu!");
        } else {
            if (!Connect.checkTable("Customer")) {
                Migration.migrate();
            } else {
                System.out.println("Cơ sở dữ liệu đã tồn tại!");
            }
        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/hotelpro/login/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}