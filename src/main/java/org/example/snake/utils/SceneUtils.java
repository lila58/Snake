package org.example.snake.utils;

import org.example.snake.controllers.GameOverController;
import org.example.snake.database.UserDAO;
import org.example.snake.game.SnakeGameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneUtils {

    private static final String GAME_FXML = "Game.fxml";

    // Retourne le chemin complet du FXML dans /niveau2/
    private static String resolvePath(String fxml) {
        return "/niveau2/" + fxml;
    }

    public static void changerScene(String fxml, Node source){
        try{
            FXMLLoader loader = new FXMLLoader(SceneUtils.class.getResource(resolvePath(fxml)));
            Parent root = loader.load();

            Stage stage = (Stage) source.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setWidth(800);
            stage.setHeight(800);
            stage.centerOnScreen();
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void changerSceneAvecUtilisateur(String fxml, Node source, int idUser, String prenom) {
        try{
            FXMLLoader loader = new FXMLLoader(SceneUtils.class.getResource(resolvePath(fxml)));
            Parent root = loader.load();

            Object controller = loader.getController();
            if(controller instanceof SnakeGameController gameController){
                gameController.setUtilisateur(idUser, prenom);
            }

            Stage stage = (Stage) source.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setWidth(800);
            stage.setHeight(800);
            stage.centerOnScreen();
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void changerSceneAvecScore(String fxml, Node source, int dernierScore, int idUser, int niveau, String prenom){
        try{
            FXMLLoader loader = new FXMLLoader(SceneUtils.class.getResource(resolvePath(fxml)));
            Parent root = loader.load();

            Object controller = loader.getController();

            int meilleurScore = new UserDAO().getMeilleurScore(idUser, niveau);

            if(controller instanceof GameOverController gameOverController){
                gameOverController.setScores(dernierScore, meilleurScore, niveau, idUser, prenom);
            }

            Stage stage = (Stage) source.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setWidth(800);
            stage.setHeight(800);
            stage.centerOnScreen();
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void lancerNouvellePartie(Node source, int idUser, String prenom){
        changerSceneAvecUtilisateur(GAME_FXML, source, idUser, prenom);
    }
}
