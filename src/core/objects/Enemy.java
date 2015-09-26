package core.objects;

import core.Game;
import core.Map;
import core.util.TankModel;
import core.util.Vector2d;
import core.util.astar.AStar;
import core.util.astar.ClosestHeuristic;
import core.util.astar.Node;
import core.util.astar.Path;

public class Enemy {
	private static final double MAP_SIZE_DESTINATION_MULTIPLYER = .01;

	enum EnemyState {
		WAITING_FOR_PATH, MOVING, SHOOTING;
	}

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

	public Enemy(int x, int y, int patrolX, int patrolY) {
		bodyAngle = gunAngle = 0;
		setX(x);
		setY(y);
		location = getLocFromMapLoc(x, y);
		setPatrolX(patrolX);
		setPatrolY(patrolY);
		enemyState = EnemyState.WAITING_FOR_PATH;
		shortestPath = Game.getPathFinder().calcShortestPath(y, x, patrolY, patrolX);
		Game.getPathFinder().printPath();
		// Logger.log(shortestPath.waypoints.toString());
		updateNextLoc();
	}

	private void updateNextLoc() {
		Node tmp = shortestPath.waypoints.remove(0);
		currentDestinationX = tmp.getY();
		currentDestinationY = tmp.getX();
		enemyState = EnemyState.MOVING;
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
		this.startX = x;
	}

	public void setY(int y) {
		this.startY = y;
	}

	public void update(double time) {
		switch (enemyState) {
		case MOVING:
			updateMovement(time);
			break;
		case SHOOTING:
			break;
		case WAITING_FOR_PATH:
			break;
		}
	}

	private void updateMovement(double time) {
		if(atDestinationNode()) {
			if(isFinalDestination())
				updateFinalLoc();
			else
			updateNextLoc();
		}
		else {
			gunAngle = bodyAngle = getLocFromMapLoc(currentDestinationX, currentDestinationY).getAngleFromPoint(location);
			location = new Vector2d(location, bodyAngle, 100*time);			
		}
	}

	private void updateFinalLoc() {
		Game.setPathFinder(new AStar(Game.getMap(), new ClosestHeuristic()));
		shortestPath = Game.getPathFinder().calcShortestPath(patrolY, patrolX, startY, startX);
		updateNextLoc();
	}

	private boolean isFinalDestination() {
		return shortestPath.waypoints.isEmpty();
	}

	private boolean atDestinationNode() {
		return Vector2d.distance(getLocFromMapLoc(currentDestinationX, currentDestinationY), location) < Map.BLOCK_SIZE * MAP_SIZE_DESTINATION_MULTIPLYER;
	}

}
