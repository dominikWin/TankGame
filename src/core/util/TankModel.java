package core.util;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;

public class TankModel {

	public static final double GUN_LENGTH = 20;
	public static final double GUN_OFFSET_LENGTH = 10;
	public static final double TRACK_WIDTH = 10;

	public static final double BODY_MAIN_X1 = 30;
	public static final double BODY_MAIN_Y1 = 25;

	public static final double BODY_MAIN_X2 = -30;
	public static final double BODY_MAIN_Y2 = 25;

	public static final double BODY_MAIN_X3 = -30;
	public static final double BODY_MAIN_Y3 = -25;

	public static final double BODY_MAIN_X4 = 30;
	public static final double BODY_MAIN_Y4 = -25;

	public static final double BODY_TRACK1_X1 = BODY_MAIN_X1;
	public static final double BODY_TRACK1_Y1 = BODY_MAIN_Y1 + TRACK_WIDTH;

	public static final double BODY_TRACK1_X2 = BODY_MAIN_X2;
	public static final double BODY_TRACK1_Y2 = BODY_MAIN_Y2 + TRACK_WIDTH;

	public static final double BODY_TRACK2_X1 = BODY_MAIN_X3;
	public static final double BODY_TRACK2_Y1 = BODY_MAIN_Y3 - TRACK_WIDTH;

	public static final double BODY_TRACK2_X2 = BODY_MAIN_X4;
	public static final double BODY_TRACK2_Y2 = BODY_MAIN_Y4 - TRACK_WIDTH;

	public static final double GUN_MAIN_X1 = 10;
	public static final double GUN_MAIN_Y1 = -5;

	public static final double GUN_MAIN_X2 = 10;
	public static final double GUN_MAIN_Y2 = 5;

	public static final double GUN_MAIN_X3 = 0;
	public static final double GUN_MAIN_Y3 = 7;

	public static final double GUN_MAIN_X4 = -10;
	public static final double GUN_MAIN_Y4 = 5;

	public static final double GUN_MAIN_X5 = -10;
	public static final double GUN_MAIN_Y5 = -5;

	public static final double GUN_MAIN_X6 = 0;
	public static final double GUN_MAIN_Y6 = -7;

	private static void renderBody(Vector2d location, double bodyAngle, double gunAngle) {
		glBegin(GL_LINES);
		{
			// Body
			Vector2d loc1 = new Vector2d(TankModel.BODY_MAIN_X1, TankModel.BODY_MAIN_Y1);
			Vector2d loc2 = new Vector2d(TankModel.BODY_MAIN_X2, TankModel.BODY_MAIN_Y2);
			Vector2d loc3 = new Vector2d(TankModel.BODY_MAIN_X3, TankModel.BODY_MAIN_Y3);
			Vector2d loc4 = new Vector2d(TankModel.BODY_MAIN_X4, TankModel.BODY_MAIN_Y4);
			loc1.rotate(bodyAngle);
			loc2.rotate(bodyAngle);
			loc3.rotate(bodyAngle);
			loc4.rotate(bodyAngle);
			loc1.add(location);
			loc2.add(location);
			loc3.add(location);
			loc4.add(location);

			Vector2d locT1 = new Vector2d(TankModel.BODY_TRACK1_X1, TankModel.BODY_TRACK1_Y1);
			Vector2d locT2 = new Vector2d(TankModel.BODY_TRACK1_X2, TankModel.BODY_TRACK1_Y2);
			Vector2d locT3 = new Vector2d(TankModel.BODY_TRACK2_X1, TankModel.BODY_TRACK2_Y1);
			Vector2d locT4 = new Vector2d(TankModel.BODY_TRACK2_X2, TankModel.BODY_TRACK2_Y2);
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

	private static void renderGun(Vector2d location, double bodyAngle, double gunAngle) {
		renderGunChassis(location, bodyAngle, gunAngle);
		renderGunBarrel(location, bodyAngle, gunAngle);
	}

	private static void renderGunBarrel(Vector2d location, double bodyAngle, double gunAngle) {
		glBegin(GL_LINES);
		{
			// Barrel
			Vector2d locB1 = new Vector2d(location, gunAngle, TankModel.GUN_OFFSET_LENGTH);
			Vector2d locB2 = new Vector2d(location, gunAngle, TankModel.GUN_LENGTH);
			locB1.glVertexWrite();
			locB2.glVertexWrite();
		}
		glEnd();
	}

	private static void renderGunChassis(Vector2d location, double bodyAngle, double gunAngle) {
		glBegin(GL_LINE_LOOP);
		{
			// Chassis
			Vector2d loc1 = new Vector2d(TankModel.GUN_MAIN_X1, TankModel.GUN_MAIN_Y1);
			Vector2d loc2 = new Vector2d(TankModel.GUN_MAIN_X2, TankModel.GUN_MAIN_Y2);
			Vector2d loc3 = new Vector2d(TankModel.GUN_MAIN_X3, TankModel.GUN_MAIN_Y3);
			Vector2d loc4 = new Vector2d(TankModel.GUN_MAIN_X4, TankModel.GUN_MAIN_Y4);
			Vector2d loc5 = new Vector2d(TankModel.GUN_MAIN_X5, TankModel.GUN_MAIN_Y5);
			Vector2d loc6 = new Vector2d(TankModel.GUN_MAIN_X6, TankModel.GUN_MAIN_Y6);
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

	public static void renderTank(Vector2d location, double bodyAngle, double gunAngle) {
		glColor3d(1, 1, 1);
		renderBody(location, bodyAngle, gunAngle);
		renderGun(location, bodyAngle, gunAngle);
	}

	public static void renderTank(Vector2d location, double bodyAngle, double gunAngle, double r, double g, double b) {
		glColor3d(r, g, b);
		renderBody(location, bodyAngle, gunAngle);
		renderGun(location, bodyAngle, gunAngle);
		glColor3d(1, 1, 1);
	}
}