package niveau3;
import database.requestDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class gameOverController {

    @FXML
    private Button rejouer;

    @FXML
    private Button menu;

    @FXML
    private Label scoreLabel;

    // Variable pour stocker le nom du fichier FXML à relancer
    private String niveauARelancer;

    // Variable pour stocker le Stage principal du jeu
    private Stage mainGameStage;

    @FXML
    public void initialize() {
        // Action pour le bouton Rejouer
        rejouer.setOnAction(event -> {
            try {
                // Vérification de sécurité
                if (niveauARelancer == null || niveauARelancer.isEmpty()) {
                    System.out.println("Erreur : Aucun niveau spécifié à relancer !");
                    return;
                }

                // Fermer la fenêtre de Game Over (le petit popup)
                Stage currentPopupStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentPopupStage.close();

                // Charger la nouvelle scène (réinitialisation du jeu)
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(niveauARelancer));
                Scene scene = new Scene(fxmlLoader.load(), 800, 800);

                // Configuration du contrôleur du jeu
                Object controller = fxmlLoader.getController();
                if (controller instanceof labyrintheController) {
                    ((labyrintheController) controller).setupKeyControls(scene);
                }

                // Si on a bien reçu le stage principal, on réutilise celui-ci !
                if (mainGameStage != null) {
                    mainGameStage.setScene(scene);
                    mainGameStage.show();
                } else {
                    // Fallback si jamais le stage n'a pas été passé (ancien comportement)
                    Stage gameStage = new Stage();
                    gameStage.setTitle("Jeu");
                    gameStage.setScene(scene);
                    gameStage.show();
                }

                scene.getRoot().requestFocus();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Action pour le bouton Menu
        menu.setOnAction(event -> {
            try {
                // Fermer la fenêtre de Game Over
                Stage currentPopupStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentPopupStage.close();

                // Charger le menu (Correction du chemin vers menuNiveau.fxml)
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/interface/menuNiveau.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 800);


            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    // Méthode appelée depuis labyrintheController pour passer le score
    public void setScore(int score) {
        scoreLabel.setText("Score : " + score);
        requestDB db = new requestDB();
        // Remplacez '1' par l'ID réel du joueur connecté (si vous avez un système de login)
        // Remplacez '3' par le numéro du niveau actuel
        db.saveScore(2, 3, score);
    }

    // NOUVELLE MÉTHODE : Permet de dire au Game Over quel fichier recharger
    public void setNiveauARelancer(String fxmlFile) {
        this.niveauARelancer = fxmlFile;
    }

    // NOUVELLE MÉTHODE : Permet de passer le Stage principal
    public void setMainGameStage(Stage stage) {
        this.mainGameStage = stage;
    }
}