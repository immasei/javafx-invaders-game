package invaders.entities.Builder;

public class Director {
    private Builder builder;

    public Director(Builder builder) {
        this.builder = builder;
    }

    public NPC constructUFO() {
        return builder.buildUFO();
    }

    public NPC constructBunker() {
        return builder.buildBunker();
    }

    public NPC constructPlayer() {
        return builder.buildPlayer();
    }
}

