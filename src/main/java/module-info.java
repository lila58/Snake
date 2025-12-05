<<<<<<< HEAD
module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop; // C'est cette ligne qui corrige ton erreur rouge !

    exports niveau3;
    exports general;
    opens org.example.snake to javafx.fxml;
    exports org.example.snake;

    opens niveau3;
    opens general to javafx.fxml;
    opens org.example.demo5 to javafx.fxml;
    // Autorise JavaFX à lire tes contrôleurs et tes modèles
    opens org.example.snake.controller to javafx.fxml;
    opens org.example.snake.model to javafx.base;
    exports org.example.demo5;

}