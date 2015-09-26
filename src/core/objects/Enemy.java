package core.objects;

import core.Game;
import core.Map;
import core.util.TankModel;
import core.util.Vector2d;
import core.util.astar.Path;

public class Enemy {
	enum EnemyState {
		WAITING_FOR_PATH, MOVING, SHOOTING;
	}

	Path shortestPath;

	Vector2d location;

	EnemyState enemyState;
	private int patrolX;
	private int patrolY;
	private int y;
	private int x;
	double gunAngle, bodyAngle;

	public Enemy(int x, int y, int patrolX, int patrolY) {
		bodyAngle = gunAngle = 0;
		setX(x);
		setY(y);
		location = getLocFromMapLoc(x, y);
		setPatrolX(patrolX);
		setPatrolY(patrolY);
		enemyState = EnemyState.WAITING_FOR_PATH;
		shortestPath = Game.getPathFinder().calcShortestPath(x, y, patrolX, patrolY);
		Game.getPathFinder().printPath();
		System.out.println();
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
		return x;
	}

	public int getY() {
		return y;
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
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void update(double time) {

	}

}
