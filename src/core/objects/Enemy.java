package core.objects;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

import core.Game;
import core.Map;
import core.util.Pathfinder;
import core.util.TankModel;
import core.util.Vector2d;
import core.util.astar.Node;
import core.util.astar.Path;

public class Enemy {
	enum EnemyState {
		MOVING, SHOOTING, SEARCHING;
	}

	private static final int SPEED = 100;

	private static final double MAP_SIZE_DESTINATION_MULTIPLYER = .01;

	private static final long FIRE_DELAY_MILS = 350;

	long lastFireTime = 0;

	Path shortestPath;

	Vector2d location;
	Vector2d lastKnownPlayerLoc;

	EnemyState enemyState;
	private int patrolX;
	private int patrolY;
	private int startY;
	private int startX;
	double gunAngle, bodyAngle;
	private int currentDestinationX;
	private int currentDestinationY;
	boolean going;

	public boolean destroyed = false;

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

	private boolean atDestinationNode() {
		return Vector2d.distance(getLocFromMapLoc(currentDestinationX, currentDestinationY), location) < Map.BLOCK_SIZE
				* MAP_SIZE_DESTINATION_MULTIPLYER;
	}

	private boolean canSeePlayer() {
		Line2D line = new Line2D.Double(location.getX(), location.getY(), Game.getWorld().getPlayer().location.getX(),
				Game.getWorld().getPlayer().location.getY());
		int[][] map = Game.getWorld().getMap().getObsticleMap();
		// Logger.log(Arrays.deepToString(map));
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] != 1) {
					continue;
				}
				Rectangle rect = new Rectangle(x * Map.BLOCK_SIZE, y * Map.BLOCK_SIZE, Map.BLOCK_SIZE, Map.BLOCK_SIZE);
				if (line.intersects(rect))
					return false;
			}
		}
		return true;
	}

	private void createPathToPlayer() {
		shortestPath = Pathfinder.getPathToLocation(getMapLocationX(), getMapLocationY(),
				(int) lastKnownPlayerLoc.getX() / Map.BLOCK_SIZE, (int) lastKnownPlayerLoc.getY() / Map.BLOCK_SIZE);
	}

	private void enterSightMode() {
		enemyState = EnemyState.SHOOTING;
		gunAngle = Game.getWorld().getPlayer().location.getAngleFromPoint(location);
		if (System.currentTimeMillis() > lastFireTime + FIRE_DELAY_MILS) {
			lastFireTime = System.currentTimeMillis();
			fire();
		}
	}

	private void fire() {
		Game.getWorld().getBullets()
				.add(new Bullet(new Vector2d(location.getX(), location.getY()), gunAngle, Player.BULLET_SPEED)
						.removeFromEnemy(this));
	}

	public Polygon getBoundingBox() {
		Vector2d loc1 = new Vector2d(TankModel.BODY_TRACK1_X1, TankModel.BODY_TRACK1_Y1);
		Vector2d loc2 = new Vector2d(TankModel.BODY_TRACK1_X2, TankModel.BODY_TRACK1_Y2);
		Vector2d loc3 = new Vector2d(TankModel.BODY_TRACK2_X1, TankModel.BODY_TRACK2_Y1);
		Vector2d loc4 = new Vector2d(TankModel.BODY_TRACK2_X2, TankModel.BODY_TRACK2_Y2);
		loc1.rotate(bodyAngle);
		loc2.rotate(bodyAngle);
		loc3.rotate(bodyAngle);
		loc4.rotate(bodyAngle);
		loc1.add(location);
		loc2.add(location);
		loc3.add(location);
		loc4.add(location);
		return new Polygon(new int[] { (int) loc1.getX(), (int) loc2.getX(), (int) loc3.getX(), (int) loc4.getX() },
				new int[] { (int) loc1.getY(), (int) loc2.getY(), (int) loc3.getY(), (int) loc4.getY() }, 4);
	}

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

	private boolean isFinalDestination() {
		return shortestPath.waypoints.isEmpty();
	}

	public boolean isIntersectingBullet() {
		Polygon polygon = getBoundingBox();
		for (Bullet b : Game.getWorld().getBullets()) {
			if (polygon.contains(b.location.getX(), b.location.getY()))
				return true;
		}
		return false;
	}

	public boolean isIntersectingBullet(Bullet bullet) {
		return getBoundingBox().contains(bullet.location.getX(), bullet.location.getY());
	}

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
		if (canSeePlayer()) {
			enterSightMode();
			lastKnownPlayerLoc = Game.getWorld().getPlayer().location;
		} else {
			if (enemyState == EnemyState.SHOOTING) {
				enemyState = EnemyState.SEARCHING;
				createPathToPlayer();
			}
		}
		if (isIntersectingBullet()) {
			destroyed = true;
		}
	}

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

	private void updateNextLoc() {
		Node tmp = shortestPath.waypoints.remove(0);
		currentDestinationX = tmp.getY();
		currentDestinationY = tmp.getX();
		enemyState = EnemyState.MOVING;
	}

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
