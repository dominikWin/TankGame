package core;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Arrays;

import core.objects.Bullet;
import core.util.CSVParser;
import core.util.Logger;
import core.util.Vector2d;

import static org.lwjgl.opengl.GL11.*;

public class Map {

	public static int BLOCK_SIZE = 100;
	int[][] map;

	public Map(String fileName) {
		map = extractMap(CSVParser.parseCSVFile("res/maps/map2.csv"));
	}

	public int getLines() {
		return map.length;
	}
	
	public Dimension getSize() {
		return new Dimension(map[0].length, map.length);
	}
	
	public static int[][] inverse(int[][] array) {
		int[][] out = new int[array[0].length][array.length];
		for(int line = 0; line < array.length; line++)
			for(int data = 0; data < array[0].length; data++)
				out[data][line] = array[line][data];
		return out;
	}

	public void update(double time) {

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

	public Vector2d getPlayerSpawn() {
		for (int y = 0; y < map.length; y++)
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] == 2)
					return new Vector2d(x * BLOCK_SIZE + BLOCK_SIZE / 2, y * BLOCK_SIZE + BLOCK_SIZE / 2);
			}
		System.err.println("Spawn not found!");
		return new Vector2d(0, 0);
	}

	public boolean isPlayerIntersecting() {
		for (int y = 0; y < map.length; y++)
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] == 1) {
					Polygon p = new Polygon(
							new int[] { x * BLOCK_SIZE, x * BLOCK_SIZE + BLOCK_SIZE, x * BLOCK_SIZE + BLOCK_SIZE,
									x * BLOCK_SIZE },
							new int[] { y * BLOCK_SIZE, y * BLOCK_SIZE, y * BLOCK_SIZE + BLOCK_SIZE,
									y * BLOCK_SIZE + BLOCK_SIZE },
							4);
					if (polygonIntersectPolygon(p, Game.getWorld().getPlayer().getBoundingBox()))
						return true;
				}
			}
		return false;
	}

	public boolean isBulletIntersecting(Bullet b) {
		for (int y = 0; y < map.length; y++)
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] == 1) {
					Polygon p = new Polygon(
							new int[] { x * BLOCK_SIZE, x * BLOCK_SIZE + BLOCK_SIZE, x * BLOCK_SIZE + BLOCK_SIZE,
									x * BLOCK_SIZE },
							new int[] { y * BLOCK_SIZE, y * BLOCK_SIZE, y * BLOCK_SIZE + BLOCK_SIZE,
									y * BLOCK_SIZE + BLOCK_SIZE },
							4);
					if (p.intersects(b.location.getX(), b.location.getY(), Bullet.RADIUS, Bullet.RADIUS))
						return true;
				}
			}
		return false;
	}

	public static int[][] extractMap(String[][] array) {
		int[][] out = new int[array.length][array[0].length];
		for (int line = 0; line < out.length; line++) {
			for (int data = 0; data < out[0].length; data++) {
				int tmp = 0;
				try {
					tmp = Integer.parseInt(array[line][data]);
				} catch (NumberFormatException e) {
					Logger.log("Can't get integer out of text " + array[line][data], Logger.ERROR);
				}
				out[line][data] = tmp;
			}
		}
		return out;
	}

	public void render() {
		glBegin(GL_QUADS);
		{
			for (int y = 0; y < map.length; y++)
				for (int x = 0; x < map[0].length; x++) {
					if (map[y][x] == 1) {
						Polygon p = new Polygon(
								new int[] { x * BLOCK_SIZE, x * BLOCK_SIZE + BLOCK_SIZE, x * BLOCK_SIZE + BLOCK_SIZE,
										x * BLOCK_SIZE },
								new int[] { y * BLOCK_SIZE, y * BLOCK_SIZE, y * BLOCK_SIZE + BLOCK_SIZE,
										y * BLOCK_SIZE + BLOCK_SIZE },
								4);
						if (polygonIntersectPolygon(p, Game.getWorld().getPlayer().getBoundingBox()))
							glColor3d(1, 0, 0);
						glVertex2d(x * BLOCK_SIZE, y * BLOCK_SIZE);
						glVertex2d(x * BLOCK_SIZE + BLOCK_SIZE, y * BLOCK_SIZE);
						glVertex2d(x * BLOCK_SIZE + BLOCK_SIZE, y * BLOCK_SIZE + BLOCK_SIZE);
						glVertex2d(x * BLOCK_SIZE, y * BLOCK_SIZE + BLOCK_SIZE);
						glColor3d(1, 1, 1);
					}
				}
		}
		glEnd();
	}

	public String toString() {
		return Arrays.deepToString(map);
	}

	public int[][] getObsticleMap() {
		int[][] tmp = new int[map.length][map[0].length];
		for (int y = 0; y < map.length; y++)
			for (int x = 0; x < map[0].length; x++)
				tmp[y][x] = map[y][x] != 1 ? 0 : 1;
		return tmp;
	}
}
