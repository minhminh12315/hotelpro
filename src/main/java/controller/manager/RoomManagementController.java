package controller.manager;

import dao.RoomDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Room;

import java.io.IOException;
import java.util.List;

public class RoomManagementController {

    @FXML
    private VBox roomContainer;

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
    public void handleRoomClick(MouseEvent mouseEvent) {
        System.out.println("Room clicked!");

    }

    @FXML
    private void initialize() {
        loadRoomData();
    }

    private void loadRoomData() {
        RoomDao roomDao = new RoomDao();
        List<Room> rooms = roomDao.getAll();

        for (Room room : rooms) {
            VBox roomBox = new VBox();
            roomBox.setPrefHeight(150);
            roomBox.setPrefWidth(120);

            roomBox.setStyle("-fx-background-color: " + (room.getStatus().equals("occupied") ? "red" : "green") + "; -fx-border-color: black; -fx-border-width: 2px; -fx-padding: 5;");

            Label roomNumberLabel = new Label("Room: " + room.getRoomNumber());
            Label priceLabel = new Label("Price: " + room.getPrice());
            Label capacityLabel = new Label("Capacity: " + room.getCapacity());
            Label typeLabel = new Label("Type: " + room.getRoomType());
            Label statusLabel = new Label("Status: " + room.getStatus());

            roomBox.getChildren().addAll(roomNumberLabel, priceLabel, capacityLabel, typeLabel, statusLabel);
            roomBox.setOnMouseClicked(event -> handleRoomClick(event));

            roomContainer.getChildren().add(roomBox);


            // Add roomBox to the appropriate container in your UI
            // For example, if you have a VBox with fx:id="roomContainer":
            // roomContainer.getChildren().add(roomBox);
        }
    }

}
