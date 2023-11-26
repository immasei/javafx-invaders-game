package invaders.engine;

import java.io.FileReader;
import java.util.*;
import invaders.entities.Builder.*;
import invaders.entities.Factory.UFOsCreator;
import invaders.entities.Product.*;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

/**
 * This class manages the main loop and logic of the game
 */
public class GameEngine {

	//GAME WINDOW
	private static double width;
	private static double height;
	//MAIN ROLE
	private List<Renderable> renderables;
	private List<NPC> allNPCs;
	//SUB ROLE
	private NPC player;
	private List<NPC> enemies;
	private List<NPC> attacker;
	private List<NPC> attackerProjectile;
	//ENEMY ATTACK INTERVAL
	private int round = 0;
	//PLAYER STATE
	private boolean left;
	private boolean right;
	private boolean shoot;
	//ENEMY STATE
	private boolean attack;
	//DEFAULT SPEED: SLOW STRAIGHT = PLAYER SPEED
	private static double speed;

	/**
	 * Constructor of Game Engine
	 */
	public GameEngine(String config) {

		Builder builder = new NPCBuilder();
		Director director = new Director(builder);

		renderables = new ArrayList<Renderable>();
		enemies = new ArrayList<NPC>();
		allNPCs = new ArrayList<NPC>();
		attackerProjectile = new ArrayList<NPC>();

		// READ CONFIG
		try {
			// LOAD CONFIG
			JSONObject conf = (JSONObject) new JSONParser().parse(new FileReader(config));

			// GAME WINDOW
			JSONObject game = (JSONObject) conf.get("Game");
			JSONObject size = (JSONObject) game.get("size");					 // game window size
			width = Integer.parseInt(size.get("x").toString());
			height = Integer.parseInt(size.get("y").toString());

			// PLAYER
			JSONObject human = (JSONObject) conf.get("Player");
			String pColor = human.get("colour").toString();
			speed = Double.parseDouble(human.get("speed").toString());			 // player speed
			double pHealth = Integer.parseInt(human.get("lives").toString());			 // player lives

			JSONObject playerPosition = (JSONObject) human.get("position");		 // player position
			double pXPos = Double.parseDouble(playerPosition.get("x").toString());
			double pYPos = Double.parseDouble(playerPosition.get("y").toString());

			//ADD player to renderables
			Builder pb = builder.setPosition(new Vector2D(pXPos, pYPos))
					.setColor(pColor)
					.setHealth(pHealth)
					.setWidth(35).setHeight(40)    //hardcode 45 50
					.setSpeed(speed)
					.setBehaviour("slow_straight"); //hardcode
			player = director.constructPlayer();
			renderables.add(player);
			allNPCs.add(player);

			// ENEMY - UFO
			JSONArray enemySpec = (JSONArray) conf.get("Enemies");
			for (Object obj : enemySpec) {
				JSONObject enemy = (JSONObject) obj;
				JSONObject enemyPosition = (JSONObject) enemy.get("position");   // enemy position
				double eXPos = Double.parseDouble(enemyPosition.get("x").toString());
				double eYPos = Double.parseDouble(enemyPosition.get("y").toString());

				Builder eb = builder.setPosition(new Vector2D(eXPos, eYPos))
									 .setWidth(35).setHeight(40) //hardcode
						             .setHealth(1) 				 //hardcode
									 .setSpeed(1)				 //hardcode
									 .setBehaviour((String) enemy.get("projectile"));
				NPC e = director.constructUFO();
				enemies.add(e);
				renderables.add(e);
				allNPCs.add(e);
			}

			// BUNKER
			JSONArray bunkerSpec = (JSONArray) conf.get("Bunkers");
			for (Object obj: bunkerSpec) {
				JSONObject bunker = (JSONObject) obj;
				// bunker position
				JSONObject bunkerPosition = (JSONObject) bunker.get("position");
				double bXPos = Double.parseDouble(bunkerPosition.get("x").toString());
				double bYPos = Double.parseDouble(bunkerPosition.get("y").toString());
				// bunker size
				JSONObject bunkerSize = (JSONObject) bunker.get("size");
				double bwidth = Double.parseDouble(bunkerSize.get("x").toString());
				double bheight = Double.parseDouble(bunkerSize.get("y").toString());

				Builder bb = builder.setPosition(new Vector2D(bXPos, bYPos))
									 .setHealth(3)				 //hardcode
							    	 .setWidth(bwidth)
							         .setHeight(bheight);
				NPC b = director.constructBunker();
				renderables.add(b);
				allNPCs.add(b);
			}

		//ERROR
		} catch (Exception e) {
			System.out.println(e + " " + e.getMessage());
		}
	}

