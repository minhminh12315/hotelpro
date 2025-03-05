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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import connect.Connect;
import java.util.regex.Pattern;


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
    private TextField idPassportInput;
    @FXML
    private DatePicker dobPicker;
    @FXML
    private ComboBox<String> genderBox;
    @FXML
    private Button submitButton;
    @FXML
    private int roomId;
    @FXML
    private Label roomNumberLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private VBox booking_tai_cho;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String NUMBER_REGEX = "\\d+";

    public void setRoomId(int roomId) {
        this.roomId = roomId;
        loadRooms();
    }

    @FXML
    public void initialize() {
        genderBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
//        statusBox.setItems(FXCollections.observableArrayList("Pending", "Confirmed", "Cancelled"));
        validateEmail();
        validatePhoneNumber();
        validateIDPassport();
    }

    private void loadRooms() {
        String sql = "SELECT * FROM Room WHERE RoomID = ?"; // Thay RoomID bằng cột thực tế trong cơ sở dữ liệu

        try (Connection conn = Connect.connection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roomId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { // Lấy phòng đầu tiên
                    int roomNumber = rs.getInt("RoomNumber");
                    double price = rs.getDouble("Price");

                    roomNumberLabel.setText(String.valueOf(roomNumber));
                    priceLabel.setText(String.format("%.2f", price));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể tải danh sách phòng: " + e.getMessage());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "ID phòng không hợp lệ: " + e.getMessage());
        }
    }


    @FXML
    private void handleBookingSubmit(ActionEvent event) {

        try {
            String fullName = fullNameInput.getText(); // customer
            String phone = phoneInput.getText(); // customer
            String email = emailInput.getText(); // customer
            String address = addressInput.getText(); // customer
            String idPassport = idPassportInput.getText(); // customer
            String dob = dobPicker.getValue().toString(); // customer
            String gender = genderBox.getValue(); // customer

            String sql_customer = "INSERT INTO Customer (FullName, PhoneNumber, Email, Address, ID_Passport, DateOfBirth, Gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String sql_booking = "INSERT INTO Booking (CustomerID, RoomID, RoomPrice, CheckinDate) VALUES (?, ?, ?, ?)";
            String sql_room = "UPDATE Room SET Status = 'Occupied' WHERE RoomID = ?";

            try (Connection connection = Connect.connection();
                 PreparedStatement stmt = connection.prepareStatement(sql_customer, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement stmt2 = connection.prepareStatement(sql_booking);
                 PreparedStatement stmt3 = connection.prepareStatement(sql_room)) {

                stmt.setString(1, fullName);
                stmt.setString(2, phone);
                stmt.setString(3, email);
                stmt.setString(4, address);
                stmt.setString(5, idPassport);
                stmt.setDate(6, java.sql.Date.valueOf(dob));
                stmt.setString(7, gender);

                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                int customerId = 0;
                if (rs.next()) {
                    customerId = rs.getInt(1);
                }

                stmt2.setInt(1, customerId);
                stmt2.setInt(2, roomId);
                String priceText = priceLabel.getText();
                double price;
                try {
                    price = Double.parseDouble(priceText);
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Price format is invalid: " + priceText);
                    return;
                }
                stmt2.setDouble(3, price);
                stmt2.setDate(4, java.sql.Date.valueOf(LocalDate.now()));

                stmt2.executeUpdate();

                stmt3.setInt(1, roomId);
                stmt3.executeUpdate();

                showAlert(Alert.AlertType.INFORMATION, "Success", "Booking successfully!");

                loadContent();

            } catch (SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Cannot insert customer data: " + e.getMessage());
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Cannot insert booking data: " + e.getMessage());
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Cannot insert bookingg data: " + e.getMessage());
        }


    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleCancle(ActionEvent event) {
        loadContent();
    }

    private void loadContent() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/room-management.fxml"));
            Parent newContent = fxmlLoader.load();
            booking_tai_cho.getChildren().setAll(newContent);
            // sau khi thêm phòng xong thì load lại dữ liệu
            RoomManagementController controller = fxmlLoader.getController();
            controller.loadRoomData();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void validateEmail() {
        emailInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.matches(EMAIL_REGEX, newValue) && !newValue.isEmpty()) {
                emailInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            } else {
                emailInput.setStyle(null);
            }
        });
    }

    private void validatePhoneNumber() {
        phoneInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.matches(NUMBER_REGEX, newValue) && !newValue.isEmpty()) {
                phoneInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            } else {
                phoneInput.setStyle(null);
            }
        });
    }

    private void validateIDPassport() {
        idPassportInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Pattern.matches(NUMBER_REGEX, newValue) && !newValue.isEmpty()) {
                idPassportInput.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            } else {
                idPassportInput.setStyle(null);
            }
        });
    }

}
