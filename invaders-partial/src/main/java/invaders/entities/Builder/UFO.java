package invaders.entities.Builder;

import invaders.entities.*;
import invaders.physics.Vector2D;

public class UFO extends NPC {

    private double speedUp;

    public UFO(Vector2D position, double width, double height, double health, double speed, String behaviour) {
        super(position, width, height, health, speed, behaviour);
        setImage(new EntityImage(this).getImage(behaviour));
        this.damage = 3;    //hardcode
        this.isUFO = -1;    //hardcode
        this.speedUp = 0.1; //hardcode
    }

    @Override
    public void down() {
        this.position.setY(this.position.getY() + height/5);
    }

    @Override
    public boolean reverse() {
        return true;
    }

    @Override
    public void alterDirection() {
        down();
        setSpeed(getSpeed() * -1);
    }

    @Override
    public void speedUp() {
        if (this.speed < 0)
            this.speed -= speedUp;
        else
            this.speed += speedUp;
    }

}

//https://forums.oracle.com/ords/apexds/post/convert-string-to-color-object-6876
