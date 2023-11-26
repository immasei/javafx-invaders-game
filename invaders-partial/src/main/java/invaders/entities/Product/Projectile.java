package invaders.entities.Product;

import invaders.entities.*;
import invaders.entities.Builder.NPC;
import invaders.physics.Vector2D;

public abstract class Projectile extends NPC {

    public Projectile(Vector2D position, String behaviour, double speed) {
        super(position, 15, 15, 1, speed, behaviour);
        setImage(new EntityImage(this).getImage(behaviour));
        this.damage = 1;
    }
}