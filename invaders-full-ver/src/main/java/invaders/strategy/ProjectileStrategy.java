package invaders.strategy;

import invaders.factory.Projectile;
import invaders.physics.Vector2D;

public interface ProjectileStrategy {
   public void update(Projectile p);
   public int getValue();
}
