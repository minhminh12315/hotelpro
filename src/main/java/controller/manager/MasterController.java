package controller.manager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MasterController {

    @FXML
    public BorderPane root;

    @FXML
    private VBox contentArea;

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