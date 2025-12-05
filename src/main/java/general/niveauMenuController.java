package general;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import niveau3.labyrintheController; // Import nécessaire

import java.io.IOException;

public class niveauMenuController {

    public Button user;
    public Label userName;
    public Button quitter;
    @FXML
    private Button niveau1;
    
    @FXML
    private Button niveau2;
    
    @FXML
    private Button niveau3;

    @FXML
    private Button btnRetour;

    @FXML
    public void initialize() {
        // Vous pouvez aussi utiliser les setOnAction ici si vous ne voulez pas les mettre dans le FXML
        // niveau1.setOnAction(e -> lancerNiveau1(e));
        niveau3.setOnAction(this::lancerNiveau3);
        
        // Ajout de l'action pour le bouton User
        user.setOnAction(this::retourMenuPrincipal);
    }

    @FXML
    void lancerNiveau3(ActionEvent event) {
        try {
            // 1. Charger la vue du niveau (exemple avec un fichier hypothétique niveau1.fxml)
            // Remplacez par le vrai chemin de votre fichier FXML de jeu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/niveau3/labyrinthe.fxml"));
            Parent root = loader.load();

            // 2. Créer la nouvelle scène
            Stage stage = new Stage();
            stage.setTitle("Niveau 3");
            Scene scene = new Scene(root); // Créer la scène d'abord pour la passer au contrôleur
            stage.setScene(scene);

            // IMPORTANT : Récupérer le contrôleur et activer les touches clavier
            labyrintheController controller = loader.getController();
            controller.setupKeyControls(scene);

            stage.show();
            
            // Donner le focus au jeu pour que le clavier réponde tout de suite
            root.requestFocus();

            // 3. Cacher la fenêtre actuelle (le menu)
            ((Node)(event.getSource())).getScene().getWindow().hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void lancerNiveau2(ActionEvent event) {
        // Logique similaire à lancerNiveau1
    }

    @FXML
    void lancerNiveau1(ActionEvent event) {
        System.out.println("Lancement Niveau 1...");
        // Logique similaire à lancerNiveau1
    }

    @FXML
    void retourMenuPrincipal(ActionEvent event) {
        try {
            // Retour au tableau de bord (InterfaceJoueur)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interface/InterfaceJoueur.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
