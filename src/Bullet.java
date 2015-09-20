import static org.lwjgl.opengl.GL11.*;

public class Bullet {
	Vector2d location;
	double angle, speed;
	
	public Bullet(Vector2d location, double angle, double speed) {
		this.location = location;
		this.angle = angle;
		this.speed = speed;
	}
	
	public void update(double time) {
		location = new Vector2d(location, angle, speed * time);
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
}
