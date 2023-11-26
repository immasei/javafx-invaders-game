package invaders.entities.Builder;

import invaders.engine.GameEngine;
import invaders.logic.Damagable;
import invaders.physics.*;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;
import java.io.File;

public abstract class NPC implements Moveable, Damagable, Renderable {

    protected Vector2D position;
    protected final double width;
    protected final double height;
    protected double health;
    protected double speed;
    protected String behaviour;
    protected Image image;
    protected int isUFO;
    protected int damage;

    public NPC(Vector2D position, double width, double height, double health, double speed, String behaviour) {
        this.position = position;
        this.health = health;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.behaviour = behaviour;
    }

    @Override
    public void up() {
        return;
    }

    @Override
    public void down() {
        return;
    }

    @Override
    public void left() {
        this.position.setX(this.position.getX() - this.speed);
    }

    @Override
    public void right() {
        this.position.setX(this.position.getX() + this.speed);
    }

    @Override
    public void takeDamage(double amount) {
        this.health -= amount;
    }

    @Override
    public boolean isAlive() {
        return this.health > 0;
    }

    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public Renderable.Layer getLayer() {
        return Renderable.Layer.FOREGROUND;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public Vector2D getPosition(){
        return position;
    }

    public void setImage(String img) {
        this.image = new Image(new File("src/main/resources/" + img).toURI().toString(), width, height, true, true);
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getBehaviour() {
        return behaviour;
    };

    public boolean reverse() {
        return false;
    };

    public boolean shootEnd() {
        return false;
    }

    public boolean attackEnd() {
        return false;
    }

    public boolean isVulnerable(GameEngine engine) {
        return false;
    }

    public void alterDirection() {
        return;
    }

    public void shoot(GameEngine engine) {
        return;
    }

    public boolean isAlly(NPC c) {
        try {
            return (this.isUFO / c.isUFO) > 0;
        } catch (Exception exception) {
            return false;
        }
    }

    public double attack() {
        return this.damage;
    }

    public void speedUp(){
        return;
    }
}

