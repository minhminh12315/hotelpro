package controller.manager.room;

import controller.manager.MasterController;
import dao.BookingDao;
import dao.RoomDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import java.util.Set;
import java.util.stream.Collectors;

public class RoomManagementController {

    @FXML
    public VBox root;

    @FXML
    HBox buttonContainer;

    @FXML
    private VBox roomContainer;

    @FXML
    private TextField searchBar;  // Search bar field
    @FXML
    Pagination pagination;

    RoomDao roomDao = new RoomDao();
    private List<Room> allRooms = roomDao.getAll();
    private List<Room> filteredRooms = allRooms;

    private static final int ROOMS_PER_PAGE = 20;  // Changed from ITEMS_PER_PAGE
    private static final int MAX_ROOMS_PER_FLOOR = 5;  // Maximum rooms per row in a floor

    BookingDao bookingDao = new BookingDao();

    @FXML
    public void initialize() {
        loadRoomData();

        // Calculate unique floors in the filtered rooms
        List<Integer> sortedFloors = filteredRooms.stream()
                .map(room -> room.getRoomNumber() / 100)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        // Calculate page count (2 floors per page)
        int pageCount = (int) Math.ceil((double) sortedFloors.size() / 2);
        pagination.setPageCount(pageCount);
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(Math.min(5, pageCount));

        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            displayRooms(newValue.intValue());
        });
    }

    @FXML
    private void handleSearch() {
        String query = searchBar.getText().toLowerCase().trim();

        filteredRooms = allRooms.stream()
                .filter(room -> String.valueOf(room.getRoomNumber()).contains(query) ||
                        room.getRoomType().toLowerCase().contains(query) ||
                        room.getStatus().toLowerCase().contains(query))
                .collect(Collectors.toList());

        initialize();
    }

    public void loadRoomData() {
        // Check if buttonContainer is already added, and if not, add it
        if (roomContainer.getChildren().isEmpty()) {
            buttonContainer = new HBox();
            buttonContainer.setId("buttonContainer");
            buttonContainer.setSpacing(10);
            buttonContainer.setStyle("-fx-alignment: center-right; -fx-padding: 10;");

            // Add "Add Room" button for manager role
            if (MasterController.userRole.equals("manager")) {
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

            // Add button container to roomContainer
            roomContainer.getChildren().add(buttonContainer);
        }

        displayRooms(0);
    }

    public void displayRooms(int pageIndex) {
        // Clear only the rooms, not the buttons
        ObservableList<Node> roomChildren = roomContainer.getChildren();
        roomChildren.removeIf(node -> !(node == buttonContainer));

        // Sort floors and get unique floors
        List<Integer> sortedFloors = filteredRooms.stream()
                .map(room -> room.getRoomNumber() / 100)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        // If pageIndex is out of bounds, reset to 0
        if (pageIndex >= Math.ceil((double) sortedFloors.size() / 2)) {
            pageIndex = 0;
        }

        // Calculate floors for the current page
        int startFloorIndex = pageIndex * 2;
        int endFloorIndex = Math.min(startFloorIndex + 2, sortedFloors.size());

        // Display floors for the current page
        for (int i = startFloorIndex; i < endFloorIndex; i++) {
            int currentFloor = sortedFloors.get(i);

            // Filter rooms for the current floor
            List<Room> floorRooms = filteredRooms.stream()
                    .filter(room -> room.getRoomNumber() / 100 == currentFloor)
                    .collect(Collectors.toList());

            // Create floor VBox
            VBox floorVBox = new VBox();
            floorVBox.setSpacing(10);
            floorVBox.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1px; -fx-background-color: #f4f4f4;");

            Label floorLabel = new Label("Floor " + currentFloor);
            floorLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 5;");
            floorVBox.getChildren().add(floorLabel);

            // Current row for rooms
            HBox currentRowBox = new HBox();
            currentRowBox.setSpacing(10);
            floorVBox.getChildren().add(currentRowBox);

            // Add rooms to the floor
            for (Room room : floorRooms) {
                // If current row is full, create a new row
                if (currentRowBox.getChildren().size() >= MAX_ROOMS_PER_FLOOR) {
                    currentRowBox = new HBox();
                    currentRowBox.setSpacing(10);
                    floorVBox.getChildren().add(currentRowBox);
                }

                // Create and add room box
                int bookingID = getBookingIDFromRoomID(room.getRoomID());
                VBox roomBox = createRoomBox(room, bookingID);
                currentRowBox.getChildren().add(roomBox);
            }

            // Add floor box to room container
            roomContainer.getChildren().add(floorVBox);
        }
    }

    private VBox createRoomBox(Room room, int bookingID) {
        VBox roomBox = new VBox();
        roomBox.setPrefHeight(150);
        roomBox.setPrefWidth(223);

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

        Label roomNumberLabel = new Label(String.valueOf(room.getRoomNumber()));
        roomNumberLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        roomNumberLabel.setMaxWidth(Double.MAX_VALUE);
        roomNumberLabel.setAlignment(javafx.geometry.Pos.CENTER);

        Label priceLabel = new Label("Price: " + room.getPrice());
        Label capacityLabel = new Label("Capacity: " + room.getCapacity());
        Label typeLabel = new Label("Type: " + room.getRoomType());
        Label statusLabel = new Label("Status: " + room.getStatus());

        if (bookingID != -1) {
            statusLabel.setText(statusLabel.getText() + " (Booking ID: " + bookingID + ")");
        }

        roomBox.getChildren().addAll(roomNumberLabel, priceLabel, capacityLabel, typeLabel, statusLabel);
        final int finalBookingID = bookingID;

        roomBox.setOnMouseClicked(event -> {
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

            MenuItem settingsItem = new MenuItem("Settings");
            settingsItem.setOnAction(_ -> handleSettings(room));

            contextMenu.getItems().addAll(settingsItem);
            contextMenu.show(roomBox, event.getScreenX(), event.getScreenY());
        });

        return roomBox;
    }

    private int getBookingIDFromRoomID(int roomID) {
//        System.out.println("Checking booking for Room ID: " + roomID);  // Debugging log
        int bookingID = bookingDao.getActiveBookingIDByRoomID(roomID);
//        System.out.println("Returned booking ID: " + bookingID);  // Debugging log
        return bookingID;
    }

    private void handleAddService(int bookingID) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/room/add-service.fxml"));
            Parent newContent = fxmlLoader.load();
            AddServiceController addServiceController = fxmlLoader.getController();
            addServiceController.setBookingID(bookingID);
            addServiceController.setParentRoot(this);
            root.getChildren().setAll(newContent);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Error while adding service");
        }
    }

    private void handleSettings(Room room) {
    }
    private void handleAddRoom() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/room/add-room.fxml"));

            // Create the controller with a reference to return to
            AddRoomController addRoomController = new AddRoomController(this);
            fxmlLoader.setControllerFactory(param -> addRoomController);

            Parent parent = fxmlLoader.load();
            root.getChildren().setAll(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCheckout(Room room) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/example/hotelpro/manager/room/checkout-room.fxml"));
            Parent newContent = fxmlLoader.load();

            CheckoutController checkoutController = fxmlLoader.getController();
            checkoutController.setRoomId(room.getRoomID());
            checkoutController.setRoot(root);
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
            bookingController.setbooking_tai_cho(root);
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

//    private void handleDeleteRoom(Room room) {
//        String currentUserRole = getCurrentUserRole();
//        // In ra để kiểm tra
//        System.out.println("Vai trò người dùng hiện tại: " + currentUserRole);
//
//        if (currentUserRole == null || !"manager".equals(currentUserRole)) {
//            showAlert("Lỗi", "Bạn không có quyền xóa phòng.");
//            return;
//        }
//        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle("Xác nhận xóa phòng");
//        alert.setHeaderText(null);
//        alert.setContentText("Bạn có chắc chắn muốn xóa phòng số " + room.getRoomNumber() + " không?");
//
//        alert.showAndWait().ifPresent(response -> {
//            if (response == ButtonType.OK) {
//                roomDao.delete(room);
//                showAlert("Thành công", "Phòng đã được xóa.");
//                loadRoomData();
//            }
//        });
//    }
}