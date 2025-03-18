package controller.manager.room;

import dao.RoomDao;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Room;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PreOrderRoomController {

    @FXML
    private VBox root;

    @FXML
    private VBox roomContainer;

    @FXML
    private TextField searchBar; // Search bar field

    @FXML
    private Pagination pagination; // Pagination control

    private static final int ITEMS_PER_PAGE = 10; // Number of rooms per page
    private List<Room> allRooms;
    private List<Room> filteredRooms;

    @FXML
    private void initialize() {
        loadRoomData();

        pagination.setPageCount((int) Math.ceil((double) allRooms.size() / ITEMS_PER_PAGE));
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(5);

        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> displayRooms(newValue.intValue()));

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());
    }

    public void loadRoomData() {
        RoomDao roomDao = new RoomDao();
        allRooms = roomDao.getAll();
        filteredRooms = allRooms;

        displayRooms(0); // Display the rooms on page 0 initially
    }

    public void handleSearch() {
        String query = searchBar.getText().toLowerCase().trim();

        filteredRooms = allRooms.stream()
                .filter(room -> String.valueOf(room.getRoomNumber()).contains(query) ||
                        room.getRoomType().toLowerCase().contains(query) ||
                        room.getStatus().toLowerCase().contains(query))
                .collect(Collectors.toList());

        pagination.setPageCount((int) Math.ceil((double) filteredRooms.size() / ITEMS_PER_PAGE));
        pagination.setCurrentPageIndex(0); // Reset to page 0 after a search
        displayRooms(0); // Display rooms after filtering
    }

    public void displayRooms(int pageIndex) {
        // Clear the roomContainer first
        roomContainer.getChildren().clear();

        // Calculate starting index based on current page
        int startIndex = pageIndex * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, filteredRooms.size());

        // Organize rooms by floor
        Map<Integer, HBox> floorMap = new HashMap<>();

        for (int i = startIndex; i < endIndex; i++) {
            Room room = filteredRooms.get(i);
            int floor = room.getRoomNumber() / 100;

            if (!floorMap.containsKey(floor)) {
                HBox floorBox = new HBox();
                floorBox.setSpacing(10);
                floorBox.setStyle("-fx-padding: 10; -fx-border-color: gray; -fx-border-width: 1px; -fx-background-color: #f4f4f4;");
                Label floorLabel = new Label("Floor " + floor);
                floorLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 5;");
                floorBox.getChildren().add(floorLabel);
                floorMap.put(floor, floorBox);
            }

            // Create VBox to display each room
            VBox roomBox = new VBox();
            roomBox.setPrefHeight(150);
            roomBox.setPrefWidth(120);
            roomBox.setStyle("-fx-background-color: " + (room.getStatus().equals("Occupied") ? "red" : "green") + "; -fx-border-color: black; -fx-border-width: 2px; -fx-padding: 5;");

            Label roomNumberLabel = new Label("Room: " + room.getRoomNumber());
            Label priceLabel = new Label("Price: " + room.getPrice());
            Label capacityLabel = new Label("Capacity: " + room.getCapacity());
            Label typeLabel = new Label("Type: " + room.getRoomType());
            Label statusLabel = new Label("Status: " + room.getStatus());

            roomBox.getChildren().addAll(roomNumberLabel, priceLabel, capacityLabel, typeLabel, statusLabel);

            // Add click event for pre-order functionality
            roomBox.setOnMouseClicked(event -> handlePreOrder(room));
            floorMap.get(floor).getChildren().add(roomBox);
        }

        // Add all floors (with rooms) to the roomContainer
        for (Map.Entry<Integer, HBox> entry : floorMap.entrySet()) {
            roomContainer.getChildren().add(entry.getValue());
        }
    }

    @FXML
    private void handlePreOrder(Room room) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/room/pre-order-page.fxml"));
            Parent newContent = fxmlLoader.load();

            PreOrderPageController preOrderController = fxmlLoader.getController();
            preOrderController.setRoomId(room.getRoomID());

            // Update the root container with the pre-order page
            root.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
