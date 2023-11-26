package invaders.facade;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class Score {
    private Label score;
    private int scoreNumber;
    private boolean stop;

    public Score () {
        stop = false;
        scoreNumber = 0;

        score = new Label(String.format("%04d", scoreNumber));
        score.setTextFill(Color.WHITE);
        score.setTranslateX(15);
        score.setTranslateY(15);

        Font font = Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 25);
        score.setFont(font);
    }

    public Label getScoreLabel() {
        return score;
    }

    public void update(int value) {
        scoreNumber += value;
        if (!stop)
            score.setText(String.format("%04d", scoreNumber));
    }

    public void stop() {
        stop = true;
        score.setTextFill(Color.ORANGERED);
    }

    public void reset() {
        scoreNumber = 0;
        stop = false;
        score.setTextFill(Color.WHITE);
    }

    public void undo(int scoreNumber) {
        this.scoreNumber = scoreNumber;
        stop = false;
        score.setTextFill(Color.WHITE);
    }
}
