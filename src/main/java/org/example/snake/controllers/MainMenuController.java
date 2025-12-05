package org.example.snake.controllers;

import org.example.snake.utils.SceneUtils;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class MainMenuController{

    @FXML
    private VBox racine;

    @FXML
    private void allerInscription() {
        SceneUtils.changerScene("Register.fxml", racine);
    }

    @FXML
    private void goToLogin() {
        SceneUtils.changerScene("Login.fxml", racine);
    }

}
