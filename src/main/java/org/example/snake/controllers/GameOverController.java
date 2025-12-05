package org.example.snake.controllers;

import org.example.snake.utils.SceneUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class GameOverController{

    @FXML
    private Label dernierScoreLabel;

    @FXML
    private Label meilleurScoreLabel;

    @FXML
    private Label niveauLabel;

    private int idUser;
    private String prenom;

    // appelée depuis SceneUtils
    public void setScores(int dernierScore, int meilleurScore, int niveau, int idUser, String prenom){
        this.idUser = idUser;
        this.prenom = prenom;

        dernierScoreLabel.setText("Score actuel : " + dernierScore);
        meilleurScoreLabel.setText("Meilleur score : " + meilleurScore);
        niveauLabel.setText("Niveau : " + niveau);
    }

    // BOUTON rejouer
    @FXML
    private void rejouer(){
        // récupère un Node de la scène
        Button bouton = (Button) dernierScoreLabel.getScene().getFocusOwner();
        Stage popup = (Stage) dernierScoreLabel.getScene().getWindow();
        popup.close();

        // Relance une partie avec le même utilisateur
        SceneUtils.lancerNouvellePartie(bouton, idUser, prenom);
    }

    // BOUTON quitter
    @FXML
    private void quitter(){
        Stage stage = (Stage) dernierScoreLabel.getScene().getWindow();
        stage.close();
        System.exit(0);
    }
}
