package invaders.entities.Builder;

import invaders.entities.State.*;
import invaders.physics.Vector2D;

public class Bunker extends NPC {

    private BunkerState state;

    public Bunker(Vector2D position, double width, double height, double health) {
        super(position, width, height, health, 0, "");
        this.damage = 0;
        this.isUFO = 0;
        this.state = new GreenState(this);
    }

    @Override
    public void takeDamage(double amount) {
        health -= amount;
        damaged();
    }

    public void damaged() {
        state.damaged();
    }

    public void changeState(BunkerState state) {
        this.state = state;
    }

}
