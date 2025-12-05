package org.example.snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application{

    @Override
    public void start(Stage stage) throws Exception{

        FXMLLoader loader = new FXMLLoader(
                HelloApplication.class.getResource("/niveau2/MainMenu.fxml")
        );

        Scene scene = new Scene(loader.load());
        stage.setTitle("Snake - Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
