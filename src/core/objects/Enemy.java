package core.objects;

import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.Arrays;

import org.lwjgl.opengl.Display;

import core.Game;
import core.Map;
import core.util.Logger;
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

	Path shortestPath;

	Vector2d location;

	EnemyState enemyState;
	private int patrolX;
	private int patrolY;
	private int startY;
	private int startX;
	double gunAngle, bodyAngle;
	private int currentDestinationX;
	private int currentDestinationY;
	boolean going;

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
			break;
		}
		if (canSeePlayer()) {
			enterSightMode();
		} else {
		}
	}

	private void enterSightMode() {
		enemyState = EnemyState.SHOOTING;
		gunAngle = Game.getWorld().getPlayer().location.getAngleFromPoint(location);
	}

	private boolean canSeePlayer() {
		Line2D line = new Line2D.Double(location.getX(), location.getY(), Game.getWorld().getPlayer().location.getX(),
				Game.getWorld().getPlayer().location.getY());
		int[][] map = Game.getWorld().getMap().getObsticleMap();
//		Logger.log(Arrays.deepToString(map));
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] != 1)
					continue;
				Rectangle rect = new Rectangle(x * Map.BLOCK_SIZE, y * Map.BLOCK_SIZE, Map.BLOCK_SIZE, Map.BLOCK_SIZE);
				if (line.intersects(rect))
					return false;
			}
		}
		return true;
	}

	private void updateFinalLoc() {
		if (going) {
			shortestPath = Pathfinder.getPathToLocation(patrolX, patrolY, startX, startY);
			going = false;
		} else {
			shortestPath = Pathfinder.getPathToLocation(startX, startY, patrolX, patrolY);
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

}
