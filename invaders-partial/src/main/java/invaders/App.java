package invaders;

import javafx.application.Application;
import javafx.stage.Stage;
import invaders.engine.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.Map;
import java.io.*;

public class App extends Application {

    private static double width;
    private static double height;
    private static final String config = "src/main/resources/config.json";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Map<String, String> params = getParameters().getNamed();

        try {
            //LOAD CONFIG
            JSONObject conf = (JSONObject) new JSONParser().parse(new FileReader(config));
            JSONObject game = (JSONObject) conf.get("Game");
            JSONObject size = (JSONObject) game.get("size");
            width = Double.parseDouble(size.get("x").toString());
            height = Double.parseDouble(size.get("y").toString());
        } catch (Exception e) {
            System.out.println("Config file error");
        }

        GameEngine model = new GameEngine(config);
        GameWindow window = new GameWindow(model, width, height);
        window.run();

        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.run();
    }
}
