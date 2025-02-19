package controller;

import connect.Connect;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class RegisterController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label errorLabel;

    public void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        try (Connection connection = Connect.connection()) {
//            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
//            String password = passwordField.getText();
            String query = "INSERT INTO User (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
//            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(2, password);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                errorLabel.setText("Registration successful!");
                // Redirect to login page
            } else {
                errorLabel.setText("Registration failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("An error occurred.");
        }
    }

    public void openLoginPage() {
        // Load Login.fxml
    }
}