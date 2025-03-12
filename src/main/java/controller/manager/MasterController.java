package controller.manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MasterController {

    @FXML
    public BorderPane root;
    public MenuButton logoutButton;

    @FXML
    private VBox contentArea;

    @FXML
    public static String userRole;

    @FXML
    private Button btnEmployeeManagement;

    public void initialize() {
        if (userRole == null) {
            userRole = "manager";
            System.out.println("Warning: userRole is null, defaulting to manager.");
        }
//        if (userRole == null) {
//            // chuyen trang login
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/login/login.fxml"));
//                Parent newContent = fxmlLoader.load();
//                root.getChildren().setAll(newContent);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        updateViewBasedOnRole(); // Cập nhật giao diện dựa trên vai trò
    }

    public static void setUserRole(String userRole) {
        MasterController.userRole = userRole;
        System.out.println("User role set to: " + userRole);
    }

    private void updateViewBasedOnRole() {

        if ("Staff".equalsIgnoreCase(userRole)) {
            btnEmployeeManagement.setVisible(false);
            btnEmployeeManagement.setManaged(false); // Loại bỏ khỏi layout
        } else if ("Manager".equalsIgnoreCase(userRole)) {
            btnEmployeeManagement.setVisible(true);
            btnEmployeeManagement.setManaged(true);
        }
        System.out.println("User role: " + userRole);
    }

    @FXML
    private void handleDashboard() {
        loadContent("/com/example/hotelpro/manager/dashboard.fxml");
    }

   @FXML
   private void handleRoomManagement() {
       loadContent("/com/example/hotelpro/manager/room-management.fxml");
   }

    @FXML
    private void handleEmployeeManagement() {
        loadContent("/com/example/hotelpro/manager/employee/employee-management.fxml");
    }

    @FXML
    private void handleServicesManagement() {
        loadContent("/com/example/hotelpro/service/services-management.fxml");
    }

    @FXML
    private void handleCustomerManagement() {
        loadContent("/com/example/hotelpro/manager/customer/customer-management.fxml");
    }


    @FXML
    private void handleSettings() {
        loadContent("/com/example/hotelpro/settings/settings.fxml");
    }

    @FXML
    public void handleLogout(ActionEvent event) {
        try {
            // Close the current stage (window)
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();

            // Load the new scene (login.fxml in this case)
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/login/login.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            currentStage.setScene(scene);

            // Set the title of the new stage (optional)
            currentStage.setTitle("Login");

            // Show the new stage (login window)
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
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

    public void handleProductManagement() {
        loadContent("/com/example/hotelpro/manager/product/product-management.fxml");
    }
}