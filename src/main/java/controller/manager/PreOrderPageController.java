package controller.manager;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.regex.Pattern;

import connect.Connect;

public class PreOrderPageController {
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
    private DatePicker expectedCheckinPicker;
    @FXML
    private DatePicker expectedCheckoutPicker;

    @FXML
    private ComboBox<String> genderBox;
    @FXML
    private Button submitButton;
    @FXML
    private VBox root;
    private int roomId;
    @FXML
    private Label roomNumberLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private String ExpectedCheckInDate;
    @FXML
    private String ExpectedCheckOutDate;


    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String NUMBER_REGEX = "\\d+";

    public void setRoomId(int roomId) {
        this.roomId = roomId;
        loadRooms();
    }


    @FXML
    public void initialize() {
        genderBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        genderBox.setValue("Male");
        validateEmail();
        validatePhoneNumber();
        validateIDPassport();
    }

    private int selectedRoomId = -1; // Biến toàn cục lưu RoomID

    private void loadRooms() {
        String sql = "SELECT * FROM Room WHERE RoomID = ?";

        try (Connection conn = Connect.connection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, roomId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    selectedRoomId = rs.getInt("RoomID"); // Lưu RoomID vào biến toàn cục
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
    private void handlePreOrderSubmit(ActionEvent event) {
        // Kiểm tra dữ liệu nhập trước khi thực hiện đặt phòng
        if (!isValidInput()) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập đúng thông tin trước khi đặt phòng!");
            return;
        }
        try (Connection connection = Connect.connection()) {
            String fullName = fullNameInput.getText();
            String phone = phoneInput.getText();
            String email = emailInput.getText();
            String address = addressInput.getText();
            String idPassport = idPassportInput.getText();
            String dob = dobPicker.getValue().toString();
            String expectedCheckInDate = expectedCheckinPicker.getValue().toString();
            String expectedCheckOutDate = expectedCheckoutPicker.getValue().toString();
            String gender = genderBox.getValue();

            // Kiểm tra RoomID đã được chọn chưa
            if (selectedRoomId == -1) {
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng chọn phòng trước khi đặt phòng!");
                return;
            }

            // Kiểm tra ngày exspecteCheckIn và exspectedCheckOut có bị trùng không, nếu trùng thì báo có người đặt rồi
            // Kiểm tra xem phòng có bị đặt trước chưa
            // Kiểm tra xem phòng đã được đặt trong khoảng thời gian này chưa
            String checkRoomQuery = """
                        SELECT COUNT(*) FROM Booking 
                        WHERE RoomID = ? 
                        AND Status IN ('CheckIn', 'PreOrder') 
                        AND (
                            (ExpectedCheckInDate BETWEEN ? AND ?) 
                            OR (ExpectedCheckOutDate BETWEEN ? AND ?) 
                            OR (? BETWEEN ExpectedCheckInDate AND ExpectedCheckOutDate)
                            OR (? BETWEEN ExpectedCheckInDate AND ExpectedCheckOutDate)
                        )
                    """;
            try (PreparedStatement checkStmt = connection.prepareStatement(checkRoomQuery)) {
                checkStmt.setInt(1, selectedRoomId);
                checkStmt.setDate(2, java.sql.Date.valueOf(expectedCheckInDate));
                checkStmt.setDate(3, java.sql.Date.valueOf(expectedCheckOutDate));
                checkStmt.setDate(4, java.sql.Date.valueOf(expectedCheckInDate));
                checkStmt.setDate(5, java.sql.Date.valueOf(expectedCheckOutDate));
                checkStmt.setDate(6, java.sql.Date.valueOf(expectedCheckInDate));
                checkStmt.setDate(7, java.sql.Date.valueOf(expectedCheckOutDate));

                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Phòng này đã có người đặt trong khoảng thời gian bạn chọn!");
                    return;
                }
            }


            double price = Double.parseDouble(priceLabel.getText());
            String sql_customer = "INSERT INTO Customer (FullName, PhoneNumber, Email, Address, ID_Passport, DateOfBirth, Gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String sql_preorder = "INSERT INTO Booking (CustomerID, RoomID, BookingDate, RoomPrice, ExpectedCheckInDate, ExpectedCheckOutDate, Status) VALUES (?, ?, ?, ?, ?, ?, 'PreOrder')";

            try (PreparedStatement stmt1 = connection.prepareStatement(sql_customer, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement stmt2 = connection.prepareStatement(sql_preorder)) {

                stmt1.setString(1, fullName);
                stmt1.setString(2, phone);
                stmt1.setString(3, email);
                stmt1.setString(4, address);
                stmt1.setString(5, idPassport);
                stmt1.setDate(6, java.sql.Date.valueOf(dob));
                stmt1.setString(7, gender);
                stmt1.executeUpdate();

                ResultSet rs1 = stmt1.getGeneratedKeys();
                int customerId = 0;
                if (rs1.next()) {
                    customerId = rs1.getInt(1);
                }

                stmt2.setInt(1, customerId);
                stmt2.setInt(2, selectedRoomId);
                LocalDate bookingDate = LocalDate.now();
                stmt2.setDate(3, java.sql.Date.valueOf(bookingDate));
                stmt2.setDouble(4, price);
                stmt2.setDate(5, java.sql.Date.valueOf(expectedCheckInDate));
                stmt2.setDate(6, java.sql.Date.valueOf(expectedCheckOutDate));

                stmt2.executeUpdate();
            }

            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đặt trước phòng thành công!");
            loadContent("/com/example/hotelpro/manager/pre-order-room.fxml");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Cảnh báo", "Dữ liệu phòng không hợp lệ: " + e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể đặt trước phòng: " + e.getMessage());
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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

    private boolean isValidInput() {
        String email = emailInput.getText();
        String phone = phoneInput.getText();
        String idPassport = idPassportInput.getText();

        // Kiểm tra Email
        if (!Pattern.matches(EMAIL_REGEX, email)) {
            showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Email không hợp lệ!");
            return false;
        }

        // Kiểm tra số điện thoại
        if (!Pattern.matches(NUMBER_REGEX, phone)) {
            showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Số điện thoại không hợp lệ!");
            return false;
        }

        // Kiểm tra số CMND/Hộ chiếu
        if (!Pattern.matches(NUMBER_REGEX, idPassport)) {
            showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Số CMND/Hộ chiếu không hợp lệ!");
            return false;
        }

        // Kiểm tra ngày tháng hợp lệ
        if (dobPicker.getValue() == null || expectedCheckinPicker.getValue() == null || expectedCheckoutPicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Vui lòng chọn đầy đủ ngày sinh, ngày nhận và ngày trả!");
            return false;
        }

        LocalDate checkIn = expectedCheckinPicker.getValue();
        LocalDate checkOut = expectedCheckoutPicker.getValue();

        if (checkIn.isBefore(LocalDate.now())) {
            showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Ngày nhận phòng phải từ hôm nay trở đi!");
            return false;
        }

        if (checkOut.isBefore(checkIn)) {
            showAlert(Alert.AlertType.ERROR, "Lỗi nhập liệu", "Ngày trả phòng phải sau ngày nhận phòng!");
            return false;
        }

        return true;
    }

}