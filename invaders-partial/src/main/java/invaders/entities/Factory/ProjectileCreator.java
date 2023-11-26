package invaders.entities.Factory;
import invaders.entities.Product.Projectile;
import invaders.physics.Vector2D;

public interface ProjectileCreator {

    Projectile createProjectile(Vector2D position, double speed, String behaviour);
}
