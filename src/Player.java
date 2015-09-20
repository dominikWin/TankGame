import static org.lwjgl.opengl.GL11.*;

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
		static final double TRACK_WIDTH = 10;
		
		static final double BODY_MAIN_X1 = 25;
		static final double BODY_MAIN_Y1 = 25;
		static final double BODY_MAIN_X2 = -25;
		static final double BODY_MAIN_Y2 = 25;
		static final double BODY_MAIN_X3 = -25;
		static final double BODY_MAIN_Y3 = -25;
		static final double BODY_MAIN_X4 = 25;
		static final double BODY_MAIN_Y4 = -25;
		static final double BODY_TRACK1_X1 = BODY_MAIN_X1;
		static final double BODY_TRACK1_Y1 = BODY_MAIN_Y1 + TRACK_WIDTH;
		static final double BODY_TRACK1_X2 = BODY_MAIN_X2;
		static final double BODY_TRACK1_Y2 = BODY_MAIN_Y2 + TRACK_WIDTH;
		static final double BODY_TRACK2_X1 = BODY_MAIN_X3;
		static final double BODY_TRACK2_Y1 = BODY_MAIN_Y3 - TRACK_WIDTH;
		static final double BODY_TRACK2_X2 = BODY_MAIN_X4;
		static final double BODY_TRACK2_Y2 = BODY_MAIN_Y4 - TRACK_WIDTH;
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
//		gunAngle = new Vector2d(Input.getMousePosition()., y)
		
		Vector2d mouseLoc = Input.getMousePosition();
		Vector2d relative = Vector2d.add(location.inverse(), mouseLoc);
		
		double mouseAngle = Math.toDegrees(Math.atan2(relative.getY(), relative.getX()));
		
		gunAngle = mouseAngle;		
		
		while(gunAngle < 0) gunAngle += 360;
		while(bodyAngle < 0) bodyAngle += 360;
		
		gunAngle %= 360;
		bodyAngle %= 360;
	}

	public void render() {
		glColor3d(1, 1, 1);
		glBegin(GL_LINES);
		{
			// Gun
			glVertex2d(location.getX(), location.getY());
			glVertex2d(new Vector2d(location, gunAngle, Model.GUN_LENGTH).getX(),
					new Vector2d(location, gunAngle, Model.GUN_LENGTH).getY());
			
			glVertex2d(100, 100);
			glVertex2d(new Vector2d(new Vector2d(00, 00), bodyAngle, 100).getX() + 100, new Vector2d(new Vector2d(00, 00), bodyAngle, 100).getY() + 100);

			// Body
			Vector2d loc1 = new Vector2d(Model.BODY_MAIN_X1, Model.BODY_MAIN_Y1);
			loc1.rotate(bodyAngle);
			loc1.add(location);
			Vector2d loc2 = new Vector2d(Model.BODY_MAIN_X2, Model.BODY_MAIN_Y2);
			loc2.rotate(bodyAngle);
			loc2.add(location);
			Vector2d loc3 = new Vector2d(Model.BODY_MAIN_X3, Model.BODY_MAIN_Y3);
			loc3.rotate(bodyAngle);
			loc3.add(location);
			Vector2d loc4 = new Vector2d(Model.BODY_MAIN_X4, Model.BODY_MAIN_Y4);
			loc4.rotate(bodyAngle);
			loc4.add(location);
			
			Vector2d locT1 = new Vector2d(Model.BODY_TRACK1_X1, Model.BODY_TRACK1_Y1);
			locT1.rotate(bodyAngle);
			locT1.add(location);
			Vector2d locT2 = new Vector2d(Model.BODY_TRACK1_X2, Model.BODY_TRACK1_Y2);
			locT2.rotate(bodyAngle);
			locT2.add(location);
			Vector2d locT3 = new Vector2d(Model.BODY_TRACK2_X1, Model.BODY_TRACK2_Y1);
			locT3.rotate(bodyAngle);
			locT3.add(location);
			Vector2d locT4 = new Vector2d(Model.BODY_TRACK2_X2, Model.BODY_TRACK2_Y2);
			locT4.rotate(bodyAngle);
			locT4.add(location);
			
			//Main
			loc1.glVertexWrite();
			loc2.glVertexWrite();

			loc2.glVertexWrite();
			loc3.glVertexWrite();

			loc3.glVertexWrite();
			loc4.glVertexWrite();

			loc4.glVertexWrite();
			loc1.glVertexWrite();
			
			//Tracks
			loc1.glVertexWrite();
			locT1.glVertexWrite();
			
			loc2.glVertexWrite();
			locT2.glVertexWrite();
			
			loc3.glVertexWrite();
			locT3.glVertexWrite();
			
			loc4.glVertexWrite();
			locT4.glVertexWrite();
			
			locT1.glVertexWrite();
			locT2 .glVertexWrite();
			
			locT3.glVertexWrite();
			locT4.glVertexWrite();
		}
		glEnd();
	}
}
