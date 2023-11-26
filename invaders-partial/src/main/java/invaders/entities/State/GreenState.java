package invaders.entities.State;

import invaders.entities.Builder.Bunker;

public class GreenState implements BunkerState {

    private Bunker bunker;

    public GreenState(Bunker bunker) {
        this.bunker = bunker;
        this.bunker.setImage("w3.png");
    }

    public void damaged() {
        bunker.changeState(new YellowState(bunker));
    }
}
