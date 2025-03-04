package controller.manager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MasterController {

    @FXML
    public BorderPane root;

    @FXML
    private VBox contentArea;

    @FXML
    public static String userRole;

    @FXML
    private Button btnEmployeeManagement;

    public void initialize() {
        updateViewBasedOnRole(); // Cập nhật giao diện dựa trên vai trò
    }

    public static void setUserRole(String userRole) {
        MasterController.userRole = userRole;
    }

    private void updateViewBasedOnRole() {
        if ("staff".equalsIgnoreCase(userRole)) {
            btnEmployeeManagement.setVisible(false);
            btnEmployeeManagement.setManaged(false); // Loại bỏ khỏi layout
        } else {
            btnEmployeeManagement.setVisible(true);
            btnEmployeeManagement.setManaged(true);
        }
    }

    @FXML
    private void handleDashboard() {
        loadContent("/com/example/hotelpro/manager/dashboard.fxml");
    }

//    @FXML
//    private void handleRoomManagement() {
//        loadContent("/com/example/hotelpro/manager/room-management.fxml");
//    }

    @FXML
    private void handleEmployeeManagement() {
        loadContent("/com/example/hotelpro/manager/employee/employee-management.fxml");
    }

    @FXML
    private void handleServicesManagement() {
        loadContent("/com/example/hotelpro/manager/services-management.fxml");
    }

    @FXML
    private void handleSettings() {
        loadContent("/com/example/hotelpro/settings/settings.fxml");
    }

    @FXML
    private void handleLogout() {
        loadContent("/com/example/hotelpro/login/login.fxml");
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
}