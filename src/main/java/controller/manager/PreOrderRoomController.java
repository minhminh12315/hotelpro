package controller.manager;

import dao.RoomDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Room;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PreOrderRoomController {

    @FXML
    private VBox root;

    @FXML
    private VBox roomContainer;

    @FXML
    private void initialize() {
        loadRoomData();
    }

    public void loadRoomData() {
        RoomDao roomDao = new RoomDao();
        List<Room> rooms = roomDao.getAll();
        roomContainer.getChildren().clear();
        Map<Integer, HBox> floorMap = new HashMap<>();

        for (Room room : rooms) {
            int floor = room.getRoomNumber() / 100;
            if (!floorMap.containsKey(floor)) {
                HBox floorBox = new HBox();
                floorBox.setSpacing(10);
                floorBox.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1px; -fx-background-color: #f4f4f4;");
                Label floorLabel = new Label("Floor" + floor);
                floorLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 5;");
                floorBox.getChildren().add(floorLabel);
                floorMap.put(floor, floorBox);
            }

            // Tạo VBox hiển thị phòng
            VBox roomBox = new VBox();
            roomBox.setPrefHeight(150);
            roomBox.setPrefWidth(120);

            roomBox.setStyle("-fx-background-color: " + (room.getStatus().equals("Occupied") ? "red" : "green") + "; -fx-border-color: black; -fx-border-width: 2px; -fx-padding: 5;");

            Label roomNumberLabel = new Label("Room: " + room.getRoomNumber());
            Label priceLabel = new Label("Price: " + room.getPrice());
            Label capacityLabel = new Label( "Capacity: " + room.getCapacity());
            Label typeLabel = new Label("Type: " + room.getRoomType());
            Label statusLabel = new Label("Status: " + room.getStatus());

            roomBox.getChildren().addAll(roomNumberLabel, priceLabel, capacityLabel, typeLabel, statusLabel);

            // Gán sự kiện click cho roomBox
            roomBox.setOnMouseClicked(event -> handleRoomClick(room));
            floorMap.get(floor).getChildren().add(roomBox);
        }
        for (Map.Entry<Integer, HBox> entry : floorMap.entrySet()) {
            roomContainer.getChildren().add(entry.getValue());
        }
    }

    @FXML
    private void handleRoomClick(Room room) {
        if (room.getStatus().equals("Occupied")) {
            System.out.println("Phòng đang được sử dụng: " + room.getRoomNumber());
            handleCheckout(room);
        } else {
            System.out.println("Phòng trống: " + room.getRoomNumber());
            handleCheckin(room);
        }
    }

    private void handleCheckout(Room room) {
    }

    private void handleCheckin(Room room) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/booking-room.fxml"));
            Parent newContent = fxmlLoader.load();

            BookingController bookingController = fxmlLoader.getController();
            bookingController.setRoomId(room.getRoomID());

            root.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
