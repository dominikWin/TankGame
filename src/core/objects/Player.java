package core.objects;

import java.awt.Polygon;

import org.lwjgl.input.Keyboard;

import core.Game;
import core.Game.GameState;
import core.Input;
import core.Map;
import core.util.Logger;
import core.util.TankModel;
import core.util.Vector2d;
import core.util.astar.Path;

public class Player {

	private static final int DRIVE_SPEED_BACKWARD = 250;
	private static final int DRIVE_SPEED_FOREWARD = 250;
	private static final int TURNING_SPEED = 180;
	private static final int FIRE_DELAY_MILS = 250;
	public Vector2d location;
	double gunAngle;
	double bodyAngle;

	Path shortestPath;

	long lastFireTime = 0;

	Player(Vector2d location) {
		this.location = location;
	}

	public Player(Vector2d location, double gunAngle, double bodyAngle) {
		this.location = location;
		this.bodyAngle = bodyAngle;
		this.gunAngle = gunAngle;
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

	public int getMapLocationX() {
		return (int) (location.getX() / Map.BLOCK_SIZE);
	}

	public int getMapLocationY() {
		return (int) (location.getY() / Map.BLOCK_SIZE);
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
		Polygon polygon = getBoundingBox();
		return polygon.contains(bullet.location.getX(), bullet.location.getY());
	}

	private void kill() {
		Logger.log("Player died");
		Game.setGameState(GameState.DEAD);
	}

	public void render() {
		TankModel.renderTank(location, bodyAngle, gunAngle);
	}

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

		if (Game.getWorld().getMap().isPlayerIntersecting()) {
			location = new Vector2d(x, y);
			bodyAngle = angle;
		}

		// gunAngle = new Vector2d(Input.getMousePosition()., y)

		Vector2d mouseLoc = Vector2d.add(Input.getMousePosition(),
				new Vector2d(location.getX() - Game.WIDTH / 2, location.getY() - Game.HEIGHT / 2));
		Vector2d relative = Vector2d.add(location.inverse(), mouseLoc);

		double mouseAngle = Math.toDegrees(Math.atan2(relative.getY(), relative.getX()));

		gunAngle = mouseAngle;

		if (isIntersectingBullet()) {
			kill();
		}

		if (Input.getKey(Keyboard.KEY_SPACE) && lastFireTime + FIRE_DELAY_MILS < System.currentTimeMillis()) {
			Game.getWorld().getBullets()
					.add(new Bullet(
							new Vector2d(location, gunAngle, TankModel.GUN_LENGTH + TankModel.GUN_OFFSET_LENGTH),
							gunAngle, 500));
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
