import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.input.Keyboard;

public class Player {
	Vector2d location;
	double gunAngle;
	double bodyAngle;

	private static class Model {
		static final double GUN_LENGTH = 20;
		static final double BODY_ANGLE_1 = 45;
		static final double BODY_ANGLE_2 = 45 + 90;
		static final double BODY_ANGLE_3 = 270 - 45;
		static final double BODY_ANGLE_4 = 360 - 45;
		static final double BODY_DISTANCE_1 = 30;
		static final double BODY_DISTANCE_2 = 30;
		static final double BODY_DISTANCE_3 = 30;
		static final double BODY_DISTANCE_4 = 30;
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
//		if (Input.getKey(Keyboard.KEY_W)) {
//				bodyAngle = 270;
//				location = new Vector2d(location, bodyAngle, time * 25);
//		}
	}

	public void render() {
		glColor3d(1, 1, 1);
		glBegin(GL_LINES);
		{
			// Gun
			glVertex2d(location.getX(), location.getY());
			glVertex2d(new Vector2d(location, gunAngle, Model.GUN_LENGTH).getX(),
					new Vector2d(location, gunAngle, Model.GUN_LENGTH).getY());

			// Body
			Vector2d loc1 = new Vector2d(location, Model.BODY_ANGLE_1, Model.BODY_DISTANCE_1);
			Vector2d loc2 = new Vector2d(location, Model.BODY_ANGLE_2, Model.BODY_DISTANCE_2);
			Vector2d loc3 = new Vector2d(location, Model.BODY_ANGLE_3, Model.BODY_DISTANCE_3);
			Vector2d loc4 = new Vector2d(location, Model.BODY_ANGLE_4, Model.BODY_DISTANCE_4);

			loc1.glVertexWrite();
			loc2.glVertexWrite();

			loc2.glVertexWrite();
			loc3.glVertexWrite();

			loc3.glVertexWrite();
			loc4.glVertexWrite();

			loc4.glVertexWrite();
			loc1.glVertexWrite();
		}
		glEnd();
	}
}
