package core;
import java.util.ArrayList;

import core.objects.Bullet;
import core.objects.Player;

public class World {
	private Player player;
	private Map map;
	private ArrayList<Bullet> bullets;
	
	public World() {
		setMap(new Map("res/maps/map2.csv"));
		setPlayer(new Player(getMap().getPlayerSpawn(), 0, 0));
		setBullets(new ArrayList<>());
	}
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public Map getMap() {
		return map;
	}

	public Player getPlayer() {
		return player;
	}

	public void render() {
		getPlayer().render();
		getMap().render();
		bullets.forEach(b -> b.render());
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void update(double time) {
		getPlayer().update(time);
		getMap().update(time);
		bullets.forEach(b -> b.update(time));
		bullets.removeIf(b -> b.destroyed);
	}
}
