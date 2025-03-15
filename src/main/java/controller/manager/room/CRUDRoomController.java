package controller.manager.room;

import dao.RoomDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Room;

import java.util.List;

public class CRUDRoomController {

    @FXML
    private VBox root;

    @FXML
    private VBox roomContainer;

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, Integer> roomNumberColumn;

    @FXML
    private TableColumn<Room, Double> priceColumn;

    @FXML
    private TableColumn<Room, Integer> capacityColumn;

    @FXML
    private TableColumn<Room, String> typeColumn;

    @FXML
    private TableColumn<Room, String> statusColumn;

    @FXML
    private TableColumn<Room, String> actionColumn;

    public void initialize() {
        loadRoomData();

    }

    public void loadRoomData() {
        RoomDao roomDao = new RoomDao();
        List<Room> rooms = roomDao.getAll();

        // Clear old data in roomTable
        roomTable.getItems().clear();

        // Create an observable list from the room data
        ObservableList<Room> roomList = FXCollections.observableArrayList(rooms);

        // Set cell value factories for the table columns
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Add the room data to the table
        roomTable.setItems(roomList);
    }

    private void handleEditRoom(Room room) {
        System.out.println("Edit room: " + room.getRoomNumber());
        // Logic chỉnh sửa phòng (mở cửa sổ hoặc giao diện chỉnh sửa)
    }

    private void handleDeleteRoom(Room room) {
        // alert confirm xóa phòng
        System.out.println("Delete room: " + room.getRoomNumber());

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa phòng");
        alert.setHeaderText("Bạn có chắc chắn muốn xóa phòng " + room.getRoomNumber() + " không?");
        alert.setContentText("Hành động này không thể hoàn tác!");



        // Logic xóa phòng (xóa khỏi database và cập nhật lại danh sách)
        RoomDao roomDao = new RoomDao();
        roomDao.delete(room);
        loadRoomData(); // Reload room data
    }
}