
public class World {
	private Player player;
	public World() {
		Map map = new Map("res/maps/map.csv");
		System.out.println(map);
		player = new Player(new Vector2d(200, 200), 0, 0);
	}
	
	public void update(double time) {
		player.update(time);
	}
	
	public void render() {
		player.render();
	}
}
