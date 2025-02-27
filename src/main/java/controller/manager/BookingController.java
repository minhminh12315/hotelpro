package controller.manager;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

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
    public void initialize() {
        // Khởi tạo dữ liệu cho ComboBox giới tính
        genderBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

        // Khởi tạo dữ liệu cho ComboBox trạng thái đặt phòng
        statusBox.setItems(FXCollections.observableArrayList("Pending", "Confirmed", "Cancelled"));

        // Gán sự kiện khi nhấn nút Submit
//        submitButton.setOnAction(event -> handleSubmit());
    }

    @FXML
    private void handleRoomClick(MouseEvent event) {
        try {
            // Load trang booking
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/booking.fxml"));
            Parent bookingPage = loader.load();

            // Lấy Stage hiện tại
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(bookingPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void handleSubmit() {
//        // Lấy dữ liệu từ các trường nhập
//        String fullName = fullNameInput.getText();
//        String phone = phoneInput.getText();
//        String email = emailInput.getText();
//        String address = addressInput.getText();
//        String id = idInput.getText();
//        String dob = (dobPicker.getValue() != null) ? dobPicker.getValue().toString() : "N/A";
//        String gender = genderBox.getValue();
//        String bookingDate = (bookingDatePicker.getValue() != null) ? bookingDatePicker.getValue().toString() : "N/A";
//        String roomPrice = roomPriceInput.getText();
//        String expectedCheckin = (expectedCheckinPicker.getValue() != null) ? expectedCheckinPicker.getValue().toString() : "N/A";
//        String expectedCheckout = (expectedCheckoutPicker.getValue() != null) ? expectedCheckoutPicker.getValue().toString() : "N/A";
//        String checkinDate = (checkinPicker.getValue() != null) ? checkinPicker.getValue().toString() : "N/A";
//        String checkoutDate = (checkoutPicker.getValue() != null) ? checkoutPicker.getValue().toString() : "N/A";
//        String status = statusBox.getValue();
//
//        // In thông tin ra console để kiểm tra
//        System.out.println("Booking Information:");
//        System.out.println("Full Name: " + fullName);
//        System.out.println("Phone: " + phone);
//        System.out.println("Email: " + email);
//        System.out.println("Address: " + address);
//        System.out.println("ID/Passport: " + id);
//        System.out.println("Date of Birth: " + dob);
//        System.out.println("Gender: " + gender);
//        System.out.println("Booking Date: " + bookingDate);
//        System.out.println("Room Price: " + roomPrice);
//        System.out.println("Expected Check-in: " + expectedCheckin);
//        System.out.println("Expected Check-out: " + expectedCheckout);
//        System.out.println("Check-in Date: " + checkinDate);
//        System.out.println("Check-out Date: " + checkoutDate);
//        System.out.println("Status: " + status);
//    }
}
