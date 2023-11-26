package invaders.entities;

import invaders.physics.Vector2D;
import invaders.rendering.Renderable;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class SpaceBackground implements Renderable {

	private Rectangle space;

	public SpaceBackground(Pane pane){

		double width = pane.getWidth();
		double height = pane.getHeight();
		space = new Rectangle(0, 0, width, height);

		// BACKGROUND COLOR
//		space.setFill(Paint.valueOf("#7C7C8E")); //lighter background: please remember to switch to black enemy in EntityImage if you intend to use this background
		space.setFill(Paint.valueOf("BLACK"));

		space.setViewOrder(1000.0);

		pane.getChildren().add(space);

	}

	@Override
	public Image getImage() {
		return null;
	}

	@Override
	public Layer getLayer() {
		return Layer.BACKGROUND;
	}

	@Override
	public double getWidth() {
		return 0;
	}

	@Override
	public double getHeight() {
		return 0;
	}

	@Override
	public Vector2D getPosition() {
		return null;
	}
}
