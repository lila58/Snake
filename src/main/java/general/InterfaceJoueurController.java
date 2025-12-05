package general;

import database.InfosJoueurDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Map;

public class InterfaceJoueurController implements Initializable {

    @FXML private VBox paneInfos;
    @FXML private VBox paneScores;
    @FXML private VBox paneClassement;

    @FXML private Label lblPseudo;
    @FXML private Label lblEmail;

    @FXML private TableView<?> tableMesScores; // Remplacer ? par votre modèle de données
    @FXML private TableView<?> tableClassement; // Remplacer ? par votre modèle de données
    @FXML private ComboBox<String> comboNiveauFilter;

    private int currentUserId=2; // L'ID du joueur connecté, à définir lors de la connexion

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialiser les vues (afficher infos par défaut)
        afficherInfos(null);

        // TODO: Initialiser le combo box des niveaux
        comboNiveauFilter.getItems().addAll("Niveau 1", "Niveau 2", "Niveau 3");
    }

    // Méthode pour passer l'ID utilisateur depuis la page de connexion
    public void initData(int userId) {
        this.currentUserId = userId;
        chargerInfosJoueur();
    }

    private void chargerInfosJoueur() {
        InfosJoueurDB db = new InfosJoueurDB();

        // Exemple de récupération (nécessite d'adapter InfosJoueurDB pour retourner un objet)
        // Map<String, String> infos = db.récupérerInfosJoueur(currentUserId);
        // if (infos != null) {
        //    lblPseudo.setText(infos.get("username"));
        //    lblEmail.setText(infos.get("email"));
        // }
    }

    @FXML
    void afficherInfos(ActionEvent event) {
        paneInfos.setVisible(true);
        paneScores.setVisible(false);
        paneClassement.setVisible(false);
    }

    @FXML
    void afficherScores(ActionEvent event) {
        paneInfos.setVisible(false);
        paneScores.setVisible(true);
        paneClassement.setVisible(false);
        // TODO: Charger les scores ici
    }

    @FXML
    void afficherClassement(ActionEvent event) {
        paneInfos.setVisible(false);
        paneScores.setVisible(false);
        paneClassement.setVisible(true);
        // TODO: Charger le classement ici
    }

    @FXML
    void deconnexion(ActionEvent event) {
        // Logique de fermeture ou retour au menu principal
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        // TODO: Réouvrir la fenêtre de login
        System.out.println("Déconnexion effectuée");
    }
}