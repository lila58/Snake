package niveau3;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class labyrintheController {
    @FXML
    public AnchorPane rootPane;

    public Button pause = new Button("Pause");
    public Label scoreLabel;
    public int score=0 ;
    private final int width;
    private final int height;
    private final int mur = 1;
    private static final int MAX_SNAKE_LENGTH = 50;  // Serpent max 50 segments
    private static final double INITIAL_SPEED = 200;
    private static final double FINAL_SPEED = 120;
    private boolean maxLengthReached = false;

    private final int tileSize = 25;
    private static class tile{
        int x;
        int y;

        public  tile(int x,int y){
            this.x=x;
            this.y=y;
        }
    }

    // Helper class for zones
    private static class Rectangle {
        int x, y, width, height;
        public Rectangle(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    // Liste de zones sûres prédéfinies
    private final Rectangle[] safeZones = {
            new Rectangle(5, 5, 14, 3),    // Bande horizontale haute
            new Rectangle(10, 8, 4, 8),    // Centre (le plus sûr)
            new Rectangle(15, 5, 3, 14),   // Bande horizontale basse
    };


    private final int level = 3;
    private final ArrayList<tile> snake;
    Random random;
    tile head;
    private int velocityX;
    private int velocityY;
    private final tile food;
    private final double  gameSpeed = 300;

    // private static final double SPEED_INCREMENT = 0.95;
    //private static final double MIN_SPEED = 50;
    private Timeline timeline;

    private final int[][] maze = {
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, // 0
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 1 Large couloir haut
            {1,0,1,1,1,0,1,1,0,1,1,1,1,1,1,0,1,1,0,1,1,1,0,1}, // 2  Obstacles espacés
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 3 Couloir circulation
            {1,0,1,0,1,1,0,1,1,1,0,0,0,0,1,1,1,0,1,1,0,1,0,1}, // 4
            {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1}, // 5
            {1,0,1,1,0,1,1,1,0,1,1,0,0,1,1,0,1,1,1,0,1,1,0,1}, // 6
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 7 Couloir large
            {1,1,1,0,1,1,0,1,0,1,0,0,0,0,1,0,1,0,1,1,0,1,1,1}, // 8
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 9
            {1,0,1,1,0,1,0,1,0,0,0,0,0,0,0,0,1,0,1,0,1,1,0,1}, // 10
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 11 CENTRE - Zone refuge
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 12 CENTRE - Zone refuge
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 13 CENTRE - Zone refuge
            {1,0,1,1,0,1,0,1,0,0,0,0,0,0,0,0,1,0,1,0,1,1,0,1}, // 14
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 15
            {1,1,1,0,1,1,0,1,0,1,0,0,0,0,1,0,1,0,1,1,0,1,1,1}, // 16
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 17 Couloir large
            {1,0,1,1,0,1,1,1,0,1,1,0,0,1,1,0,1,1,1,0,1,1,0,1}, // 18
            {1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1}, // 19
            {1,0,1,0,1,1,0,1,1,1,0,0,0,0,1,1,1,0,1,1,0,1,0,1}, // 20
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, // 21 Couloir circulation
            {1,0,1,1,1,0,1,1,0,1,1,1,1,1,1,0,1,1,0,1,1,1,0,1}, // 22 Obstacles espacés
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}  // 23
    };

    @FXML
    public Canvas labyrinthe;

    private GraphicsContext gc;

    public labyrintheController(){
        width = 600;
        height = 600;
        head = new tile(12, 12);
        snake = new ArrayList<>();
        food = new tile(10, 10);
        random = new Random();
        callFood();
    }

    public void draw(){
        // Efface tout d'abord
        gc.clearRect(0, 0, labyrinthe.getWidth(), labyrinthe.getHeight());

        // Parcourt toute la matrice
        for (int i = 0; i < maze.length; i++) {           // i = ligne (Y)
            for(int j = 0; j < maze[i].length; j++) {     // j = colonne (X)

                // Si c'est un mur, dessine-le
                if (maze[i][j] == mur) {
                    gc.setFill(Color.DARKGRAY);
                    gc.fillRect(j * tileSize, i * tileSize, tileSize, tileSize);

                    // Optionnel : ajoute des bordures pour un effet "briques"
                    gc.setStroke(Color.BLACK);
                    gc.setLineWidth(1);
                    gc.strokeRect(j * tileSize, i * tileSize, tileSize, tileSize);
                }
            }
        }
        //food
        gc.setFill(Color.RED);
        gc.fillRect(food.x* tileSize, food.y * tileSize, tileSize, tileSize);
        //head
        gc.setFill(Color.GREEN);
        gc.fillRect(head.x * tileSize, head.y * tileSize, tileSize, tileSize);

        //Snake Body
        gc.setFill(Color.GREEN);
        for (tile snakePart : snake) {
            gc.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
        }

    }

    public void initialize(){
        gc = labyrinthe.getGraphicsContext2D();

        // S'assurer que le Canvas peut recevoir le focus
        labyrinthe.setFocusTraversable(true);
        labyrinthe.requestFocus();

        draw();
        timeline = new Timeline(new KeyFrame(Duration.millis(gameSpeed), e -> run()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        pause.setOnAction(e -> {
            if(timeline.getStatus()==Animation.Status.RUNNING){
                timeline.pause();
            }else{
                timeline.play();
            }
        });
    }
    private void run() {
        updateMove();
        draw();

    }

    public boolean isWall(int x, int y) {
        if (x < 0 || x >= maze[0].length || y < 0 || y >= maze.length) {
            return true; // Hors limites = considéré comme mur
        }
        return maze[y][x] == mur;
    }

    public boolean isEmpty(int x, int y) {
        if (x < 0 || x >= maze[0].length || y < 0 || y >= maze.length) {
            return false;
        }
        int vide = 0;
        return maze[y][x] == vide;
    }

    // Check if a snake body occupies a tile
    private boolean isOccupiedBySnake(tile t) {
        // Check head
        if (head.x == t.x && head.y == t.y) return true;
        // Check body
        for (tile part : snake) {
            if (part.x == t.x && part.y == t.y) return true;
        }
        return false;
    }

    //call food
    public void callFood(){
        tile newFood;
        int attempts = 0;
        
        do {
            // Position complètement aléatoire dans le labyrinthe
            newFood = new tile(
                random.nextInt(maze[0].length),  // Colonne aléatoire (0-23)
                random.nextInt(maze.length)      // Ligne aléatoire (0-23)
            );
            attempts++;
            
            // Sécurité : si on essaie 200 fois sans succès
            if (attempts > 200) {
                System.out.println("⚠️ Impossible de placer un fruit !");
                break;
            }
            
        } while (!isEmpty(newFood.x, newFood.y) || isOccupiedBySnake(newFood));
        
        food.x = newFood.x;
        food.y = newFood.y;
    }

    private boolean collision(tile tile1, tile tile2){
        return  tile1.x == tile2.x && tile1.y==tile2.y;
    }
    private boolean collisionWall(){
        return isWall(head.x + velocityX, head.y + velocityY);
    }
    private boolean checkSelfCollision() {
        for (tile t : snake) {
            if (collision(head, t)) {
                return true;
            }
        }
        return false;
    }
    //update move
    public void updateMove(){
        

        if(collision(head, food)){
            callFood();
            
            // Grandir seulement si pas encore à 50 segments
            if (snake.size() < MAX_SNAKE_LENGTH) {
                snake.add(new tile(head.x, head.y));
                updateSpeed();
            } else {
                // Message une fois quand on atteint 50
                if (!maxLengthReached) {
                    maxLengthReached = true;
                    System.out.println("🎉 Longueur MAX atteinte ! Mode High Score activé !");
                    showMaxLengthMessage();
                }
            }
            
            score += 2;
            scoreLabel.setText("Score : " + score);
        }

        if (collisionWall()) {
            timeline.stop();
            gameOver();
            return; // Quitter immédiatement
        }

        for(int i = snake.size() - 1; i >= 0; i--){
            tile SnakePart = snake.get(i);
            
            if(i == 0){
                // Le premier segment prend la position de la tête
                SnakePart.x = head.x;
                SnakePart.y = head.y;
            } else {
                // Chaque segment prend la position du segment précédent
                tile prevSnakePart = snake.get(i - 1);
                SnakePart.x = prevSnakePart.x;
                SnakePart.y = prevSnakePart.y;
            }
        }
        head.x += velocityX;
        head.y += velocityY;

        if (checkSelfCollision()) {
            timeline.stop();
            gameOver();
            return;
        }
    }
    //recuperation des touches
    public void setupKeyControls(Scene scene){
        scene.setOnKeyPressed(event -> {
            handleKeyPress(event);
            event.consume(); // Empêche la propagation de l'événement
        });

        // Force le canvas à garder le focus
        labyrinthe.setFocusTraversable(true);
        labyrinthe.setOnMouseClicked(e -> labyrinthe.requestFocus());
    }
    private void handleKeyPress(KeyEvent event) {
        KeyCode keyCode = event.getCode();
        if(keyCode == KeyCode.LEFT && velocityX == 0) { // Empêche le retour arrière
            velocityX = -1;
            velocityY = 0;
        } else if(keyCode == KeyCode.RIGHT && velocityX == 0) {
            velocityX = 1;
            velocityY = 0;
        } else if(keyCode == KeyCode.UP && velocityY == 0) {
            velocityX = 0;
            velocityY = -1;
        } else if(keyCode == KeyCode.DOWN && velocityY == 0) {
            velocityX = 0;
            velocityY = 1;
        }

    }

    private void gameOver() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interface/modalGameOver.fxml"));
            Parent root = loader.load();

            gameOverController controller = loader.getController();


            controller.setScore(score); // Changed monScoreActuel to score


            controller.setNiveauARelancer("labyrinthe.fxml");
            // Pour le niveau 2, tu écriras : controller.setNiveauARelancer("niveau2.fxml");

            // On récupère le stage actuel et on le passe au contrôleur
            Stage currentGameStage = (Stage) rootPane.getScene().getWindow();
            controller.setMainGameStage(currentGameStage);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            // IMPORTANT : Utiliser initOwner pour que le popup soit lié à la fenêtre principale
            stage.initOwner(currentGameStage);
            stage.initModality(javafx.stage.Modality.WINDOW_MODAL);

            stage.show(); // Plus besoin de showAndWait ni de fermer manuellement ici

            // Fermer la fenêtre actuelle si nécessaire
            // ((Stage) rootPane.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // FONCTION : Augmenter la vitesse (jusqu'à 50 segments seulement)
            private void updateSpeed() {
        int length = snake.size();

        // Calculer la nouvelle vitesse
        double progression = (double) length / MAX_SNAKE_LENGTH;
        double newSpeed = INITIAL_SPEED - (INITIAL_SPEED - FINAL_SPEED) * progression;

        // Recréer la timeline
        timeline.stop();
        timeline = new Timeline(new KeyFrame(Duration.millis(newSpeed), e -> run()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    // OPTIONNEL : Afficher un message quand on atteint 50 segments
    private void showMaxLengthMessage() {
        // Créer un label temporaire
        Label maxLabel = new Label("🎉 LONGUEUR MAX ! Mode High Score ! 🎉");
        maxLabel.setStyle(
                "-fx-font-size: 20px; " +
                        "-fx-text-fill: gold; " +
                        "-fx-background-color: rgba(0,0,0,0.7); " +
                        "-fx-padding: 10px; " +
                        "-fx-background-radius: 10px;"
        );

        // Positionner au centre
        maxLabel.setLayoutX(150);
        maxLabel.setLayoutY(280);

        rootPane.getChildren().add(maxLabel);

        // Faire disparaître après 3 secondes
        Timeline fadeOut = new Timeline(
                new KeyFrame(Duration.seconds(3), e -> rootPane.getChildren().remove(maxLabel))
        );
        fadeOut.play();
    }


}
