package com.example.cm1601_2410212_20233136;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProjectController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}