package invaders.state;

import invaders.gameobject.Bunker;
import javafx.scene.image.Image;
import java.io.File;

public class GreenState implements BunkerState {
    private Bunker bunker;

    public GreenState(Bunker bunker){
        this.bunker = bunker;
        bunker.setImage(new Image(new File("src/main/resources/bunkerGreen.png").toURI().toString()));
    }

    @Override
    public void takeDamage() {
        bunker.setState(new YellowState(bunker));
    }
}
