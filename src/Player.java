import static org.lwjgl.opengl.GL11.*;

import java.awt.Polygon;

import org.lwjgl.input.Keyboard;

public class Player {
	private static final int DRIVE_SPEED_BACKWARD = 250;
	private static final int DRIVE_SPEED_FOREWARD = 250;
	private static final int TURNING_SPEED = 180;
	Vector2d location;
	double gunAngle;
	double bodyAngle;

	private static class Model {

		static final double GUN_LENGTH = 20;
		static final double GUN_OFFSET_LENGTH = 10;
		static final double TRACK_WIDTH = 10;

		static final double BODY_MAIN_X1 = 30;
		static final double BODY_MAIN_Y1 = 25;

		static final double BODY_MAIN_X2 = -30;
		static final double BODY_MAIN_Y2 = 25;

		static final double BODY_MAIN_X3 = -30;
		static final double BODY_MAIN_Y3 = -25;

		static final double BODY_MAIN_X4 = 30;
		static final double BODY_MAIN_Y4 = -25;

		static final double BODY_TRACK1_X1 = BODY_MAIN_X1;
		static final double BODY_TRACK1_Y1 = BODY_MAIN_Y1 + TRACK_WIDTH;

		static final double BODY_TRACK1_X2 = BODY_MAIN_X2;
		static final double BODY_TRACK1_Y2 = BODY_MAIN_Y2 + TRACK_WIDTH;

		static final double BODY_TRACK2_X1 = BODY_MAIN_X3;
		static final double BODY_TRACK2_Y1 = BODY_MAIN_Y3 - TRACK_WIDTH;

		static final double BODY_TRACK2_X2 = BODY_MAIN_X4;
		static final double BODY_TRACK2_Y2 = BODY_MAIN_Y4 - TRACK_WIDTH;

		static final double GUN_MAIN_X1 = 10;
		static final double GUN_MAIN_Y1 = -5;

		static final double GUN_MAIN_X2 = 10;
		static final double GUN_MAIN_Y2 = 5;

		static final double GUN_MAIN_X3 = 0;
		static final double GUN_MAIN_Y3 = 7;

		static final double GUN_MAIN_X4 = -10;
		static final double GUN_MAIN_Y4 = 5;

		static final double GUN_MAIN_X5 = -10;
		static final double GUN_MAIN_Y5 = -5;

		static final double GUN_MAIN_X6 = 0;
		static final double GUN_MAIN_Y6 = -7;
	}

	Player(Vector2d location) {
		this.location = location;
	}

	Player(Vector2d location, double gunAngle, double bodyAngle) {
		this.location = location;
		this.bodyAngle = bodyAngle;
		this.gunAngle = gunAngle;
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

		Vector2d mouseLoc = Input.getMousePosition();
		Vector2d relative = Vector2d.add(location.inverse(), mouseLoc);

		double mouseAngle = Math.toDegrees(Math.atan2(relative.getY(), relative.getX()));

		gunAngle = mouseAngle;

		while (gunAngle < 0)
			gunAngle += 360;
		while (bodyAngle < 0)
			bodyAngle += 360;

		gunAngle %= 360;
		bodyAngle %= 360;
	}

	public void render() {
		glColor3d(1, 1, 1);
		renderBody();
		renderGun();
	}

	private void renderGun() {
		renderGunChassis();
		renderGunBarrel();
	}

	private void renderGunBarrel() {
		glBegin(GL_LINES);
		{
			// Barrel
			Vector2d locB1 = new Vector2d(location, gunAngle, Model.GUN_OFFSET_LENGTH);
			Vector2d locB2 = new Vector2d(location, gunAngle, Model.GUN_LENGTH);
			locB1.glVertexWrite();
			locB2.glVertexWrite();
		}
		glEnd();
	}

