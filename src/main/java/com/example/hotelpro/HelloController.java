package com.example.hotelpro;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;

public class HelloController {

    @FXML
    private CheckBox chkFood;
    @FXML
    private CheckBox chkDrink;
    @FXML
    private CheckBox chkSnack;
    @FXML
    private CheckBox chkRepair;
    @FXML
    private CheckBox chkBed;
    @FXML
    private CheckBox chkRoomDecor;
    @FXML
    private CheckBox chkRoomSauna;
    @FXML
    private CheckBox chkSpa;
    @FXML
    private CheckBox chkAirport;
    @FXML
    private CheckBox chkCheckinout;
    @FXML
    private CheckBox chkKaraoke;
    @FXML
    private CheckBox chkFishing;
    @FXML
    private CheckBox chkBoating;
    @FXML
    private CheckBox chkBiking;
    @FXML
    private ListView<String> cartlistView;

    private final ObservableList<String> selectedServices = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        cartlistView.setItems(selectedServices);

        setupCheckboxListener(chkFood, "Food");
        setupCheckboxListener(chkDrink, "Drink");
        setupCheckboxListener(chkSnack, "Snack");
        setupCheckboxListener(chkRepair, "Furniture Repair");
        setupCheckboxListener(chkBed, "Bed Sheets");
        setupCheckboxListener(chkRoomDecor, "Room Decoration");
        setupCheckboxListener(chkRoomSauna, "Sauna");
        setupCheckboxListener(chkSpa, "Spa");
        setupCheckboxListener(chkAirport, "Airport Pickup");
        setupCheckboxListener(chkCheckinout, "Late Check-in");
        setupCheckboxListener(chkKaraoke, "Karaoke");
        setupCheckboxListener(chkFishing, "Fishing");
        setupCheckboxListener(chkBoating, "Boating");
        setupCheckboxListener(chkBiking, "Biking");
    }

    private void setupCheckboxListener(CheckBox checkBox, String serviceName) {
        checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                selectedServices.add(serviceName);
            } else {
                selectedServices.remove(serviceName);
            }
        });
    }
}
