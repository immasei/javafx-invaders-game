package invaders.gameobject;

import invaders.singleton.GameEngine;
import invaders.factory.Projectile;

import java.util.ArrayList;

// contains basic methods that all GameObjects must implement
public interface GameObject {

    public void start();
    public void update(GameEngine model);

    public default void setEnemyProjectile(ArrayList<Projectile> enemyProjectile) {
        return;
    }

    public default ArrayList<Projectile> getEnemyProjectile() {
        return new ArrayList<>();
    }

    public default int getxVel() {
        return 0;
    }

    public default void setxVel(int xVel) {
        return;
    }
}
