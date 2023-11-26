package invaders.singleton;

import java.util.ArrayList;
import java.util.List;

import invaders.ConfigReader;
import invaders.engine.GameWindow;
import invaders.memento.Memento;
import invaders.builder.BunkerBuilder;
import invaders.builder.Director;
import invaders.builder.EnemyBuilder;
import invaders.facade.LabelFacade;
import invaders.factory.Projectile;
import invaders.gameobject.Bunker;
import invaders.gameobject.Enemy;
import invaders.gameobject.GameObject;
import invaders.entities.Player;
import invaders.rendering.Renderable;
import org.json.simple.JSONObject;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine {
	private List<GameObject> gameObjects; // A list of game objects that gets updated each frame
	private List<GameObject> pendingToAddGameObject;
	private List<GameObject> pendingToRemoveGameObject;

	private List<Renderable> pendingToAddRenderable;
	private List<Renderable> pendingToRemoveRenderable;

	private List<Renderable> renderables;

	private Player player;

	private LabelFacade facade;

	private boolean left;
	private boolean right;
	private int gameWidth;
	private int gameHeight;
	private int timer = 45;

	private static GameEngine instance;
	private GameWindow gameWindow;

	// default game level is easy
	private GameEngine() {
		facade = new LabelFacade();
		setUp("src/main/resources/config_easy.json");
	}

	public void setUp(String config){
		facade.reset();

		gameObjects = new ArrayList<>(); // A list of game objects that gets updated each frame
		pendingToAddGameObject = new ArrayList<>();
		pendingToRemoveGameObject = new ArrayList<>();

		pendingToAddRenderable = new ArrayList<>();
		pendingToRemoveRenderable = new ArrayList<>();

		renderables =  new ArrayList<>();

		// Read the config here
		ConfigReader.parse(config);

		// Get game width and height
		gameWidth = ((Long)((JSONObject) ConfigReader.getGameInfo().get("size")).get("x")).intValue();
		gameHeight = ((Long)((JSONObject) ConfigReader.getGameInfo().get("size")).get("y")).intValue();

		//Get player info
		this.player = new Player(ConfigReader.getPlayerInfo());
		renderables.add(player);

		Director director = new Director();
		BunkerBuilder bunkerBuilder = new BunkerBuilder();
		//Get Bunkers info
		for(Object eachBunkerInfo:ConfigReader.getBunkersInfo()){
			Bunker bunker = director.constructBunker(bunkerBuilder, (JSONObject) eachBunkerInfo);
			gameObjects.add(bunker);
			renderables.add(bunker);
		}

		EnemyBuilder enemyBuilder = new EnemyBuilder();
		//Get Enemy info
		for(Object eachEnemyInfo:ConfigReader.getEnemiesInfo()){
			Enemy enemy = director.constructEnemy(this,enemyBuilder,(JSONObject)eachEnemyInfo);
			gameObjects.add(enemy);
			renderables.add(enemy);
		}
	}

	public static GameEngine getInstance() {
		if (instance == null)
			instance = new GameEngine();
		return instance;
	}

	public void setConfigLevel(String config) {
		setUp(config);
		gameWindow.clear();
	}

	public void cheat(int code) {
		for (Renderable ro: renderables) {
			int value = ro.getValue();
			if (value == code) {
				ro.takeDamage(Integer.MAX_VALUE);
				facade.update(code);
			}
		}
	}

	public LabelFacade getFacade() {
		return facade;
	}

	public void setGameWindow(GameWindow gameWindow) {
		this.gameWindow = gameWindow;
	}

	/**
	 * Updates the game/simulation
	 */
	public void update(){
		timer+=1;

		facade.update(0);

		movePlayer();

		for(GameObject go: gameObjects){
			go.update(this);
		}

		boolean hasEnemy = false;
		for (int i = 0; i < renderables.size(); i++) {
			Renderable renderableA = renderables.get(i);
			for (int j = i+1; j < renderables.size(); j++) {
				Renderable renderableB = renderables.get(j);

				if((renderableA.getRenderableObjectName().equals("Enemy") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))
						||(renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("Enemy"))||
						(renderableA.getRenderableObjectName().equals("EnemyProjectile") && renderableB.getRenderableObjectName().equals("EnemyProjectile"))){
				}else{
					if(renderableA.isColliding(renderableB) && (renderableA.getHealth()>0 && renderableB.getHealth()>0)) {
						renderableA.takeDamage(1);
						renderableB.takeDamage(1);
						//score
						if (renderableA.getRenderableObjectName().equals("PlayerProjectile"))
							facade.update(renderableB.getValue());
						if (renderableB.getRenderableObjectName().equals("PlayerProjectile"))
							facade.update(renderableA.getValue());
					}
					// player dead
					if (!renderableA.isAlive() && renderableA.getRenderableObjectName().equals("Player"))
						facade.stop();
					// has enemy alive
					if (renderableA.isAlive() && renderableA.getRenderableObjectName().equals("Enemy")
						|| renderableB.isAlive() && renderableB.getRenderableObjectName().equals("Enemy"))
						hasEnemy = true;
				}
			}
		}
		// no enemy left
		if (!hasEnemy)
			facade.stop();

		// ensure that renderable foreground objects don't go off-screen
		int offset = 1;
		for(Renderable ro: renderables){
			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
				continue;
			}
			if(ro.getPosition().getX() + ro.getWidth() >= gameWidth) {
				ro.getPosition().setX((gameWidth - offset) -ro.getWidth());
			}

			if(ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(offset);
			}

			if(ro.getPosition().getY() + ro.getHeight() >= gameHeight) {
				ro.getPosition().setY((gameHeight - offset) -ro.getHeight());
			}

			if(ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(offset);
			}
		}
	}

	public List<Renderable> getRenderables(){
		return renderables;
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public List<GameObject> getPendingToAddGameObject() {
		return pendingToAddGameObject;
	}

	public List<GameObject> getPendingToRemoveGameObject() {
		return pendingToRemoveGameObject;
	}

	public List<Renderable> getPendingToAddRenderable() {
		return pendingToAddRenderable;
	}

	public List<Renderable> getPendingToRemoveRenderable() {
		return pendingToRemoveRenderable;
	}

	public void leftReleased() {
		this.left = false;
	}

	public void rightReleased(){
		this.right = false;
	}

	public void leftPressed() {
		this.left = true;
	}

	public void rightPressed(){
		this.right = true;
	}

	public boolean shootPressed(){
		if(timer>45 && player.isAlive()){
			Projectile projectile = player.shoot();
			gameObjects.add(projectile);
			renderables.add(projectile);
			timer=0;
			return true;
		}
		return false;
	}

	private void movePlayer(){
		if(left){
			player.left();
		}

		if(right){
			player.right();
		}
	}

	public int getGameWidth() {
		return gameWidth;
	}

	public int getGameHeight() {
		return gameHeight;
	}

	public Player getPlayer() {
		return player;
	}

	public Memento save() {
		return new Memento(this);
	}

	public void undo(Memento memento) {
		System.out.println("Memento: " + memento);
		if (memento!=null) {
			gameWindow.clear();

			facade.undo(memento.getScore(), memento.getTime());

			pendingToAddRenderable.clear();
			pendingToRemoveRenderable.clear();
			pendingToAddGameObject.clear();
			pendingToRemoveGameObject.clear();

			renderables = memento.getRenderables();
			gameObjects = memento.getGameObjects();
		}
	}
}
