package invaders.facade;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import java.text.*;

public class Clock {
    private Label time;
    private DateFormat timeFormat;
    private long startTime;
    private boolean stop;

    public Clock() {
        stop = false;

        time = new Label();
        timeFormat = new SimpleDateFormat("mm:ss");
        startTime = System.currentTimeMillis();

        time.setTextFill(Color.WHITE);
        time.setTranslateX(525);
        time.setTranslateY(15);

        Font font = Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 25);
        time.setFont(font);
    }

    public Label getClockLabel() {
        return time;
    }

    public void update() {
        if (!stop)
            time.setText(timeFormat.format(System.currentTimeMillis() - startTime));
    }

    public void stop() {
        stop = true;
        time.setTextFill(Color.ORANGERED);
    }

    public void reset() {
        startTime = System.currentTimeMillis();
        stop = false;
        time.setTextFill(Color.WHITE);
    }

    public void undo(long newTime) {
        startTime = startTime + (System.currentTimeMillis() - newTime);
        stop = false;
        time.setTextFill(Color.WHITE);
    }
}