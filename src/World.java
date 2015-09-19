
public class World {
	private Player player;
	public World() {
		player = new Player(new Vector2d(200, 200), 0, 0);
	}
	
	public void update(double time) {
		player.update(time);
	}
	
	public void render() {
		player.render();
	}
}
