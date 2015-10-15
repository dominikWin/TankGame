package core.objects;

import core.Game;
import core.Map;
import core.util.Collision;
import core.util.Pathfinder;
import core.util.TankModel;
import core.util.Vector2d;
import core.util.astar.Node;
import core.util.astar.Path;

/**
 * A class for handling enemies.
 * @author Dominik Winecki

 *
 */
public class Enemy {
	/**
	 * Enum for storing all of the possible states of the enemy AI.
	 * @author Dominik Winecki
	 *
	 */
	enum EnemyState {
		MOVING, SHOOTING, SEARCHING;
	}

	private static final int SPEED = 100;

	private static final double MAP_SIZE_DESTINATION_MULTIPLYER = .01;

	private static final long FIRE_DELAY_MILS = 350;

	long lastFireTime = 0;

	Path shortestPath;

	public Vector2d location;
	Vector2d lastKnownPlayerLoc;

	EnemyState enemyState;
	private int patrolX;
	private int patrolY;
	private int startY;
	private int startX;
	public double gunAngle;

	public double bodyAngle;
	private int currentDestinationX;
	private int currentDestinationY;
	boolean going;

	public boolean destroyed = false;

	/**
	 * Creates an enemy at a location specified by a x and y, and will patrol to patorlX and patrolY.
	 * @param x
	 * @param y
	 * @param patrolX
	 * @param patrolY
	 */
	public Enemy(int x, int y, int patrolX, int patrolY) {
		going = true;
		bodyAngle = gunAngle = 0;
		setX(x);
		setY(y);
		location = getLocFromMapLoc(x, y);
		setPatrolX(patrolX);
		setPatrolY(patrolY);
		enemyState = EnemyState.SEARCHING;
		shortestPath = Pathfinder.getPathToLocation(x, y, patrolX, patrolY);
		updateNextLoc();
	}

	/**
	 * @return true if the enemy is at the destination.
	 */
	private boolean atDestinationNode() {
		return Vector2d.distance(getLocFromMapLoc(currentDestinationX, currentDestinationY), location) < Map.BLOCK_SIZE
				* MAP_SIZE_DESTINATION_MULTIPLYER;
	}

	/**
	 * Creates a new path to follow the player.
	 */
	private void createPathToPlayer() {
		shortestPath = Pathfinder.getPathToLocation(getMapLocationX(), getMapLocationY(),
				(int) lastKnownPlayerLoc.getX() / Map.BLOCK_SIZE, (int) lastKnownPlayerLoc.getY() / Map.BLOCK_SIZE);
	}

	/**
	 * Runs AI code for when the enemy sees the player.
	 */
	private void enterSightMode() {
		enemyState = EnemyState.SHOOTING;
		gunAngle = Game.getWorld().getPlayer().location.getAngleFromPoint(location);
		if (System.currentTimeMillis() > lastFireTime + FIRE_DELAY_MILS) {
			lastFireTime = System.currentTimeMillis();
			fire();
		}
	}

	/**
	 * Fires a bullet at the player.
	 */
	private void fire() {
		Game.getWorld().getBullets()
				.add(new Bullet(new Vector2d(location.getX(), location.getY()), gunAngle, Player.BULLET_SPEED)
						.removeFromEnemy(this));
	}


	/**
	 * @param x
	 * @param y
	 * @return a vector for the location on the map.
	 */
	private Vector2d getLocFromMapLoc(int x, int y) {
		return new Vector2d(x * Map.BLOCK_SIZE + Map.BLOCK_SIZE / 2, y * Map.BLOCK_SIZE + Map.BLOCK_SIZE / 2);
	}

	public int getMapLocationX() {
		return (int) (location.getX() / Map.BLOCK_SIZE);
	}

	public int getMapLocationY() {
		return (int) (location.getY() / Map.BLOCK_SIZE);
	}

	public int getPatrolX() {
		return patrolX;
	}

	public int getPatrolY() {
		return patrolY;
	}

	public int getX() {
		return startX;
	}

	public int getY() {
		return startY;
	}

	/**
	 * @return true if the tank has completed its patrol route.
	 */
	private boolean isFinalDestination() {
		return shortestPath.waypoints.isEmpty();
	}

	/**
	 * Renders the enemy.
	 */
	public void render() {
		TankModel.renderTank(location, bodyAngle, gunAngle, 1, 0, 0);
	}

	public void setPatrolX(int patrolX) {
		this.patrolX = patrolX;
	}

	public void setPatrolY(int patrolY) {
		this.patrolY = patrolY;
	}

	public void setX(int x) {
		startX = x;
	}

	public void setY(int y) {
		startY = y;
	}

	/**
	 * Updates the tank AI.
	 * @param time
	 */
	public void update(double time) {
		switch (enemyState) {
		case MOVING:
			updateMovement(time);
			break;
		case SHOOTING:
			break;
		case SEARCHING:
			updateSearch(time);
			break;
		}
		if (Collision.canEnemySeePlayer(this)) {
			enterSightMode();
			lastKnownPlayerLoc = Game.getWorld().getPlayer().location;
		} else {
			if (enemyState == EnemyState.SHOOTING) {
				enemyState = EnemyState.SEARCHING;
				createPathToPlayer();
			}
		}
		if (Collision.isEnemyIntersectingBullet(this)) {
			destroyed = true;
		}
	}

	/**
	 * Updates the final location of the path, starts path back.
	 */
	private void updateFinalLoc() {
		if (going) {
			shortestPath = Pathfinder.getPathToLocation(getMapLocationX(), getMapLocationY(), startX, startY);
			going = false;
		} else {
			shortestPath = Pathfinder.getPathToLocation(getMapLocationX(), getMapLocationY(), patrolX, patrolY);
			going = true;
		}
		updateNextLoc();
	}

	/**
	 * Updates the AI movement.
	 * @param time
	 */
	private void updateMovement(double time) {
		if (atDestinationNode()) {
			location = getLocFromMapLoc(currentDestinationX, currentDestinationY);
			if (isFinalDestination()) {
				updateFinalLoc();
			} else {
				updateNextLoc();
			}
		} else {
			gunAngle = bodyAngle = getLocFromMapLoc(currentDestinationX, currentDestinationY)
					.getAngleFromPoint(location);
			location = new Vector2d(location, bodyAngle, SPEED * time);
		}
	}

	/**
	 * Moves to the next tile.
	 */
	private void updateNextLoc() {
		Node tmp = shortestPath.waypoints.remove(0);
		currentDestinationX = tmp.getY();
		currentDestinationY = tmp.getX();
		enemyState = EnemyState.MOVING;
	}

	/**
	 * Updates search for the player.
	 * @param time
	 */
	private void updateSearch(double time) {
		if (atDestinationNode()) {
			location = getLocFromMapLoc(currentDestinationX, currentDestinationY);
			if (isFinalDestination()) {
				enemyState = EnemyState.MOVING;
				updateFinalLoc();

			} else {
				updateNextLoc();
			}
		} else {
			gunAngle = bodyAngle = getLocFromMapLoc(currentDestinationX, currentDestinationY)
					.getAngleFromPoint(location);
			location = new Vector2d(location, bodyAngle, SPEED * time);
		}
	}

}
