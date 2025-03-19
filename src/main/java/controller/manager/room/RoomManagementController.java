package controller.manager.room;

import controller.manager.MasterController;
import dao.BookingDao;
import dao.RoomDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Room;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomManagementController {

    @FXML
    private VBox root;

    // @FXML
    // private Button preOrderButton; // Đảm bảo khai báo nếu cần

    @FXML
    private VBox roomContainer;

    @FXML
    private TextField searchBar;  // Search bar field
    @FXML
    Pagination pagination;

    RoomDao roomDao = new RoomDao();
    private List<Room> allRooms = roomDao.getAll();
    private List<Room> filteredRooms = allRooms;

    private static final int ITEMS_PER_PAGE = 50;

    BookingDao bookingDao = new BookingDao();

    @FXML
    private void handleAddRoom() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/room/add-room.fxml"));
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

        pagination.setPageCount((int) Math.ceil((double) allRooms.size() / ITEMS_PER_PAGE));
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(5);

        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> displayRooms(newValue.intValue()));
    }

    @FXML
    private void handleSearch() {
        String query = searchBar.getText().toLowerCase().trim();

        filteredRooms = allRooms.stream()
                .filter(room -> String.valueOf(room.getRoomNumber()).contains(query) ||
                        room.getRoomType().toLowerCase().contains(query) ||
                        room.getStatus().toLowerCase().contains(query))
                .toList();
        displayRooms(0);
    }

    public void loadRoomData() {

        // Xóa dữ liệu cũ trong roomContainer
        roomContainer.getChildren().clear();

         HBox buttonContainer = new HBox();
    buttonContainer.setSpacing(10);
    buttonContainer.setStyle("-fx-alignment: center-right; -fx-padding: 10;");

    if (MasterController.userRole.equals("manager")) {
        // Add "Add Room" button
        Button addRoomButton = new Button("Add Room");
        addRoomButton.getStyleClass().add("btn-primary");
        addRoomButton.setOnAction(event -> handleAddRoom());
        buttonContainer.getChildren().add(addRoomButton);
    }

    // Add "Pre-order Room" button
    Button preOrderButton = new Button("Pre-order Room");
    preOrderButton.getStyleClass().add("btn-green");
    preOrderButton.setOnAction(event -> handlePreOrderButtonClick());
    buttonContainer.getChildren().add(preOrderButton);

    roomContainer.getChildren().add(buttonContainer);
    displayRooms(0);
    }

    public void displayRooms(int pageIndex) {
        // roomContainer.getChildren().clear();

        // Calculate starting index based on current page
        int startIndex = pageIndex * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, filteredRooms.size());

        // Organize rooms by floor
        // Button preOrderButton = new Button("Pre-order Room");
        // preOrderButton.setOnAction(event -> handlePreOrderButtonClick());
        // roomContainer.getChildren().add(preOrderButton);

        // Tổ chức phòng theo tầng
        Map<Integer, HBox> floorMap = new HashMap<>();

        for (int i = startIndex; i < endIndex; i++) {
            Room room = filteredRooms.get(i);
            int floor = room.getRoomNumber() / 100;
            int bookingID = -1;

            bookingID = getBookingIDFromRoomID(room.getRoomID());

            // Tạo HBox cho mỗi tầng nếu chưa có
            if (!floorMap.containsKey(floor)) {
                HBox floorBox = new HBox();
                floorBox.setSpacing(10);
                floorBox.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1px; -fx-background-color: #f4f4f4;");
                Label floorLabel = new Label("Floor " + floor);
                floorLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 5;");
                floorBox.getChildren().add(floorLabel);
                floorMap.put(floor, floorBox);
            }

            // Tạo VBox hiển thị phòng
            VBox roomBox = new VBox();
            roomBox.setPrefHeight(150);
            roomBox.setPrefWidth(120);

            String backgroundColor;
            switch (room.getStatus()) {
                case "Occupied":
                    backgroundColor = "red";
                    break;
                case "Available":
                    backgroundColor = "#41ff1f";
                    break;
                case "Maintenance":
                    backgroundColor = "yellow";
                    break;
                default:
                    backgroundColor = "gray";
                    break;
            }

            roomBox.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-color: black; -fx-border-width: 2px; -fx-padding: 5;");

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

            roomBox.setOnMouseClicked(event ->
            {
                ContextMenu contextMenu = new ContextMenu();
                MenuItem checkInItem = null;
                MenuItem checkOutItem = null;
                MenuItem addServiceItem = null;

                if (room.getStatus().equals("Occupied")) {
                    checkOutItem = new MenuItem("Check-out");
                    contextMenu.getItems().add(checkOutItem);
                } else if (room.getStatus().equals("Available")) {
                    checkInItem = new MenuItem("Check-in");
                    contextMenu.getItems().add(checkInItem);
                }
                if (room.getStatus().equals("Occupied")) {
                    System.out.println("Booking ID: " + finalBookingID);
                    addServiceItem = new MenuItem("Add Service");
                    contextMenu.getItems().add(addServiceItem);
                }

                if (checkInItem != null) {
                    checkInItem.setOnAction(_ -> handleCheckin(room));
                }
                if (checkOutItem != null) {
                    checkOutItem.setOnAction(_ -> handleCheckout(room));
                }
                if (addServiceItem != null) {
                    addServiceItem.setOnAction(_ -> handleAddService(finalBookingID));
                }

                MenuItem deleteRoomItem = new MenuItem("Xóa phòng");
                deleteRoomItem.setOnAction(_ -> handleDeleteRoom(room));



                contextMenu.getItems().add(deleteRoomItem);
                contextMenu.show(roomBox, event.getScreenX(), event.getScreenY());
            });
            // roomBox.setOnMouseClicked(event -> handleRoomClick(room));

            // Thêm roomBox vào HBox của tầng
            floorMap.get(floor).getChildren().add(roomBox);
        }

        for(Map.Entry<Integer, HBox> entry :floorMap.entrySet())
        {
            roomContainer.getChildren().add(entry.getValue());
        }

    }

    private int getBookingIDFromRoomID(int roomID) {
        return bookingDao.getActiveBookingIDByRoomID(roomID); // Truy vấn booking đang active
    }

    private void handleAddService(int bookingID) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/room/add-service.fxml"));
            Parent newContent = fxmlLoader.load();
            AddServiceController addServiceController = fxmlLoader.getController();
            addServiceController.setBookingID(bookingID);
            root.getChildren().setAll(newContent);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Error while adding service");
        }
    }

    private void handleSettings(Room room) {
    }

    private void handleCheckout(Room room) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/example/hotelpro/manager/room/checkout-room.fxml"));
            Parent newContent = fxmlLoader.load();

            CheckoutController checkoutController = fxmlLoader.getController();
            checkoutController.setRoomId(room.getRoomID());
            // check getBookingIDFromRoomID if null then alert error
            int bookingID = getBookingIDFromRoomID(room.getRoomID());
            if (bookingID == -1) {
                showAlert("Error", "No active booking found for this room");
                return;
            } else {
                checkoutController.setBookingId(bookingID);
            }

            root.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCheckin(Room room) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/example/hotelpro/manager/room/booking-room.fxml"));
            Parent newContent = fxmlLoader.load();

            BookingController bookingController = fxmlLoader.getController();
            bookingController.setRoomId(room.getRoomID());
            bookingController.setCheckIn(true);

            root.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCRUDRoom() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/example/hotelpro/manager/room/CRUD-room.fxml"));
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
        loadContent("/com/example/hotelpro/manager/room/pre-order-room.fxml");
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

    private void handleDeleteRoom(Room room) {
//        String currentUserRole = getCurrentUserRole();
//        // In ra để kiểm tra
//        System.out.println("Vai trò người dùng hiện tại: " + currentUserRole);
//
//        if (currentUserRole == null || !"manager".equals(currentUserRole)) {
//            showAlert("Lỗi", "Bạn không có quyền xóa phòng.");
//            return;
//        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa phòng");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa phòng số " + room.getRoomNumber() + " không?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                roomDao.delete(room);
                showAlert("Thành công", "Phòng đã được xóa.");
                loadRoomData();
            }
        });
    }
}