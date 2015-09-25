package core.objects;

import core.Game;
import core.Map;
import core.util.Vector2d;
import core.util.astar.Path;

public class Enemy {
	Path shortestPath;
	Vector2d location;

	enum EnemyState {
		WAITING_FOR_PATH, MOVING, SHOOTING;
	}

	EnemyState enemyState;
	private int patrolX;
	private int patrolY;
	private int y;
	private int x;
	double gunAngle, bodyAngle;

	public Enemy(int x, int y, int patrolX, int patrolY) {
		bodyAngle = gunAngle = 0;
		this.setX(x);
		this.setY(y);
		location = getLocFromMapLoc(x, y);
		this.setPatrolX(patrolX);
		this.setPatrolY(patrolY);
		enemyState = EnemyState.WAITING_FOR_PATH;
		shortestPath = Game.getPathFinder().calcShortestPath(getMapLocationX(), getMapLocationY(), patrolX, patrolY);
		Game.getPathFinder().printPath();
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

	public void update(double time) {
		
	}

	public void render() {

	}

	public int getPatrolX() {
		return patrolX;
	}

	public void setPatrolX(int patrolX) {
		this.patrolX = patrolX;
	}

	public int getPatrolY() {
		return patrolY;
	}

	public void setPatrolY(int patrolY) {
		this.patrolY = patrolY;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

}
