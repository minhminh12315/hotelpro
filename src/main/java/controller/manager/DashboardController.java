package controller.manager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private void handleHome() {
        loadPage("/com/example/hotelpro/home/home.fxml");
    }

    @FXML
    private void handleUserProfile() {
        loadPage("/com/example/hotelpro/profile/profile.fxml");
    }

    @FXML
    private void handleSettings() {
        loadPage("/com/example/hotelpro/settings/settings.fxml");
    }

    @FXML
    private void handleLogout() {
        loadPage("/com/example/hotelpro/login/login.fxml");
    }

    private void loadPage(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Parent) fxmlLoader.getNamespace().get("root")).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}