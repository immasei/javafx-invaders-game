package invaders.engine;

import invaders.memento.Caretaker;
import invaders.singleton.GameEngine;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class KeyboardInputHandler {
    private final GameEngine model;
    private boolean left = false;
    private boolean right = false;
    private Caretaker caretaker;
    private Set<KeyCode> pressedKeys = new HashSet<>();

    private Map<String, MediaPlayer> sounds = new HashMap<>();

    KeyboardInputHandler(GameEngine model) {
        this.model = model;
        this.caretaker = new Caretaker();

        // TODO (longGoneUser): Is there a better place for this code?
        URL mediaUrl = getClass().getResource("/shoot.wav");
        String jumpURL = mediaUrl.toExternalForm();

        Media sound = new Media(jumpURL);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        sounds.put("shoot", mediaPlayer);
    }

    void handlePressed(KeyEvent keyEvent) {
        if (pressedKeys.contains(keyEvent.getCode())) {
            return;
        }
        pressedKeys.add(keyEvent.getCode());

        if (keyEvent.getCode().equals(KeyCode.SPACE)) {
            if (model.shootPressed()) {
                MediaPlayer shoot = sounds.get("shoot");
                shoot.stop();
                shoot.play();
            }
        }

        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            left = true;
        }
        if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            right = true;
        }

        if (left) {
            model.leftPressed();
        }

        if(right){
            model.rightPressed();
        }

        // DIFFICULTY LEVEL
        GameEngine engine = GameEngine.getInstance();
        if (keyEvent.getCode().equals(KeyCode.Z)) {
            System.out.println("easy");
            engine.setConfigLevel("src/main/resources/config_easy.json");
        }
        if (keyEvent.getCode().equals(KeyCode.X)) {
            System.out.println("medium");
            engine.setConfigLevel("src/main/resources/config_medium.json");
        }
        if (keyEvent.getCode().equals(KeyCode.C)) {
            System.out.println("hard");
            engine.setConfigLevel("src/main/resources/config_hard.json");
        }
        // CHEATING MECHANISM
        if (keyEvent.getCode().equals(KeyCode.A)) {
            System.out.println("cheat: slow projectile");
            engine.cheat(1);
        }
        if (keyEvent.getCode().equals(KeyCode.S)) {
            System.out.println("cheat: fast projectile");
            engine.cheat(2);
        }
        if (keyEvent.getCode().equals(KeyCode.Q)) {
            System.out.println("cheat: slow alien");
            engine.cheat(3);
        }
        if (keyEvent.getCode().equals(KeyCode.W)) {
            System.out.println("cheat: fast alien");
            engine.cheat(4);
        }
        // SAVE STATE
        if (keyEvent.getCode().equals(KeyCode.O)) {
            System.out.println("save");
            caretaker.setMemento(engine.save());
        }
        if (keyEvent.getCode().equals(KeyCode.P)) {
            System.out.println("undo");
            engine.undo(caretaker.getMemento());
            caretaker.setMemento(null);
        }
    }

    void handleReleased(KeyEvent keyEvent) {
        pressedKeys.remove(keyEvent.getCode());

        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            left = false;
            model.leftReleased();
        }
        if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            model.rightReleased();
            right = false;
        }
    }
}