	private void renderGunChassis() {
		glBegin(GL_LINE_LOOP);
		{
			// Chassis
			Vector2d loc1 = new Vector2d(Model.GUN_MAIN_X1, Model.GUN_MAIN_Y1);
			Vector2d loc2 = new Vector2d(Model.GUN_MAIN_X2, Model.GUN_MAIN_Y2);
			Vector2d loc3 = new Vector2d(Model.GUN_MAIN_X3, Model.GUN_MAIN_Y3);
			Vector2d loc4 = new Vector2d(Model.GUN_MAIN_X4, Model.GUN_MAIN_Y4);
			Vector2d loc5 = new Vector2d(Model.GUN_MAIN_X5, Model.GUN_MAIN_Y5);
			Vector2d loc6 = new Vector2d(Model.GUN_MAIN_X6, Model.GUN_MAIN_Y6);
			loc1.rotate(gunAngle);
			loc2.rotate(gunAngle);
			loc3.rotate(gunAngle);
			loc4.rotate(gunAngle);
			loc5.rotate(gunAngle);
			loc6.rotate(gunAngle);
			loc1.add(location);
			loc2.add(location);
			loc3.add(location);
			loc4.add(location);
			loc5.add(location);
			loc6.add(location);
			loc1.glVertexWrite();
			loc2.glVertexWrite();
			loc3.glVertexWrite();
			loc4.glVertexWrite();
			loc5.glVertexWrite();
			loc6.glVertexWrite();
		}
		glEnd();
	}

	public Polygon getBoundingBox() {
		Vector2d loc1 = new Vector2d(Model.BODY_TRACK1_X1, Model.BODY_TRACK1_Y1);
		Vector2d loc2 = new Vector2d(Model.BODY_TRACK1_X2, Model.BODY_TRACK1_Y2);
		Vector2d loc3 = new Vector2d(Model.BODY_TRACK2_X1, Model.BODY_TRACK2_Y1);
		Vector2d loc4 = new Vector2d(Model.BODY_TRACK2_X2, Model.BODY_TRACK2_Y2);
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

	private void renderBody() {
		glBegin(GL_LINES);
		{
			// Body
			Vector2d loc1 = new Vector2d(Model.BODY_MAIN_X1, Model.BODY_MAIN_Y1);
			Vector2d loc2 = new Vector2d(Model.BODY_MAIN_X2, Model.BODY_MAIN_Y2);
			Vector2d loc3 = new Vector2d(Model.BODY_MAIN_X3, Model.BODY_MAIN_Y3);
			Vector2d loc4 = new Vector2d(Model.BODY_MAIN_X4, Model.BODY_MAIN_Y4);
			loc1.rotate(bodyAngle);
			loc2.rotate(bodyAngle);
			loc3.rotate(bodyAngle);
			loc4.rotate(bodyAngle);
			loc1.add(location);
			loc2.add(location);
			loc3.add(location);
			loc4.add(location);

			Vector2d locT1 = new Vector2d(Model.BODY_TRACK1_X1, Model.BODY_TRACK1_Y1);
			Vector2d locT2 = new Vector2d(Model.BODY_TRACK1_X2, Model.BODY_TRACK1_Y2);
			Vector2d locT3 = new Vector2d(Model.BODY_TRACK2_X1, Model.BODY_TRACK2_Y1);
			Vector2d locT4 = new Vector2d(Model.BODY_TRACK2_X2, Model.BODY_TRACK2_Y2);
			locT1.rotate(bodyAngle);
			locT2.rotate(bodyAngle);
			locT3.rotate(bodyAngle);
			locT4.rotate(bodyAngle);
			locT1.add(location);
			locT2.add(location);
			locT3.add(location);
			locT4.add(location);

			// Main
			loc1.glVertexWrite();
			loc2.glVertexWrite();

			loc2.glVertexWrite();
			loc3.glVertexWrite();

			loc3.glVertexWrite();
			loc4.glVertexWrite();

			loc4.glVertexWrite();
			loc1.glVertexWrite();

			// Tracks
			loc1.glVertexWrite();
			locT1.glVertexWrite();

			loc2.glVertexWrite();
			locT2.glVertexWrite();

			loc3.glVertexWrite();
			locT3.glVertexWrite();

			loc4.glVertexWrite();
			locT4.glVertexWrite();

			locT1.glVertexWrite();
			locT2.glVertexWrite();

			locT3.glVertexWrite();
			locT4.glVertexWrite();
		}
		glEnd();
	}
}
