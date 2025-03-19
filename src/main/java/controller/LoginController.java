package controller;

import connect.Connect;
import controller.manager.MasterController;
import controller.manager.employee.EmployeeEditController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    public Button openRegisterButton;
    @FXML
    private BorderPane root;
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
            String query = "SELECT * FROM Employee WHERE fullname = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                String role = resultSet.getString("role");
                String employeeID = resultSet.getString("employeeID");

                if (password.equals(storedPassword)) {
                    errorLabel.setText("Login successful!");



                    MasterController masterController = new MasterController();

                    masterController.setEmployeeID(Integer.parseInt(employeeID));

                    if ("manager".equals(role)) {
                        masterController.setUserRole("manager");
                    } else {
                        masterController.setUserRole("staff");
                    }

                    FXMLLoader fxmlLoader = new FXMLLoader(
                            getClass().getResource("/com/example/hotelpro/manager/master.fxml"));
                    Parent newContent = fxmlLoader.load();
                    root.setTop(null);
                    root.setBottom(null);
                    root.setLeft(null);
                    root.setRight(null);
                    root.setCenter(newContent);

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
            Stage currentStage = (Stage) openRegisterButton.getScene().getWindow();
            currentStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/example/hotelpro/register/register.fxml"));

            RegisterController registerController = new RegisterController(root);
            fxmlLoader.setControllerFactory(param -> registerController);

            Parent newContent = fxmlLoader.load();

            Scene scene = new Scene(newContent);
            currentStage.setScene(scene);
            currentStage.setTitle("Login");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            errorLabel.setText("An error occurred while opening the register page.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}