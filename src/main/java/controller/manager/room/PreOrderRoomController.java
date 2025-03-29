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
import javafx.scene.Node;

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
    private static final int MAX_ROOMS_PER_FLOOR = 5;
    RoomDao roomDao = new RoomDao();
    private List<Room> allRooms = roomDao.getAll();
    private List<Room> filteredRooms = allRooms;

    @FXML
    private void initialize() {
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
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());
    }

    public void loadRoomData() {
        displayRooms(0); // Display the rooms on page 0 initially
    }

    public void handleSearch() {
        String query = searchBar.getText().toLowerCase().trim();

        filteredRooms = allRooms.stream()
                .filter(room -> String.valueOf(room.getRoomNumber()).contains(query) ||
                        room.getRoomType().toLowerCase().contains(query) ||
                        room.getStatus().toLowerCase().contains(query))
                .collect(Collectors.toList());

        initialize();
    }

    public void displayRooms(int pageIndex) {
        roomContainer.getChildren().clear();

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
                VBox roomBox = createRoomBox(room);
                currentRowBox.getChildren().add(roomBox);
            }

            // Add floor box to room container
            roomContainer.getChildren().add(floorVBox);
        }
    }


    private VBox createRoomBox(Room room) {
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

        roomBox.getChildren().addAll(roomNumberLabel, priceLabel, capacityLabel, typeLabel, statusLabel);

        // Add click handler for pre-ordering
        roomBox.setOnMouseClicked(event -> handlePreOrder(room));

        return roomBox;
    }


    @FXML
    private void handlePreOrder(Room room) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/room/pre-order-page.fxml"));
            Parent newContent = fxmlLoader.load();

            PreOrderPageController preOrderController = fxmlLoader.getController();
            preOrderController.setRoomId(room.getRoomID());

            // Cập nhật giao diện
            root.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
