package invaders.engine;

import java.util.*;

import invaders.entities.*;
import invaders.entities.Builder.NPC;
import invaders.physics.*;
import javafx.util.Duration;

import invaders.rendering.Renderable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;


public class GameWindow {

	private Scene scene;
    private Pane pane;
    private GameEngine model;
    private List<EntityView> entityViews;
    private Renderable background;

    private double xViewportOffset = 0.0;
    private double yViewportOffset = 0.0;
    private static final double VIEWPORT_MARGIN = 280.0;

    private final Collider upperbound;
    private final Collider lowerbound;
    private final Collider rightbound;
    private final Collider leftbound;

    private List<NPC> allNPCs;
    private List<NPC> enemies;
    private ArrayList<Renderable> markfordel;

	public GameWindow(GameEngine model, double width, double height){

        this.model = model;
        pane = new Pane();
        scene = new Scene(pane, width, height);
        this.background = new SpaceBackground(pane);

        //bound
        this.upperbound = new BoxCollider(width, 1, new Vector2D(1, 1));
        this.lowerbound = new BoxCollider(width, 1, new Vector2D(1, height-1));
        this.rightbound = new BoxCollider(1, height, new Vector2D(width-1, 1));
        this.leftbound = new BoxCollider(1, height, new Vector2D(1, 1));

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(this.model);

        scene.setOnKeyPressed(keyboardInputHandler::handlePressed);
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);

        entityViews = new ArrayList<EntityView>();
        markfordel  = new ArrayList<Renderable>();
    }

	public void run() {

         Timeline timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));

         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.play();
    }

    private void draw(){

        model.update();
        List<Renderable> renderables = model.getRenderables();
        allNPCs = model.getNPC();
        enemies = model.getEnemies();

        boolean reverse = false;
        boolean speedUp = false;
        for (Renderable entity : renderables) {
            boolean notFound = true;
            NPC npc = getChar(entity);

            for (EntityView view : entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);

                    // skip if already marked for delete
                    if (markfordel.contains(entity))
                        continue;

                    //projectile or enemy reach end
                    if (entity.isColliding(upperbound)) {
                        // only player projectile return true in shootEnd()
                        if (npc.shootEnd()) {
                            model.shootEnd();
                            markfordel.add(entity);
                        }
                        continue;
                    }
                    else if (entity.isColliding(lowerbound)) {
                        // only enemy projectile return true attackEnd()
                        // or it's an enemy reaching bottom
                        if (npc.attackEnd() || enemies.contains(entity)) {
                            model.attackEnd();
                            markfordel.add(entity);
                        }
                        continue;
                    }

                    //enemy descending
                    if (entity.isColliding(rightbound) || entity.isColliding(leftbound)) {
                        if (!reverse) {
                            reverse = npc.reverse();
                        }
                    }

                    // handle collision
                    for (NPC c : allNPCs) {
                        if (entity.isColliding(c)) {
                            if (!npc.isAlly(c) && npc != c) {

                                npc.takeDamage(c.attack());
                                c.takeDamage(npc.attack());

                                // if c is Projectile
                                if (c.isVulnerable(model)) {
                                    markfordel.add(c);
                                }
                                // if npc is Projectile
                                if (npc.isVulnerable(model)) {
                                    markfordel.add(npc);
;                                }
                                // enemy slightly incr speed
                                if (enemies.contains(npc) && !speedUp) {
                                    speedUp = true;
                                }
                            }
                            //npc dead
                            if (!npc.isAlive() && !markfordel.contains(npc)) {
                                markfordel.add(entity);
                            }
                        }
                    }
                }
            }
            // if view not found, add view
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity);
                entityViews.add(entityView);
                pane.getChildren().add(entityView.getNode());
            }
            // if entity mark for delete, entityView is also marked for delete
            for (NPC x: allNPCs) {
                if (markfordel.contains(x) || !x.isAlive()) {
                    for (EntityView v: entityViews) {
                        if (v.matchesEntity(x)) {
                            v.markForDelete();
                        }
                    }
                }
            }
        }
        //delete all view marked for delete
        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());
            }
        }
        entityViews.removeIf(EntityView::isMarkedForDelete);

        //if enemy is shot then speedup
        if (speedUp) {
            for (NPC e: enemies) {
                e.speedUp();
            }
        }

        //if enemy reach side-bound then switch direction
        if (reverse) {
            for (NPC e : enemies) {
                e.alterDirection();
            }
        }

        //remove all enemy marked for del
        for (Renderable re: markfordel) {
            renderables.remove(re);
            if (allNPCs.contains(re))
                allNPCs.remove(re);
            if (enemies.contains(re))
                enemies.remove(re);
        }

        //send updated copies to GameEngine
        markfordel.clear();
        model.setRenderables(renderables);
        model.setEnemies(enemies);
        model.setNPC(allNPCs);
    }

	public Scene getScene() {
        return scene;
    }

    public NPC getChar(Renderable r) {
        //determine Renderable as Character
        for(NPC c: allNPCs) {
            if (c == r) {
                return c;
            }
        }
        return null;
    }
}
