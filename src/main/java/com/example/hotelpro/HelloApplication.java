package com.example.hotelpro;

import connect.Connect;
import connect.Migration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
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
        Scene scene = new Scene(fxmlLoader.load());

        // title
        stage.setTitle("Hello!");
        stage.setScene(scene);

        // css tung thang
        scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/duongminh.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/quangminh.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/quang.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/css/haianh.css").toExternalForm());

        // Lấy độ phân giải màn hình
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // Đặt kích thước ứng dụng khớp với màn hình
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        // Không dùng full-screen
        // stage.setFullScreen(true); // Không sử dụng dòng này
        stage.setFullScreen(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}