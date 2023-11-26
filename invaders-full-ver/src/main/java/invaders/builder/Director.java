package invaders.builder;

import invaders.singleton.GameEngine;
import invaders.gameobject.Bunker;
import invaders.gameobject.Enemy;
import invaders.physics.Vector2D;
import javafx.scene.image.Image;
import org.json.simple.JSONObject;

import java.io.File;

public class Director {

    public Bunker constructBunker(BunkerBuilder builder, JSONObject eachBunkerInfo){
        int x = ((Long)((JSONObject)eachBunkerInfo.get("position")).get("x")).intValue();
        int y = ((Long)((JSONObject)eachBunkerInfo.get("position")).get("y")).intValue();
        int width = ((Long)((JSONObject)eachBunkerInfo.get("size")).get("x")).intValue();
        int height = ((Long)((JSONObject)eachBunkerInfo.get("size")).get("y")).intValue();

        builder.reset();
        builder.buildPosition(new Vector2D(x,y));
        builder.buildImage(new Image(new File("src/main/resources/bunkerGreen.png").toURI().toString(), width, height, true, true));
        builder.buildLives(3);

        return (Bunker) builder.createGameObject();
    }

    public Enemy constructEnemy(GameEngine engine,EnemyBuilder builder, JSONObject eachEnemyInfo){
        int x = ((Long)((JSONObject)eachEnemyInfo.get("position")).get("x")).intValue();
        int y = ((Long)((JSONObject)eachEnemyInfo.get("position")).get("y")).intValue();
        String strategy = (String)(eachEnemyInfo.get("projectile"));

        builder.reset();
        builder.buildPosition(new Vector2D(x,y));
        builder.buildLives(1);
        builder.buildImageAndStrategy(strategy);

        return (Enemy) builder.createGameObject();

    }
}
