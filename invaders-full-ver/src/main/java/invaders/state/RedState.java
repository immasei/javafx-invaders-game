package invaders.state;
import invaders.gameobject.Bunker;
import javafx.scene.image.Image;
import java.io.File;

public class RedState implements BunkerState {
    private Bunker bunker;

    public RedState(Bunker bunker){
        this.bunker = bunker;
        bunker.setImage(new Image(new File("src/main/resources/bunkerRed.png").toURI().toString()));
    }

    @Override
    public void takeDamage() {
        // bunker.
    }
}
