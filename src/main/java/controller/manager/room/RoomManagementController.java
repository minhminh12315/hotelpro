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

public class RoomManagementController {

    @FXML
    private VBox root;

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

    private static final int ITEMS_PER_PAGE = 10;

    BookingDao bookingDao = new BookingDao();

    @FXML
    private void handleAddRoom() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/room/add-room.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add New Room");

            root.getChildren().setAll(parent);

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

        // Remove all room-related components but keep the button container
        roomChildren.removeIf(node -> !(node == buttonContainer));


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

                MenuItem settingsItem = new MenuItem("Settings");
                settingsItem.setOnAction(_ -> handleSettings(room));


                contextMenu.getItems().addAll(settingsItem);
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
}