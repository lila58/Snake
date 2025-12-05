package niveau3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class niveau3Application extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // On charge le fichier FXML du labyrinthe
        FXMLLoader fxmlLoader = new FXMLLoader(niveau3Application.class.getResource("labyrinthe.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);


        labyrintheController controller = fxmlLoader.getController();
        controller.setupKeyControls(scene);

        stage.setTitle("Jeu du Labyrinthe");
        stage.setScene(scene);
        stage.show();

        // Donner le focus pour que le clavier fonctionne tout de suite
        scene.getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch();
    }
}
