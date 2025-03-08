package com.example.hotelpro;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HelloController {

    @FXML
    private CheckBox chkFood, chkDrink, chkSnack, chkRepair, chkBed, chkRoomDecor;
    @FXML
    private CheckBox chkRoomSauna, chkSpa, chkAirport, chkCheckinout, chkKaraoke;
    @FXML
    private CheckBox chkFishing, chkBoating, chkBiking;
    @FXML
    private ListView<String> cartlistView;
    private final ObservableList<String> selectedServices = FXCollections.observableArrayList();
    private final Map<String, Stage> openStages = new HashMap<>();

    @FXML
    public void initialize() {
        cartlistView.setItems(selectedServices);
        setupMultiInstanceCheckbox(chkFood, "Food");
        setupMultiInstanceCheckbox(chkDrink, "Drink");
        setupMultiInstanceCheckbox(chkSnack, "Snack");

        setupToggleCheckbox(chkSpa, "Spa");
        setupToggleCheckbox(chkRepair, "Furniture Repair");
        setupToggleCheckbox(chkBed, "Bed Sheets");
        setupToggleCheckbox(chkRoomDecor, "Room Decoration");
        setupToggleCheckbox(chkRoomSauna, "Sauna");
        setupToggleCheckbox(chkAirport, "Airport Pickup");
        setupToggleCheckbox(chkCheckinout, "Late Check-in");
        setupToggleCheckbox(chkKaraoke, "Karaoke");
        setupToggleCheckbox(chkFishing, "Fishing");
        setupToggleCheckbox(chkBoating, "Boating");
        setupToggleCheckbox(chkBiking, "Biking");
    }

    private void setupMultiInstanceCheckbox(CheckBox checkBox, String serviceName) {
        checkBox.setOnMouseClicked(event -> {
            selectedServices.add(serviceName);
            openPageMulti(serviceName);
            checkBox.setSelected(false);
        });
    }

    private void setupToggleCheckbox(CheckBox checkBox, String serviceName) {
        checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                selectedServices.add(serviceName);
            } else {
                selectedServices.remove(serviceName);
            }
        });
    }

    private void openPageMulti(String serviceName) {
        String fxmlFile = getFXMLForService(serviceName);
        if (fxmlFile == null) return;
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root, 850, 600);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(serviceName + " Services");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFXMLForService(String serviceName) {
        switch (serviceName) {
            case "Food":
                return "/com/example/hotelpro/service/food-selection.fxml";
            case "Drink":
                return "/com/example/hotelpro/service/water-selection.fxml";
            case "Snack":
                return "/com/example/hotelpro/snack.fxml";
            default:
                return null;
        }
    }
}
