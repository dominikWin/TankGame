package core.objects;

import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPointSize;

import core.util.Collision;
import core.util.Vector2d;

/**
 * Class for creating and rendering bullets.
 * @author Dominik Winecki
 *
 */
public class Bullet {
	public static final double RADIUS = 3;
	int bounces = 1;
	public boolean destroyed = false;
	public Vector2d location;
	Vector2d velocity;

	public Bullet(Vector2d location, double angle, double speed) {
		this.location = location;
		velocity = new Vector2d(new Vector2d(0, 0), angle, speed);
	}

	public Bullet(Vector2d location, double angle, double speed, int bounces) {
		this.location = location;
		velocity = new Vector2d(new Vector2d(0, 0), angle, speed);
		this.bounces = bounces;
	}

	/**
	 * Keeps moving the bullet until it no longer hits the enemy.
	 * @param e the Enemy.
	 * @return this.
	 */
	public Bullet removeFromEnemy(Enemy e) {
		while (Collision.isEnemyIntersectingBullet(e, this)) {
			location.add(Vector2d.multiply(velocity, .01));
		}
		return this;
	}

	/**
	 * Keeps moving the bullet until it no longer hits the player.
	 * @return this.
	 */
	public Bullet removeFromPlayer() {
		while (Collision.isPlayerIntersectingBullet(this)) {
			location.add(Vector2d.multiply(velocity, .01));
		}
		return this;
	}

	/**
	 * Renders the bullet.
	 */
	public void render() {
		glPointSize(3);
		glBegin(GL_POINTS);
		{
			location.glVertexWrite();
		}
		glEnd();
	}

	@Override
	public String toString() {
		return "Bullet[" + location + "]";
	}

	/**
	 * Updates the bullet.
	 * @param time
	 */
	public void update(double time) {
		Vector2d _location = new Vector2d(location.getX(), location.getY());
		location.add(Vector2d.multiply(velocity, time));
		if (Collision.isBulletIntersectingMap(this)) {
			if (bounces <= 0) {
				destroyed = true;
				return;
			}
			bounces--;

			location = _location;
			Vector2d xIncrease = Vector2d.multiply(velocity, time);
			xIncrease.setY(0);
			location.add(xIncrease);
			if (Collision.isBulletIntersectingMap(this)) {
				velocity.setX(-velocity.getX());
			}

			location = _location;
			Vector2d yIncrease = Vector2d.multiply(velocity, time);
			xIncrease.setX(0);
			location.add(yIncrease);
			if (Collision.isBulletIntersectingMap(this)) {
				velocity.setY(-velocity.getY());
			}

			location = _location;
		}
	}
}
