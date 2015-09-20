
public class World {
	private Player player;
	private Map map;
	public World() {
		setMap(new Map("res/maps/map.csv"));
		setPlayer(new Player(getMap().getPlayerSpawn(), 0, 0));
	}
	
	public void update(double time) {
		getPlayer().update(time);
		getMap().update(time);
	}
	
	public void render() {
		getPlayer().render();
		getMap().render();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
}
