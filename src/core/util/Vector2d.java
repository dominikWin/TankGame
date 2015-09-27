package core.util;

import org.lwjgl.opengl.GL11;

/**
 * @author Dominik Stores an X and Y double
 */
public class Vector2d {
	/**
	 * @param obj1
	 * @param obj2
	 * @return new Vector2d with sum value of obj1 & obj2
	 */
	public static Vector2d add(Vector2d obj1, Vector2d obj2) {
		return new Vector2d(obj1.x + obj2.x, obj1.y + obj2.y);
	}

	/**
	 * @param obj1
	 * @param obj2
	 * @return new Vector2d directly in between two obj1 & obj2
	 */
	public static Vector2d avarage(Vector2d obj1, Vector2d obj2) {
		return new Vector2d((obj1.x + obj2.x) / 2, (obj1.x + obj2.x) / 2);
	}

	/**
	 * @param obj1
	 * @param obj2
	 * @param object1Bias
	 * @return new Vector2d in between two obj1 & obj2, with obj1s bias from 0-1
	 */
	public static Vector2d between(Vector2d obj1, Vector2d obj2, double object1Bias) {
		return new Vector2d(obj1.x * object1Bias + obj2.x * (1 - object1Bias) / 2,
				obj1.y * object1Bias + obj2.y * (1 - object1Bias) / 2);
	}

	public static Vector2d crappyRotate(Vector2d obj, Vector2d base, double angle) {
		// if (angle == 0) return obj;
		// Convert to radians
		angle = Math.toRadians(angle);
		// Define values
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		// Remove offset
		obj.x -= base.x;
		obj.y -= base.y;
		// Calculate new pos
		double x = obj.x * cos + obj.y * sin;
		double y = obj.x * sin + obj.y * cos;
		// Re-add base offset
		x += base.x;
		y += base.y;
		return new Vector2d(x, y);
	}

	/**
	 * @param obj1
	 * @param obj2
	 * @return distance between obj1 & obj2
	 */
	public static double distance(Vector2d obj1, Vector2d obj2) {
		return Math.sqrt(Math.pow(obj1.getX() - obj2.getX(), 2) + Math.pow(obj1.y - obj2.y, 2));
	}

	public static Vector2d multiply(Vector2d vector, double value) {
		return new Vector2d(vector.getX() * value, vector.getY() * value);
	}

	/**
	 * @param obj
	 * @param base
	 * @param angle
	 * @return Vector2d obj rotated around base by the angle
	 */
	public static Vector2d rotate(Vector2d obj, Vector2d base, double angle) {
		// if (angle == 0) return obj;
		// Convert to radians
		angle = Math.toRadians(angle);
		// Define values
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		// Remove offset
		obj.x -= base.x;
		obj.y -= base.y;
		// Calculate new pos
		double x = obj.x * cos + obj.y * sin;
		double y = obj.x * sin - obj.y * cos;
		// Re-add base offset
		x += base.x;
		y += base.y;
		return new Vector2d(x, y);
	}

	private double x, y;

	/**
	 * @param x
	 * @param y
	 *            Creates new Vector2d at position x, y
	 */
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @param base
	 * @param angle
	 * @param distance
	 *            Creates new Vector2d as polar point with origin at base
	 */
	public Vector2d(Vector2d base, double angle, double distance) {
		x = Vector2d.rotate(new Vector2d(base.x + distance, base.y), base, angle).x;
		y = Vector2d.rotate(new Vector2d(base.x + distance, base.y), base, angle).y;
	}

	/**
	 * @param other
	 *            Adds other to value
	 */
	public void add(Vector2d other) {
		x += other.x;
		y += other.y;
	}

	public void crappyRotate(double angle) {
		// double _x = Vector2d.rotate(this, new Vector2d(0, 0), angle).x;
		// double _y = Vector2d.rotate(this, new Vector2d(0, 0), angle).y;
		// x = _x;
		// y = _y;
		crappyRotate(new Vector2d(0, 0), angle);
	}

	/**
	 * @param base
	 * @param angle
	 *            Rotates Vector2d around base by angle degrees
	 */
	public void crappyRotate(Vector2d base, double angle) {
		double _x = Vector2d.crappyRotate(this, base, angle).x;
		double _y = Vector2d.crappyRotate(this, base, angle).y;
		x = _x;
		y = _y;
	}

	public double getAngleFromOrigin() {
		return Math.toDegrees(Math.atan2(y, x));
	}

	public double getAngleFromPoint(Vector2d point) {
		return Math.toDegrees(Math.atan2(y - point.getY(), x - point.getX()));
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void glVertexWrite() {
		GL11.glVertex2d(x, y);
	}

	public Vector2d inverse() {
		return new Vector2d(-x, -y);
	}

	/**
	 * @param amount
	 *            Multiples x & y by amount
	 */
	public void multiply(double amount) {
		x *= amount;
		y *= amount;
	}

	/**
	 * @param angle
	 *            Rotates point angle degrees around point (0, 0)
	 */
	public void rotate(double angle) {
		// double _x = Vector2d.rotate(this, new Vector2d(0, 0), angle).x;
		// double _y = Vector2d.rotate(this, new Vector2d(0, 0), angle).y;
		// x = _x;
		// y = _y;
		rotate(new Vector2d(0, 0), angle);
	}

	/**
	 * @param base
	 * @param angle
	 *            Rotates Vector2d around base by angle degrees
	 */
	public void rotate(Vector2d base, double angle) {
		double _x = Vector2d.rotate(this, base, angle).x;
		double _y = Vector2d.rotate(this, base, angle).y;
		x = _x;
		y = _y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Vector2d[" + x + ", " + y + "]";
	}
}
