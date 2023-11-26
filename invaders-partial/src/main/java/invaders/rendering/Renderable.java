package invaders.rendering;

import invaders.physics.Collider;
import javafx.scene.image.Image;

/**
 * Represents something that can be rendered
 */
public interface Renderable extends Collider {

    Image getImage();

    Renderable.Layer getLayer();

    /**
     * The set of available layers
     */
    public static enum Layer {
        BACKGROUND, FOREGROUND, EFFECT
    }

}