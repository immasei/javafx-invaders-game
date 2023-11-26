package invaders.rendering;

import invaders.physics.Vector2D;
import javafx.scene.image.Image;

/**
 * Represents something that can be rendered
 */
public interface Renderable {

    public Image getImage();

    public double getWidth();
    public double getHeight();

    public Vector2D getPosition();

    public Renderable.Layer getLayer();

    public boolean isAlive();
    public void takeDamage(double amount);

    public double getHealth();

    public default int getValue() {
        return 0;
    }

    public void setLives(double lives);

    /**
     * The set of available layers
     */
    public static enum Layer {
        BACKGROUND, FOREGROUND, EFFECT
    }

    public default boolean isColliding(Renderable col) {
        double minX1 = this.getPosition().getX();
        double maxX1 = this.getPosition().getX() + this.getWidth();
        double minY1 = this.getPosition().getY();
        double maxY1 = this.getPosition().getY() + this.getHeight();

        double minX2 = col.getPosition().getX();
        double maxX2 = col.getPosition().getX() + col.getWidth();
        double minY2 = col.getPosition().getY();
        double maxY2 = col.getPosition().getY() + col.getHeight();

        if (maxX1 < minX2 || maxX2 < minX1) {
            return false; // No overlap in the x-axis
        }

        if (maxY1 < minY2 || maxY2 < minY1) {
            return false; // No overlap in the y-axis
        }

        return true; // Overlap in both x-axis and y-axis
    }

    public String getRenderableObjectName();
}
