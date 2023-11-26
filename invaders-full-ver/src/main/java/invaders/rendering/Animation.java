package invaders.rendering;

import javafx.scene.image.Image;

public interface  Animation {
    public String getName();
    public Image getCurrentFrame();
    public void next();
}
