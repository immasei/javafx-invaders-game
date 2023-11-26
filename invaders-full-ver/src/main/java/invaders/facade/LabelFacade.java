package invaders.facade;

import javafx.scene.layout.Pane;

public class LabelFacade {
    private Score score;
    private Clock clock;

    public LabelFacade() {
        score = new Score();
        clock = new Clock();
    }

    public void show(Pane pane) {
        pane.getChildren().add(score.getScoreLabel());
        pane.getChildren().add(clock.getClockLabel());
    }

    public void update(int value) {
        score.update(value);
        clock.update();
    }

    public void stop() {
        score.stop();
        clock.stop();
    }

    public void reset() {
        score.reset();
        clock.reset();
    }

    public void undo(int score, long time) {
        this.score.undo(score);
        this.clock.undo(time);
    }

    public int getScore() {
        return Integer.parseInt(score.getScoreLabel().getText());
    }
}
