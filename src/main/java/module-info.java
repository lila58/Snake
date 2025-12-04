module org.example.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop; // C'est cette ligne qui corrige ton erreur rouge !

    opens org.example.snake to javafx.fxml;
    exports org.example.snake;

    // Autorise JavaFX à lire tes contrôleurs et tes modèles
    opens org.example.snake.controller to javafx.fxml;
    opens org.example.snake.model to javafx.base;
}