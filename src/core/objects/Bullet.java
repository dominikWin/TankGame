package core.objects;

import static org.lwjgl.opengl.GL11.*;

import core.Game;
import core.util.Vector2d;

public class Bullet {
	public static final double RADIUS = 3;
	public Vector2d location;
	Vector2d velocity;
	public boolean destroyed = false;
	int bounces = 2;

	public Bullet(Vector2d location, double angle, double speed) {
		this.location = location;
		velocity = new Vector2d(new Vector2d(0, 0), angle, speed);
	}

	public Bullet(Vector2d location, double angle, double speed, int bounces) {
		this.location = location;
		velocity = new Vector2d(new Vector2d(0, 0), angle, speed);
		this.bounces = bounces;
	}

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

	public void update(double time) {
		Vector2d _location = new Vector2d(location.getX(), location.getY());
		location.add(Vector2d.multiply(velocity, time));
		if (Game.getWorld().getMap().isBulletIntersecting(this)) {
			if (bounces <= 0) {
				destroyed = true;
				return;
			}
			bounces--;

			location = _location;
			Vector2d xIncrease = Vector2d.multiply(velocity, time);
			xIncrease.setY(0);
			location.add(xIncrease);
			if (Game.getWorld().getMap().isBulletIntersecting(this))
				velocity.setX(-velocity.getX());

			location = _location;
			Vector2d yIncrease = Vector2d.multiply(velocity, time);
			xIncrease.setX(0);
			location.add(yIncrease);
			if (Game.getWorld().getMap().isBulletIntersecting(this))
				velocity.setY(-velocity.getY());

			location = _location;
		}
	}
}
