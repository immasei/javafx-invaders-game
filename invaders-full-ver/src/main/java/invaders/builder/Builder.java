package invaders.builder;
import invaders.gameobject.GameObject;
import invaders.physics.Vector2D;
import javafx.scene.image.Image;

public abstract class Builder {
    public abstract void buildPosition(Vector2D position);
    public abstract void buildLives(int live);
    public abstract GameObject createGameObject();
    public abstract void reset();
}
