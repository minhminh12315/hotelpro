package controller;

import connect.Connect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

    public Hyperlink openLoginButton;
    @FXML
    BorderPane root;
    @FXML
    private TextField fullnameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label errorLabel;


    public RegisterController(BorderPane root) throws SQLException {
        this.root = root;
    }

    public void handleRegister() {
        String fullname = fullnameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (fullname.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match.");
            return;
        }

        try (Connection connection = Connect.connection()) {
            String query = "INSERT INTO Employee (fullname, phonenumber, password, startdate) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, fullname);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                errorLabel.setText("Registration successful!");
                openLoginPage();
            } else {
                errorLabel.setText("Registration failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("An error occurred.");
        }
    }

    public void openLoginPage() {
        try {
            Stage currentStage = (Stage) openLoginButton.getScene().getWindow();
            currentStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/login/login.fxml"));
            Parent newContent = fxmlLoader.load();
//            root.getChildren().setAll(newContent);


            Scene scene = new Scene(newContent);
            currentStage.setScene(scene);
            currentStage.setTitle("Register");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("An error occurred while opening the login page.");
        }
    }
}