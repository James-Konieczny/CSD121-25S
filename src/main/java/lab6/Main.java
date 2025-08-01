package lab6;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Game game = new Game();
        primaryStage.setTitle("My First Game");
        primaryStage.setScene(game.getScene());
        primaryStage.show();
        game.start();
    }
    public static void main(String[] args) {
        launch(args); // Just in case :)
    }
}