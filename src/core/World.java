package core;

import java.util.ArrayList;

import core.objects.Bullet;
import core.objects.Enemy;
import core.objects.Player;

public class World {
	private Player player;
	private Map map;
	private ArrayList<Bullet> bullets;
	private ArrayList<Enemy> enemies;

	public World() {
		setMap(new Map("res/maps/map2.csv"));
		setPlayer(new Player(getMap().getPlayerSpawn(), 0, 0));
		setBullets(new ArrayList<>());
		setEnemies(new ArrayList<>());
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public Map getMap() {
		return map;
	}

	public Player getPlayer() {
		return player;
	}

	public void init() {
		map.init();
	}

	public void initiateEnemys() {
		for (Enemy e : map.getEnemes()) {
			enemies.add(e);
		}
	}

	public void render() {
		enemies.forEach(e -> e.render());
		getPlayer().render();
		getMap().render();
		bullets.forEach(b -> b.render());
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}

	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void update(double time) {
		getPlayer().update(time);
		enemies.forEach(e -> e.update(time));
		getMap().update(time);
		bullets.forEach(b -> b.update(time));
		bullets.removeIf(b -> b.destroyed);
		enemies.removeIf(e -> e.destroyed);
	}
}
