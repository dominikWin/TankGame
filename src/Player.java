
public class Player {
	Vector2d location;
	double gunAngle;
	double bodyAngle;
	
	Player(Vector2d location) {
		this.location = location;
	}
	
	Player(Vector2d location, double gunAngle, double bodyAngle) {
		this.location = location;
		this.bodyAngle = bodyAngle;
		this.gunAngle = gunAngle;
	}
	
	public void update(double time) {

	}

	public void render() {

	}
}
