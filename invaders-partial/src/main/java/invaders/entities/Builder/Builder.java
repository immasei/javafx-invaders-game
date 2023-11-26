package invaders.entities.Builder;
import invaders.physics.Vector2D;

public interface Builder {
    Builder setColor(String color);
    Builder setPosition(Vector2D position);
    Builder setWidth(double width);
    Builder setHeight(double height);
    Builder setHealth(double health);
    Builder setSpeed(double speed);
    Builder setBehaviour(String behaviour);
    NPC buildUFO();
    NPC buildBunker();
    NPC buildPlayer();
}