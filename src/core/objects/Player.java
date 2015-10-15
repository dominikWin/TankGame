package core.objects;

import org.lwjgl.input.Keyboard;

import core.Game;
import core.Game.GameState;
import core.Input;
import core.Map;
import core.util.Collision;
import core.util.Logger;
import core.util.TankModel;
import core.util.Vector2d;
import core.util.astar.Path;

/**
 * Class for representing a player object.
 * 
 * @author Dominik Winecki
 *
 * 
 */
public class Player {

	public static final int BULLET_SPEED = 500;
	private static final int DRIVE_SPEED_BACKWARD = 250;
	private static final int DRIVE_SPEED_FOREWARD = 250;
	private static final int FIRE_DELAY_MILS = 250;
	private static final int TURNING_SPEED = 180;
	public double bodyAngle;
	public double gunAngle;
	long lastFireTime = 0;

	public Vector2d location;

	Path shortestPath;

	/**
	 * Creates a player at the location.
	 * 
	 * @param location
	 */
	Player(Vector2d location) {
		this.location = location;
	}

	/**
	 * Created a player at the location with the body and gun angles.
	 * 
	 * @param location
	 * @param gunAngle
	 * @param bodyAngle
	 */
	public Player(Vector2d location, double gunAngle, double bodyAngle) {
		this.location = location;
		this.bodyAngle = bodyAngle;
		this.gunAngle = gunAngle;
	}

	public int getMapLocationX() {
		return (int) (location.getX() / Map.BLOCK_SIZE);
	}

	public int getMapLocationY() {
		return (int) (location.getY() / Map.BLOCK_SIZE);
	}

	/**
	 * Kills the payer, only called when the player is hit by a bullet.
	 */
	private void kill() {
		Logger.log("Player died");
		Game.setGameState(GameState.DEAD);
	}

	/**
	 * Renders the player.
	 */
	public void render() {
		TankModel.renderTank(location, bodyAngle, gunAngle);
	}

	/**
	 * Updates the player based on the controls.
	 * 
	 * @param time
	 */
	public void update(double time) {
		double x = location.getX(), y = location.getY(), angle = bodyAngle;
		if (Input.getKey(Keyboard.KEY_W)) {
			location = new Vector2d(location, bodyAngle, time * DRIVE_SPEED_FOREWARD);
		}
		if (Input.getKey(Keyboard.KEY_A)) {
			bodyAngle -= TURNING_SPEED * time;
		}
		if (Input.getKey(Keyboard.KEY_S)) {
			location = new Vector2d(location, 180 + bodyAngle, time * DRIVE_SPEED_BACKWARD);
		}
		if (Input.getKey(Keyboard.KEY_D)) {
			bodyAngle += TURNING_SPEED * time;
		}

		if (Collision.isPlayerIntersectingMap(this)) {
			location = new Vector2d(x, y);
			bodyAngle = angle;
		}

		// gunAngle = new Vector2d(Input.getMousePosition()., y)

		Vector2d mouseLoc = Vector2d.add(Input.getMousePosition(),
				new Vector2d(location.getX() - Game.WIDTH / 2, location.getY() - Game.HEIGHT / 2));
		Vector2d relative = Vector2d.add(location.inverse(), mouseLoc);

		double mouseAngle = Math.toDegrees(Math.atan2(relative.getY(), relative.getX()));

		gunAngle = mouseAngle;

		if (Collision.isPlayerIntersectingBullet()) {
			kill();
		}

		if (Input.getKey(Keyboard.KEY_SPACE) && lastFireTime + FIRE_DELAY_MILS < System.currentTimeMillis()) {
			Game.getWorld().getBullets()
					.add(new Bullet(
							new Vector2d(location, gunAngle, TankModel.GUN_LENGTH + TankModel.GUN_OFFSET_LENGTH),
							gunAngle, BULLET_SPEED).removeFromPlayer());
			lastFireTime = System.currentTimeMillis();
		}

		while (gunAngle < 0) {
			gunAngle += 360;
		}
		while (bodyAngle < 0) {
			bodyAngle += 360;
		}

		gunAngle %= 360;
		bodyAngle %= 360;

	}
}
