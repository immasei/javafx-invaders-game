package invaders.memento;

import invaders.singleton.GameEngine;
import invaders.factory.Projectile;
import invaders.gameobject.GameObject;
import invaders.rendering.Renderable;
import java.util.*;

public class Memento {

    private List<Renderable> renderables;
    private List<GameObject> gameObjects;

    private List<ArrayList<Double>> positions;
    private List<Double> lives;
    private List<List<Projectile>> enemyProjectiles;
    private List<Integer> xVels;

    private int score;
    private long time;

    public Memento(GameEngine engine) {

        this.score = engine.getFacade().getScore();
        this.time = System.currentTimeMillis();

        this.renderables = new ArrayList<>(engine.getRenderables());
        this.gameObjects = new ArrayList<>(engine.getGameObjects());
        this.positions = new ArrayList<>();
        this.lives = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        this.xVels = new ArrayList<>();

        for (Renderable ro: renderables) {
            if (!ro.getRenderableObjectName().equals("background")) {
                positions.add(new ArrayList<>(
                        Arrays.asList(ro.getPosition().getX(),
                                      ro.getPosition().getY())
                        )
                );
                lives.add(ro.getHealth());
            } else {
                positions.add(new ArrayList<>());
                lives.add((double) 0);
            }
        }
        for (GameObject go: gameObjects) {
            xVels.add(go.getxVel());
            enemyProjectiles.add(new ArrayList<>(go.getEnemyProjectile()));
        }
    }

    public List<Renderable> getRenderables() {
        for (int i = 0; i < renderables.size(); i++) {
            renderables.get(i).setLives(lives.get(i));
            renderables.get(i).getPosition().setX(positions.get(i).get(0));
            renderables.get(i).getPosition().setY(positions.get(i).get(1));
        }
        return renderables;
    }

    public List<GameObject> getGameObjects() {
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).setxVel(xVels.get(i));
            gameObjects.get(i).setEnemyProjectile(new ArrayList<>(enemyProjectiles.get(i)));
        }
        return gameObjects;
    }

    public int getScore() {
        return score;
    }

    public long getTime() {
        return time;
    }
}
