package invaders.factory;

import invaders.physics.Vector2D;
import invaders.strategy.ProjectileStrategy;
import javafx.scene.image.Image;

import java.util.Vector;

public interface ProjectileFactory {
    public Projectile createProjectile(Vector2D position, ProjectileStrategy strategy, Image image);
}
