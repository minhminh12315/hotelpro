package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class DashboardController {
    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private ComboBox<String> comboBox2;

    @FXML
    private ComboBox<String> comboBox3;

    @FXML
    private ComboBox<String> comboBox4;

    @FXML
    private ComboBox<String> comboBoxUser;

    @FXML
    public void initialize() {
        comboBox1.getItems().addAll("1", "2", "3");

        comboBox2.getItems().addAll("1", "2", "3");

        comboBox3.getItems().addAll("1", "2", "3");

        comboBox4.getItems().addAll("1", "2", "3");

        comboBoxUser.getItems().addAll("setting", "login");

        comboBox1.setValue("1");
        comboBox2.setValue("2");
        comboBox3.setValue("3");
        comboBox3.setValue("1");
    }

}
