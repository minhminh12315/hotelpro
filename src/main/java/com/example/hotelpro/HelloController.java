package com.example.hotelpro;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {

    @FXML
    private Label someLabel;
    @FXML
    public void initialize() {
        System.out.println("HotelController đã được khởi tạo!");
    }
}