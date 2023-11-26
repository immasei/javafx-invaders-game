package invaders.entities.Builder;

import invaders.physics.Vector2D;

public class NPCBuilder implements Builder {
    private Vector2D position;
    private double width;
    private double height;
    private double health;
    private double speed;
    private String behaviour;
    private String color;

    public Builder setColor(String color) {
        this.color = color;
        return this;
    }

    public Builder setPosition(Vector2D position) {
        this.position = position;
        return this;
    }

    public Builder setWidth(double width) {
        this.width = width;
        return this;
    }

    public Builder setHeight(double height) {
        this.height = height;
        return this;
    }

    public Builder setHealth(double health) {
        this.health = health;
        return this;
    }

    public Builder setSpeed(double speed) {
        this.speed = speed;
        return this;
    }

    public Builder setBehaviour(String behaviour) {
        this.behaviour = behaviour;
        return this;
    }

    public NPC buildUFO() {
        return new UFO(position, width, height, health, speed, behaviour);
    }

    public NPC buildBunker() {
        return new Bunker(position, width, height, health);
    }

    public NPC buildPlayer() {
        return new Player(position, color, width, height, health, speed, behaviour);
    }
}
