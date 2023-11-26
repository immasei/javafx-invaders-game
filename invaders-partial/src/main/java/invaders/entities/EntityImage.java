package invaders.entities;

import invaders.entities.Factory.*;
import invaders.entities.Product.Projectile;
import invaders.entities.Strategy.*;
import invaders.entities.Builder.*;

import java.util.*;

public class EntityImage {

    private static Map<String, String> colorMap = new HashMap<String, String>();
    private static Map<String, ShootBehaviour> behaviourMap = new HashMap<String, ShootBehaviour>();

    //player ship image base on color in config file
    public EntityImage(Player p) {
        colorMap.put("blue", "playerBlue.png");
        colorMap.put("green", "playerGreen.png");
        colorMap.put("black", "playerBlack.png");
        colorMap.put("white", "playerWhite.png");
        colorMap.put("grey", "playerGrey.png");
        colorMap.put("yellow", "playerYellow.png");
    }

    //enemy image base on behaviour
    public EntityImage(UFO e) {
        colorMap.put("fast_straight", "e1.png"); //dark blue enemy

        //SWITCHING BACKGROUND COLOR -> SWITCH ENEMY TO BLACK
        colorMap.put("slow_straight", "e3.png"); //light blue enemy
                                                 //black enemy is e2.png, in case you switch to lighter background
    }

    public EntityImage(ProjectileCreator pf) {
        behaviourMap.put("fast_straight", new FastStraight());
        behaviourMap.put("slow_straight", new SlowStraight());
    }

    //enemy projectile image base on behviour
    public EntityImage(Projectile pr) {
        colorMap.put("fast_straight", "fast.png");
        colorMap.put("slow_straight", "slow.png");
        colorMap.put("player", "playerBullet.png");
    }

    public String getImage(String col) {
        return colorMap.get(col);
    }
    public ShootBehaviour getBehaviour(String behaviour) {
        return behaviourMap.get(behaviour);
    }
}
