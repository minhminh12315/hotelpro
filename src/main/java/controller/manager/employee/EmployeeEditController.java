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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

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
    TextField editRole;
    @FXML
    PasswordField editPassword;
    @FXML
    DatePicker editDate;

    Employee employee;

    public EmployeeEditController(){

    }

    public EmployeeEditController(int id) {
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
        String role = editRole.getText();
        String password = editPassword.getText();
        String date = editDate.getValue().toString();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || role.isEmpty() || password.isEmpty() || date.isEmpty()) {
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
            LocalDate parsedDate = LocalDate.parse(date);
            employee.setStartDate(parsedDate);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Date must be in the format: yyyy-MM-dd");
            alert.showAndWait();
            return;
        }

        // All fields are valid, so update the employee
        employee.setFullName(name);
        employee.setPhoneNumber(phone);
        employee.setEmail(email);
        employee.setRole(role);
//        employee.setPassword(hashedPassword);
        employee.setPassword(password);
        employee.update();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Update successful");
        alert.showAndWait();

        loadContent("/com/example/hotelpro/manager/employee/employee-management.fxml");
    }

    @FXML
    public void initialize() {
        if (employee != null) {
            editName.setText(employee.getFullName());
            editPhoneNumber.setText(employee.getPhoneNumber());
            editEmail.setText(employee.getEmail());
            editRole.setText(employee.getRole());
            editPassword.setText(employee.getPassword());
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

