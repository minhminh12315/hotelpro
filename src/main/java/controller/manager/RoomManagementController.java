package controller.manager;

import dao.BookingDao;
import dao.RoomDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Room;
import javafx.event.ActionEvent; // ✅ Đúng, dùng cho JavaFX

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.manager.room.AddServiceController;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class RoomManagementController {

    @FXML
    private VBox root;

    // @FXML
    // private Button preOrderButton; // Đảm bảo khai báo nếu cần

    @FXML
    private VBox roomContainer;

    RoomDao roomDao = new RoomDao();
    BookingDao bookingDao = new BookingDao();

    @FXML
    private void handleAddRoom() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/example/hotelpro/manager/add-room.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add New Room");
            stage.setScene(new Scene(parent));
            stage.show();

            // sau khi thêm phòng xong thì load lại dữ liệu
            stage.setOnHidden(event -> loadRoomData());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        loadRoomData();

    }

    public void loadRoomData() {
        List<Room> rooms = roomDao.getAll();

        // Xóa dữ liệu cũ trong roomContainer
        roomContainer.getChildren().clear();

        if (MasterController.userRole.equals("manager")) {
            // Thêm nút thêm phòng
            Button addRoomButton = new Button("Add Room");
            addRoomButton.setOnAction(event -> handleAddRoom());
            roomContainer.getChildren().add(addRoomButton);
        }

        Button preOrderButton = new Button("Pre-order Room");
        preOrderButton.setOnAction(event -> handlePreOrderButtonClick());
        roomContainer.getChildren().add(preOrderButton);

        // Tổ chức phòng theo tầng
        Map<Integer, HBox> floorMap = new HashMap<>();

        for (Room room : rooms) {
            int floor = room.getRoomNumber() / 100; // Lấy số tầng từ số phòng (101 -> tầng 1)
            int bookingID = -1;
            if (room.getStatus().equals("Occupied")) {
                bookingID = getBookingIDFromRoomID(room.getRoomID());
            }

            // Tạo HBox cho mỗi tầng nếu chưa có
            if (!floorMap.containsKey(floor)) {
                HBox floorBox = new HBox();
                floorBox.setSpacing(10);
                floorBox.setStyle(
                        "-fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1px; -fx-background-color: #f4f4f4;");
                Label floorLabel = new Label("Floor" + floor);
                floorLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 5;");
                floorBox.getChildren().add(floorLabel); // Thêm nhãn hiển thị tầng
                floorMap.put(floor, floorBox);
            }

            // Tạo VBox hiển thị phòng
            VBox roomBox = new VBox();
            roomBox.setPrefHeight(150);
            roomBox.setPrefWidth(120);

            roomBox.setStyle("-fx-background-color: " + (room.getStatus().equals("Occupied") ? "red" : "green")
                    + "; -fx-border-color: black; -fx-border-width: 2px; -fx-padding: 5;");

            Label roomNumberLabel = new Label("Room: " + room.getRoomNumber());
            Label priceLabel = new Label("Price: " + room.getPrice());
            Label capacityLabel = new Label("Capacity: " + room.getCapacity());
            Label typeLabel = new Label("Type: " + room.getRoomType());
            Label statusLabel = new Label("Status: " + room.getStatus());
            if (bookingID != -1) {
                statusLabel.setText(statusLabel.getText() + " (Booking ID: " + bookingID + ")");
            }

            roomBox.getChildren().addAll(roomNumberLabel, priceLabel, capacityLabel, typeLabel, statusLabel);
            final int finalBookingID = bookingID;
            // Gán sự kiện click cho roomBox
            // show dropdown with 3 options: check-in or check-out, settings, add service
            roomBox.setOnMouseClicked(
                    event -> {
                        ContextMenu contextMenu = new ContextMenu();
                        MenuItem checkInItem = null;
                        MenuItem checkOutItem = null;

                        if (room.getStatus().equals("Occupied")) {
                            checkOutItem = new MenuItem("Check-out");
                            contextMenu.getItems().add(checkOutItem);
                        } else if (room.getStatus().equals("Available")) {
                            checkInItem = new MenuItem("Check-in");
                            contextMenu.getItems().add(checkInItem);
                        }

                        MenuItem settingsItem = new MenuItem("Settings");
                        MenuItem addServiceItem = new MenuItem("Add Service");

                        if (checkInItem != null) {
                            checkInItem.setOnAction(e -> handleCheckin(room));
                        }
                        if (checkOutItem != null) {
                            checkOutItem.setOnAction(e -> handleCheckout(room));
                        }
                        settingsItem.setOnAction(e -> handleSettings(room));
                        if (room.getStatus().equals("Occupied") && finalBookingID != -1) {
                            addServiceItem.setOnAction(e -> {
                                handleAddService(finalBookingID);
                            });
                        }

                        contextMenu.getItems().addAll(settingsItem, addServiceItem);
                        contextMenu.show(roomBox, event.getScreenX(), event.getScreenY());
                    });

            // roomBox.setOnMouseClicked(event -> handleRoomClick(room));

            // Thêm roomBox vào HBox của tầng
            floorMap.get(floor).getChildren().add(roomBox);
        }

        // Thêm tất cả các tầng vào roomContainer
        for (Map.Entry<Integer, HBox> entry : floorMap.entrySet()) {
            roomContainer.getChildren().add(entry.getValue());
        }
    }

    private int getBookingIDFromRoomID(int roomID) {

        return bookingDao.getActiveBookingIDByRoomID(roomID); // Truy vấn booking đang active
    }

    private void handleAddService(int bookingID) {

        showAlert("Add Service", "Add service to booking ID: " + bookingID);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/example/hotelpro/manager/room/add-service.fxml"));
            Parent newContent = fxmlLoader.load();


            AddServiceController addServiceController = fxmlLoader.getController();
            addServiceController.setBookingID(bookingID);

            root.getChildren().setAll(newContent);
        } catch (Exception e) {
            showAlert("Error", "Error while adding service");
        }


    }

    private void handleSettings(Room room) {
    }

    private void handleCheckout(Room room) {
    }

    private void handleCheckin(Room room) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/example/hotelpro/manager/booking-room.fxml"));
            Parent newContent = fxmlLoader.load();

            BookingController bookingController = fxmlLoader.getController();
            bookingController.setRoomId(room.getRoomID());

            root.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCRUDRoom() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/example/hotelpro/manager/CRUD-room.fxml"));
            Parent newContent = fxmlLoader.load();

            root.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stage stage;
    private Scene scene;

    @FXML
    public void handlePreOrderButtonClick() {
        loadContent("/com/example/hotelpro/manager/pre-order-room.fxml");
    }

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newContent = fxmlLoader.load();
            root.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}