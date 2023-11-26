package invaders.entities.State;

import invaders.entities.Builder.Bunker;

public class RedState implements BunkerState {

    private Bunker bunker;

    public RedState(Bunker bunker) {
        this.bunker = bunker;
        this.bunker.setImage("w1.png");
    }

    public void damaged() {
        return;
    }
}