	/**
	 * Updates the game/simulation
	 */
	public void update(){
		// game end
		if (!renderables.contains(player)) {
			System.out.println("Game end!");
			enemies.clear();
		}
		else if (enemies.size() == 0) {
			System.out.println("Game end!");
			if(renderables.contains(player)) {
				renderables.remove(player);
			}
		}

		movePlayer();
		moveEnemy();

		if (round % 10 == 0)
			attackStart();
		round++;

		// ensure that renderable foreground objects don't go off-screen
		for(Renderable ro: renderables){
			if(!ro.getLayer().equals(Renderable.Layer.FOREGROUND)){
				continue;
			}
			if(ro.getPosition().getX() + ro.getWidth() >= width) {
				ro.getPosition().setX((width-1)-ro.getWidth());
			}

			if(ro.getPosition().getX() <= 0) {
				ro.getPosition().setX(1);
			}

			if(ro.getPosition().getY() + ro.getHeight() >= height) {
				ro.getPosition().setY((height-1)-ro.getHeight());
			}

			if(ro.getPosition().getY() <= 0) {
				ro.getPosition().setY(1);
			}
		}
	}

	/**
	 * Update Player State
	 */
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
		return true;
	}

	public void shootStart(){
		shoot = true;
		player.shoot(this);
	}

	public void shootEnd(){
		shoot = false;
	}

	/**
	 * Update Enemy State
	 */
	public void attackStart(){
		attack = true;
	}

	public void attackEnd(){
		attack = false;
	}

	/**
	 * Update Action due to State
	 */
	private void movePlayer(){
		if (left) { player.left(); }

		if (right) { player.right(); }

		if (shoot) {
			player.shoot(this);
		}
	}

	private void moveEnemy(){
		for (NPC e : enemies) {
			e.right();
		}

		if (attack){
	        if (!isContain(attackerProjectile) && enemies.size() > 0) {
				attacker = randomAttack();
				for (NPC e: attacker) {
					Projectile p = new UFOsCreator()
							.createProjectile(new Vector2D(e.getPosition().getX() + 13,
											               e.getPosition().getY() + 30),
									                           speed, e.getBehaviour());
					renderables.add(p);
					attackerProjectile.add(p);
					allNPCs.add(p);
				}
			}
		}

		for (NPC projectile: attackerProjectile) {
			projectile.down();
		}
	}

	/**
	 * Getter & Setter
	 */

	public List<NPC> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<NPC> enemies) {
		this.enemies = enemies;
	}

	public List<Renderable> getRenderables(){
		return renderables;
	}

	public void setRenderables(List<Renderable> renderables){
		this.renderables = renderables;
	}

	public void addRenderables(Projectile projectile) {
		renderables.add(projectile);
		allNPCs.add(projectile);
	}

	public List<NPC> getNPC() {
		return allNPCs;
	}

	public void setNPC(List<NPC> npc) {
		allNPCs = npc;
	}

	public List<NPC> randomAttack() {
		List<NPC> attacker = new ArrayList<NPC>();
		Random randomNumberGenerator = new Random();
		int randomNo = randomNumberGenerator.nextInt(2); //[0, 2]
		randomNo++; //[1, 3]
		if (randomNo > enemies.size())
			randomNo = enemies.size();

		if (enemies.size() - 1  <= 0)
			return attacker;

		while (attacker.size() != randomNo) {
			int randomLoc = randomNumberGenerator.nextInt(enemies.size()-1);
			if (!attacker.contains(randomLoc)) {
				attacker.add(enemies.get(randomLoc));
			}
		}
		return attacker;
	}

	public boolean isContain(List<NPC> attacker) {
		for (NPC p: attacker) {
			if (renderables.contains(p)) {
				return true;
			}
		}
		return false;
	}
}
