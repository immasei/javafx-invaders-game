package invaders.entities.Product;

import invaders.engine.GameEngine;
import invaders.physics.Vector2D;

public class UFOsProjectile extends Projectile {

    public UFOsProjectile(Vector2D position, String behaviour, double speed) {
        super(position, behaviour, speed);
        this.isUFO = -1;
    }

    @Override
    public void down() {
        this.position.setY(this.position.getY() + this.speed);
    };

    @Override
    public boolean attackEnd() {
        return true;
    }

    @Override
    public boolean isVulnerable(GameEngine engine) {
        this.health = 0;
        engine.attackEnd();
        return true;
    }

}
