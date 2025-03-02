package controller.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connect.Connect;

public class BookingController {
    @FXML
    private TextField fullNameInput;
    @FXML
    private TextField phoneInput;
    @FXML
    private TextField emailInput;
    @FXML
    private TextField addressInput;
    @FXML
    private TextField idInput;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private ComboBox<String> genderBox;
    @FXML
    private DatePicker bookingDatePicker;
    @FXML
    private TextField roomPriceInput;
    @FXML
    private DatePicker expectedCheckinPicker;
    @FXML
    private DatePicker expectedCheckoutPicker;
    @FXML
    private DatePicker checkinPicker;
    @FXML
    private DatePicker checkoutPicker;
    @FXML
    private ComboBox<String> statusBox;
    @FXML
    private ComboBox<Integer> roomIdBox; // Danh sách phòng
    @FXML
    private Button submitButton;

    @FXML
    public void initialize() {
        // Khởi tạo dữ liệu cho ComboBox giới tính
        genderBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

        // Khởi tạo dữ liệu cho ComboBox trạng thái đặt phòng
        statusBox.setItems(FXCollections.observableArrayList("Pending", "Confirmed", "Cancelled"));

        // Gọi hàm tải danh sách phòng
        loadRooms();
    }

    /**
     * Hàm lấy toàn bộ danh sách phòng từ DB và hiển thị trong ComboBox
     */
    private void loadRooms() {
        ObservableList<Integer> roomList = FXCollections.observableArrayList();
        String sql = "SELECT RoomID FROM Room"; // Lấy tất cả phòng

        try (Connection conn = Connect.connection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                roomList.add(rs.getInt("RoomID"));
            }

            // Cập nhật ComboBox
            roomIdBox.setItems(roomList);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách phòng: " + e.getMessage());
        }
    }

    @FXML
    private void handleRoomClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/booking-room.fxml"));
            Parent bookingPage = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(bookingPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
