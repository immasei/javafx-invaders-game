package invaders.entities.State;

import invaders.entities.Builder.Bunker;

public class YellowState implements BunkerState {

    private Bunker bunker;

    public YellowState(Bunker bunker) {
        this.bunker = bunker;
        this.bunker.setImage("w2.png");
    }

    public void damaged() {
        bunker.changeState(new RedState(bunker));
    }
}
