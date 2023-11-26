package invaders.entities.Builder;

import invaders.engine.*;
import invaders.entities.EntityImage;
import invaders.entities.Factory.PlayerCreator;
import invaders.entities.Product.Projectile;
import invaders.physics.Vector2D;

public class Player extends NPC {

    private Projectile projectile;

    public Player(Vector2D position, String color, double width, double height, double health, double speed, String behaviour) {
        super(position, width, height, health, speed, behaviour);
        setImage(new EntityImage(this).getImage(color));
        this.damage = 0;
        this.isUFO = 1;
    }

   @Override
    public void shoot(GameEngine engine){
        if (!engine.getRenderables().contains(projectile) && engine.getRenderables().contains(this)) {
            projectile = new PlayerCreator().createProjectile(
                                                new Vector2D(position.getX() + 13,
                                                            position.getY() - 15),
                                                                speed, behaviour);
            engine.addRenderables(projectile);
        }
        projectile.up();
    }
}