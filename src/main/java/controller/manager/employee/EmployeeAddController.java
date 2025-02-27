package controller.manager.employee;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Employee;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.ZoneId;

public class EmployeeAddController {
    @FXML
    Button addButton;
    @FXML
    private VBox contentArea;

    @FXML
    TextField addName;
    @FXML
    TextField addPhoneNumber;
    @FXML
    TextField addEmail;

    @FXML
    TextField addRole;
    @FXML
    PasswordField addPassword;
    @FXML
    DatePicker addDate;

    public void addEmployee() throws IOException {
        String name = addName.getText();
        String phone = addPhoneNumber.getText();
        String email = addEmail.getText();
        String role = addRole.getText();
        String password = addPassword.getText();
        LocalDate date = addDate.getValue();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || role.isEmpty() || password.isEmpty() || date == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields are required");
            alert.showAndWait();
            return;
        }

        if (!phone.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Phone number must contain only digits");
            alert.showAndWait();
            return;
        }

        if (!email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Email must be in the format: example@gmail.com");
            alert.showAndWait();
            return;
        }

        if (!(role.equalsIgnoreCase("manager") || role.equalsIgnoreCase("user"))) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Role must be either 'manager' or 'user'");
            alert.showAndWait();
            return;
        }

//        String hashedPassword = hashPasswordWithSHA1(password);

        try {
            LocalDate.parse(date.toString());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Date must be in the format: yyyy-MM-dd");
            alert.showAndWait();
            return;
        }

        Employee employee = new Employee(name, phone, email, role, password, date);
        int res = employee.addEmployee();

        if (res > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Thêm mới thành công");
            alert.showAndWait();
            loadContent("/com/example/hotelpro/manager/employee/employee-management.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Thêm mới thất bại");
            alert.showAndWait();
        }
    }


    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newContent = fxmlLoader.load();
            contentArea.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String hashPasswordWithSHA1(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = messageDigest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
