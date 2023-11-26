package invaders;

import javafx.application.Application;
import javafx.stage.Stage;
import invaders.singleton.GameEngine;
import invaders.engine.GameWindow;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameEngine model = GameEngine.getInstance();
        GameWindow window = new GameWindow(model);

        model.setGameWindow(window);
        window.run();

        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.run();
    }
}
