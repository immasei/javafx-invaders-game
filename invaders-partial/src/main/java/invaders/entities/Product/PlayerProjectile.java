package invaders.entities.Product;
import invaders.engine.GameEngine;
import invaders.physics.Vector2D;

public class PlayerProjectile extends Projectile {

    public PlayerProjectile(Vector2D position, String behaviour, double speed) {
        super(position, behaviour, speed);
        this.isUFO = 1;
    }

    @Override
    public void up() {
        this.position.setY(this.position.getY() - this.speed);
    };

    @Override
    public boolean shootEnd() {
        return true;
    }

    @Override
    public boolean isVulnerable(GameEngine engine) {
        this.health = 0;
        engine.shootEnd();
        return true;
    }
}
