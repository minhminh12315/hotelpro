package controller;

import connect.Connect;
import controller.manager.MasterController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private VBox root;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please fill in all fields.");
            return;
        }

        try (Connection connection = Connect.connection()) {
            String query = "SELECT password, role FROM Employee WHERE phonenumber = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                String role = resultSet.getString("role");

                if (password.equals(storedPassword)) {
                    errorLabel.setText("Login successful!");

                    if ("manager".equals(role)) {
                        MasterController.setUserRole("manager");
                    } else {
                        MasterController.setUserRole("staff");
                    }

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/master.fxml"));
                    Parent newContent = fxmlLoader.load();
                    root.getChildren().setAll(newContent);


                } else {
                    errorLabel.setText("Invalid password.");
                }
            } else {
                errorLabel.setText("User not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("An error occurred.");
        }
    }

    public void openRegisterPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/register/register.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("An error occurred while opening the register page.");
        }

    }
}