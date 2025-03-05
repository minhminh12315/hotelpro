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
import java.sql.SQLException;
import java.time.LocalDate;

public class EmployeeEditController {

    @FXML
    Button editButton;
    @FXML
    VBox contentArea;
    @FXML
    TextField editId;

    @FXML
    TextField editName;
    @FXML
    TextField editPhoneNumber;
    @FXML
    TextField editEmail;

    @FXML
    ComboBox<String> editRole;
    @FXML
    PasswordField editPassword;
    @FXML
    PasswordField editConfirmPassword;
    @FXML
    DatePicker editDate;

    Employee employee;

    public EmployeeEditController() {}

    public EmployeeEditController(int id) throws SQLException {
        employee = new Employee().getById(id);
        System.out.println(id);

        if (employee == null) {
            System.out.println("Employee is null after getById call");
        } else {
            System.out.println("Employee loaded: " + employee.getFullName());
        }
    }

    public void updateEmployee() throws IOException {
        String name = editName.getText();
        String phone = editPhoneNumber.getText();
        String email = editEmail.getText();
        String role = editRole.getValue();
        String password = editPassword.getText();
        String confirmPassword = editConfirmPassword.getText();
        String date = editDate.getValue().toString();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || role == null || password.isEmpty() ||
                confirmPassword.isEmpty() || date.isEmpty()) {
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

        if (!password.equals(confirmPassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Passwords do not match");
            alert.showAndWait();
            return;
        }

        try {
            LocalDate parsedDate = LocalDate.parse(date);
            employee.setStartDate(parsedDate);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Date must be in the format: yyyy-MM-dd");
            alert.showAndWait();
            return;
        }

        employee.setFullName(name);
        employee.setPhoneNumber(phone);
        employee.setEmail(email);
        employee.setRole(role);
        employee.setPassword(password);

        try {
            employee.update();

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Update successful");
            alert.showAndWait();

            loadContent("/com/example/hotelpro/manager/employee/employee-management.fxml");
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Employee update failed: " + e.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    public void initialize() {
        editRole.getItems().addAll("manager", "user");

        if (employee != null) {
            editName.setText(employee.getFullName());
            editPhoneNumber.setText(employee.getPhoneNumber());
            editEmail.setText(employee.getEmail());
            editRole.setValue(employee.getRole());
            editPassword.setText(employee.getPassword());
            editConfirmPassword.setText(employee.getPassword());
            editDate.setValue(employee.getStartDate());
            editId.setText(String.valueOf(employee.getEmployeeID()));
        } else {
            System.out.println("Employee is null");
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

//    private String hashPasswordWithSHA1(String password) {
//        try {
//            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
//            byte[] hashedBytes = messageDigest.digest(password.getBytes());
//            StringBuilder sb = new StringBuilder();
//            for (byte b : hashedBytes) {
//                sb.append(String.format("%02x", b));
//            }
//            return sb.toString();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
