package invaders.physics;

public interface Collider {

    double getWidth();

    double getHeight();

    Vector2D getPosition();

    default boolean isColliding(Collider col) {
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
}