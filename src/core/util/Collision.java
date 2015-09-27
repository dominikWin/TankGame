package core.util;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import core.Game;
import core.Map;
import core.objects.Bullet;
import core.objects.Enemy;
import core.objects.Player;

public class Collision {
	public static boolean canEnemySeePlayer(Enemy enemy) {
		return canEnemySeePlayer(enemy, Game.getWorld().getPlayer(), Game.getWorld().getMap().getObsticleMap());
	}

	public static boolean canEnemySeePlayer(Enemy enemy, int[][] map) {
		return canEnemySeePlayer(enemy, Game.getWorld().getPlayer(), map);
	}

	public static boolean canEnemySeePlayer(Enemy enemy, Player player) {
		return canEnemySeePlayer(enemy, player, Game.getWorld().getMap().getObsticleMap());
	}

	public static boolean canEnemySeePlayer(Enemy enemy, Player player, int[][] map) {
		Line2D line = new Line2D.Double(enemy.location.getX(), enemy.location.getY(),
				Game.getWorld().getPlayer().location.getX(), Game.getWorld().getPlayer().location.getY());
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] != 1) {
					continue;
				}
				Rectangle rect = new Rectangle(x * Map.BLOCK_SIZE, y * Map.BLOCK_SIZE, Map.BLOCK_SIZE, Map.BLOCK_SIZE);
				if (line.intersects(rect))
					return false;
			}
		}
		return true;
	}

	public static Polygon getBlockBoundingBox(int x, int y) {
		return new Polygon(
				new int[] { x * Map.BLOCK_SIZE, x * Map.BLOCK_SIZE + Map.BLOCK_SIZE,
						x * Map.BLOCK_SIZE + Map.BLOCK_SIZE, x * Map.BLOCK_SIZE },
				new int[] { y * Map.BLOCK_SIZE, y * Map.BLOCK_SIZE, y * Map.BLOCK_SIZE + Map.BLOCK_SIZE,
						y * Map.BLOCK_SIZE + Map.BLOCK_SIZE },
				4);
	}

	public static Polygon getTankBoundingBox(Enemy enemy) {
		return getTankBoundingBox(enemy.location, enemy.bodyAngle, enemy.gunAngle);
	}

	public static Polygon getTankBoundingBox(Player player) {
		return getTankBoundingBox(player.location, player.bodyAngle, player.gunAngle);
	}

	public static Polygon getTankBoundingBox(Vector2d location, double bodyAngle, double gunAngle) {
		Vector2d loc1 = new Vector2d(TankModel.BODY_TRACK1_X1, TankModel.BODY_TRACK1_Y1);
		Vector2d loc2 = new Vector2d(TankModel.BODY_TRACK1_X2, TankModel.BODY_TRACK1_Y2);
		Vector2d loc3 = new Vector2d(TankModel.BODY_TRACK2_X1, TankModel.BODY_TRACK2_Y1);
		Vector2d loc4 = new Vector2d(TankModel.BODY_TRACK2_X2, TankModel.BODY_TRACK2_Y2);
		loc1.rotate(bodyAngle);
		loc2.rotate(bodyAngle);
		loc3.rotate(bodyAngle);
		loc4.rotate(bodyAngle);
		loc1.add(location);
		loc2.add(location);
		loc3.add(location);
		loc4.add(location);
		return new Polygon(new int[] { (int) loc1.getX(), (int) loc2.getX(), (int) loc3.getX(), (int) loc4.getX() },
				new int[] { (int) loc1.getY(), (int) loc2.getY(), (int) loc3.getY(), (int) loc4.getY() }, 4);
	}

	public static boolean isBulletIntersectingMap(Bullet bullet) {
		return isBulletIntersectingMap(bullet, Game.getWorld().getMap().getObsticleMap());
	}

	public static boolean isBulletIntersectingMap(Bullet bullet, int[][] map) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] == 1) {
					Polygon p = getBlockBoundingBox(x, y);
					if (p.intersects(bullet.location.getX(), bullet.location.getY(), Bullet.RADIUS, Bullet.RADIUS))
						return true;
				}
			}
		}
		return false;
	}

	public static boolean isEnemyIntersectingBullet(Enemy enemy) {
		for (Bullet bullet : Game.getWorld().getBullets())
			if (getTankBoundingBox(enemy).contains(bullet.location.getX(), bullet.location.getY()))
				return true;
		return false;
	}

	public static boolean isEnemyIntersectingBullet(Enemy enemy, ArrayList<Bullet> bullets) {
		for (Bullet bullet : bullets)
			if (getTankBoundingBox(enemy).contains(bullet.location.getX(), bullet.location.getY()))
				return true;
		return false;
	}

	public static boolean isEnemyIntersectingBullet(Enemy enemy, Bullet bullet) {
		return getTankBoundingBox(enemy).contains(bullet.location.getX(), bullet.location.getY());
	}

	public static boolean isPlayerIntersectingBlock(int x, int y) {
		if (Game.getWorld().getMap().getObsticleMap()[y][x] == 1) {
			Polygon p = getBlockBoundingBox(x, y);
			if (polygonIntersectPolygon(p, getTankBoundingBox(Game.getWorld().getPlayer())))
				return true;
		}
		return false;
	}

	public static boolean isPlayerIntersectingBlock(int[][] map, int x, int y) {
		if (map[y][x] == 1) {
			Polygon p = getBlockBoundingBox(x, y);
			if (polygonIntersectPolygon(p, getTankBoundingBox(Game.getWorld().getPlayer())))
				return true;
		}
		return false;
	}

	public static boolean isPlayerIntersectingBlock(Player player, int[][] map, int x, int y) {
		if (map[y][x] == 1) {
			Polygon p = getBlockBoundingBox(x, y);
			if (polygonIntersectPolygon(p, getTankBoundingBox(player)))
				return true;
		}
		return false;
	}

	public static boolean isPlayerIntersectingBullet() {
		for (Bullet bullet : Game.getWorld().getBullets())
			if (isPlayerIntersectingBullet(Game.getWorld().getPlayer(), bullet))
				return true;
		return false;
	}

	public static boolean isPlayerIntersectingBullet(ArrayList<Bullet> bullets) {
		for (Bullet bullet : bullets)
			if (isPlayerIntersectingBullet(Game.getWorld().getPlayer(), bullet))
				return true;
		return false;
	}

	public static boolean isPlayerIntersectingBullet(Bullet bullet) {
		return getTankBoundingBox(Game.getWorld().getPlayer()).contains(bullet.location.getX(), bullet.location.getY());
	}

	public static boolean isPlayerIntersectingBullet(Player player, ArrayList<Bullet> bullets) {
		for (Bullet bullet : bullets)
			if (isPlayerIntersectingBullet(player, bullet))
				return true;
		return false;
	}

	public static boolean isPlayerIntersectingBullet(Player player, Bullet bullet) {
		return getTankBoundingBox(player).contains(bullet.location.getX(), bullet.location.getY());
	}

	public static boolean isPlayerIntersectingMap() {
		return isPlayerIntersectingMap(Game.getWorld().getPlayer(), Game.getWorld().getMap().getObsticleMap());
	}

	public static boolean isPlayerIntersectingMap(int[][] map) {
		return isPlayerIntersectingMap(Game.getWorld().getPlayer(), map);
	}

	public static boolean isPlayerIntersectingMap(Player player) {
		return isPlayerIntersectingMap(player, Game.getWorld().getMap().getObsticleMap());
	}

	public static boolean isPlayerIntersectingMap(Player player, int[][] map) {
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] == 1) {
					if (isPlayerIntersectingBlock(player, map, x, y))
						return true;
				}
			}
		}
		return false;
	}

	public static boolean polygonIntersectPolygon(Polygon p1, Polygon p2) {
		Point p;
		for (int i = 0; i < p2.npoints; i++) {
			p = new Point(p2.xpoints[i], p2.ypoints[i]);
			if (p1.contains(p))
				return true;
		}
		for (int i = 0; i < p1.npoints; i++) {
			p = new Point(p1.xpoints[i], p1.ypoints[i]);
			if (p2.contains(p))
				return true;
		}
		return false;
	}
}
