package invaders.entities.Factory;

import invaders.entities.EntityImage;
import invaders.entities.Product.*;
import invaders.physics.Vector2D;

public class PlayerCreator implements ProjectileCreator {

    @Override
    public Projectile createProjectile(Vector2D position, double speed, String behaviour) {
        return new PlayerProjectile(position,
                                    "player",
                                    new EntityImage(this).getBehaviour(behaviour).apply(speed));
    }
}
