package controller.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;


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
    private Button submitButton;
    @FXML
    private Label roomIdLabel;
    @FXML
    private Label priceLabel;

    @FXML
    public void initialize() {
        // Khởi tạo dữ liệu cho ComboBox giới tính
        genderBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

        // Khởi tạo dữ liệu cho ComboBox trạng thái đặt phòng
        statusBox.setItems(FXCollections.observableArrayList("Pending", "Confirmed", "Cancelled"));

        // Tải danh sách phòng
        loadRooms();
    }

    /**
     * Hàm lấy toàn bộ danh sách phòng từ DB và hiển thị phòng đầu tiên
     */
    private void loadRooms() {
        String sql = "SELECT RoomID, Price FROM Room"; // Lấy tất cả phòng

        try (Connection conn = Connect.connection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) { // Lấy phòng đầu tiên
                int roomId = rs.getInt("RoomID");
                double price = rs.getDouble("Price");

                roomIdLabel.setText(String.valueOf(roomId));
                priceLabel.setText(String.valueOf(price));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách phòng: " + e.getMessage());
        }
    }

    @FXML
    private void handleBookingSubmit(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/booking-room.fxml"));
            Parent bookingPage = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(bookingPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể mở trang đặt phòng: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setRoomInfo(String roomId, String price) {
        roomIdLabel.setText(roomId);
        priceLabel.setText(price);
    }

    @FXML
    private void handleBackToRoom(ActionEvent event) {
        try {
            // Load file FXML của trang quản lý phòng
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/room-management.fxml"));
            Parent roomPage = loader.load();

            // Lấy Stage hiện tại từ sự kiện
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(roomPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể quay lại trang phòng:\n" + e.getMessage());
        }
    }



}
