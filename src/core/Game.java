package core;

import static org.lwjgl.opengl.GL11.*;

import java.util.Arrays;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import core.util.Logger;
import core.util.astar.AStar;
import core.util.astar.AreaMap;
import core.util.astar.ClosestHeuristic;

public class Game {

	public static int WIDTH;
	public static int HEIGHT;

	private static World world;
	private static UserInterface userInterface;

	public static void main(String[] args) {
		createDisplay();
		init();
		gameLoop();
	}

	private static void init() {
		Logger.log("Starting TankGame");
		glInit();
		world = new World();
		Logger.log(Arrays.deepToString(world.getMap().map));
		Logger.log(Arrays.deepToString(Map.inverse(world.getMap().map)));
		AreaMap map = new AreaMap((int) world.getMap().getSize().getHeight(), (int) world.getMap().getSize().getWidth(),
				world.getMap().getObsticleMap());
		AStar pathFinder = new AStar(map, new ClosestHeuristic());
		pathFinder.calcShortestPath(1, 1, world.getPlayer().getMapLocationY(), world.getPlayer().getMapLocationX());
		pathFinder.printPath();
		userInterface = new UserInterface();
	}

	private static void gameLoop() {
		long lastRunTime = 0;
		double time = 0;
		while (!Display.isCloseRequested()) {
			long startTime = System.nanoTime();

			{// Scope declared to emphasize position between start and end time
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				Input.update();
				update(time);
				glTranslated(-(world.getPlayer().location.getX() - Game.WIDTH / 2),
						-(world.getPlayer().location.getY() - Game.HEIGHT / 2), 0);
				render();
				glTranslated((world.getPlayer().location.getX() - Game.WIDTH / 2),
						(world.getPlayer().location.getY() - Game.HEIGHT / 2), 0);
				Display.update();
			}

			long endTime = System.nanoTime();
			lastRunTime = endTime - startTime;
			time = (double) lastRunTime / 1000000000d;
		}
		exit();
	}

	private static void render() {
		world.render();
		userInterface.render();
	}

	private static void update(double time) {
		world.update(time);
		userInterface.update(time);
	}

	private static void exit() {
		exit(0);
	}

	private static void exit(int status) {
		Display.destroy();
		Logger.close();
		System.exit(status);
	}

	private static void glInit() {
		glEnable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	private static void createDisplay() {
		try {
			Display.setDisplayModeAndFullscreen(new DisplayMode(1280, 720));
			Display.setFullscreen(false);
			Display.setVSyncEnabled(true);
			Display.setResizable(false);

			Game.WIDTH = Display.getDisplayMode().getWidth();
			Game.HEIGHT = Display.getDisplayMode().getHeight();
			Logger.log("Display created with display mode:" + Display.getDisplayMode());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		;
		try {
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

	}

	public static UserInterface getUserInterface() {
		return userInterface;
	}

	public static void setUserInterface(UserInterface userInterface) {
		Game.userInterface = userInterface;
	}

	public static World getWorld() {
		return world;
	}

	public static void setWorld(World world) {
		Game.world = world;
	}

}
