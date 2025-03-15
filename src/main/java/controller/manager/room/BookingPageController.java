package controller.manager.room;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.sql.*;
import java.time.LocalDate;
import connect.Connect;

public class BookingPageController {

    @FXML private TextField fullNameInput;
    @FXML private TextField phoneInput;
    @FXML private TextField emailInput;
    @FXML private TextField addressInput;
    @FXML private TextField idInput;
    @FXML private DatePicker dobPicker;
    @FXML private ComboBox<String> genderBox;
    @FXML private DatePicker bookingDatePicker;
    @FXML private TextField roomPriceInput;
    @FXML private DatePicker expectedCheckinPicker;
    @FXML private DatePicker expectedCheckoutPicker;
    @FXML private DatePicker checkinPicker;
    @FXML private DatePicker checkoutPicker;
    @FXML private ComboBox<String> statusBox;
    @FXML private ComboBox<Integer> roomIdBox;
    @FXML private Button submitButton;

    @FXML
    private void handleBookingSubmit(ActionEvent event) {
        System.out.println("Button clicked!");

        // 1. Lấy dữ liệu từ giao diện
        String fullName = fullNameInput.getText();
        String phoneNumber = phoneInput.getText();
        String email = emailInput.getText();
        String address = addressInput.getText();
        String idPassport = idInput.getText();
        LocalDate dateOfBirth = dobPicker.getValue();
        String gender = genderBox.getValue();
        LocalDate BookingDate = bookingDatePicker.getValue();
        String RoomPrice = roomPriceInput.getText();
        LocalDate ExpectedCheckInDate = expectedCheckinPicker.getValue();
        LocalDate ExpectedCheckOutDate = expectedCheckoutPicker.getValue();
        LocalDate CheckInDate = checkinPicker.getValue();
        LocalDate CheckOutDate = checkoutPicker.getValue();
        String Status = statusBox.getValue();
        Integer RoomID = roomIdBox.getValue();

        if (RoomID == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng chọn phòng!");
            return;
        }

        try (Connection conn = Connect.connection()) {
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            // 2. Kiểm tra khách hàng đã tồn tại chưa
            int CustomerID = getCustomerId(conn, idPassport);
            if (CustomerID == -1) {
                // Nếu chưa tồn tại, thêm mới khách hàng
                CustomerID = insertCustomer(conn, fullName, phoneNumber, email, address, idPassport, dateOfBirth, gender);
                if (CustomerID == -1) {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm khách hàng!");
                    conn.rollback();
                    return;
                }
            }

            // 3. Chèn dữ liệu vào bảng Booking
            String sql = "INSERT INTO Booking (CustomerID, RoomID, BookingDate, RoomPrice, ExpectedCheckInDate, ExpectedCheckOutDate, CheckInDate, CheckOutDate, Status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, CustomerID);
                stmt.setInt(2, RoomID);
                stmt.setDate(3, BookingDate != null ? java.sql.Date.valueOf(BookingDate) : null);
                stmt.setBigDecimal(4, new java.math.BigDecimal(RoomPrice));
                stmt.setDate(5, ExpectedCheckInDate != null ? java.sql.Date.valueOf(ExpectedCheckInDate) : null);
                stmt.setDate(6, ExpectedCheckOutDate != null ? java.sql.Date.valueOf(ExpectedCheckOutDate) : null);
                stmt.setDate(7, CheckInDate != null ? java.sql.Date.valueOf(CheckInDate) : null);
                stmt.setDate(8, CheckOutDate != null ? java.sql.Date.valueOf(CheckOutDate) : null);
                stmt.setString(9, Status);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    conn.commit();
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đặt phòng thành công!");
                } else {
                    conn.rollback();
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể đặt phòng!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Lỗi kết nối", "Lỗi khi đặt phòng: " + e.getMessage());
        }
    }

    /**
     * Lấy CustomerID nếu khách hàng đã tồn tại, nếu không trả về -1.
     */
    private int getCustomerId(Connection conn, String idPassport) throws SQLException {
        String sql = "SELECT CustomerID FROM Customer WHERE ID_Passport = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idPassport);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("CustomerID");
            }
        }
        return -1; // Không tìm thấy
    }

    /**
     * Thêm khách hàng mới và trả về CustomerID, nếu thất bại trả về -1.
     */
    private int insertCustomer(Connection conn, String fullName, String phoneNumber, String email, String address, String idPassport, LocalDate dateOfBirth, String gender) throws SQLException {
        String sql = "INSERT INTO Customer (FullName, PhoneNumber, Email, Address, ID_Passport, DateOfBirth, Gender) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING CustomerID";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            stmt.setString(2, phoneNumber);
            stmt.setString(3, email);
            stmt.setString(4, address);
            stmt.setString(5, idPassport);
            stmt.setDate(6, dateOfBirth != null ? java.sql.Date.valueOf(dateOfBirth) : null);
            stmt.setString(7, gender);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("CustomerID");
            }
        }
        return -1; // Thêm thất bại
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
