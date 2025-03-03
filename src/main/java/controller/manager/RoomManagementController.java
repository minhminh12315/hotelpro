package controller.manager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RoomManagementController {

    @FXML
    private void handleAddRoom() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/add-room.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add New Room");
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRoomClick(javafx.scene.input.MouseEvent event) {
        VBox selectedRoom = (VBox) event.getSource(); // Lấy VBox của phòng được bấm vào
        Label roomLabel = (Label) selectedRoom.getChildren().get(0); // Lấy ID phòng
        Label priceLabel = (Label) selectedRoom.getChildren().get(1); // Lấy giá phòng

        String roomId = roomLabel.getText(); // Số phòng
        String priceText = priceLabel.getText().replace("Giá: ", "").replace(".", "");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/booking-room.fxml"));
            Parent bookingRoot = loader.load();

            // Lấy controller của trang Booking
            BookingController bookingController = loader.getController();
            bookingController.setRoomInfo(roomId, priceText);

            // Chuyển sang trang booking
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(bookingRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
