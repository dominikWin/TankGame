package core;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTranslated;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import core.ui.UserInterface;
import core.util.Logger;

public class Game {

	public enum GameState {
		LOADING, MAIN_MENU, PLAYING, PAUSED, DEAD;
	}

	public static int WIDTH;

	public static int HEIGHT;
	private static World world;

	private static UserInterface userInterface;

	private static GameState gameState;

	private static void createDisplay() {
		try {
			Display.setDisplayModeAndFullscreen(new DisplayMode(1600, 900));
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

	public static void exit() {
		exit(0);
	}

	static void exit(int status) {
		Logger.log("Exiting");
		Logger.log("Destroying display");
		Display.destroy();
		Logger.close();
		System.exit(status);
	}

	private static void gameLoop() {
		setGameState(GameState.MAIN_MENU);
		long lastRunTime = 0;
		double time = 0;
		Logger.log("Starting Game Loop");
		while (!Display.isCloseRequested()) {
			long startTime = System.nanoTime();

			{// Scope declared to emphasize position between start and end time
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				Input.update();
				update(time);
				render();
				Display.update();
			}

			long endTime = System.nanoTime();
			lastRunTime = endTime - startTime;
			time = lastRunTime / 1000000000d;
		}
		exit();
	}

	public static GameState getGameState() {
		return gameState;
	}

	public static UserInterface getUserInterface() {
		return userInterface;
	}

	public static World getWorld() {
		return world;
	}

	private static void glInit() {
		glEnable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	private static void init() {
		Logger.log("Starting TankGame");
		Logger.log("Intitating gameState");
		setGameState(GameState.LOADING);
		Logger.log("Initializing OpenGL");
		glInit();
		Logger.log("Creating user interface");
		userInterface = new UserInterface();
		lightInit();
	}

	public static void lightInit() {
		Logger.log("Creating world");
		world = new World();
		Logger.log("Initializing world");
		world.init();
	}

	public static void main(String[] args) {
		// Windows runs Java?
		createDisplay();
		init();
		gameLoop();
	}

	private static void render() {
		renderWithoutShift();

		glTranslated(-(world.getPlayer().location.getX() - Game.WIDTH / 2),
				-(world.getPlayer().location.getY() - Game.HEIGHT / 2), 0);

		renderWithShift();

		glTranslated(world.getPlayer().location.getX() - Game.WIDTH / 2,
				world.getPlayer().location.getY() - Game.HEIGHT / 2, 0);

	}

	private static void renderWithoutShift() {
		userInterface.render();
	}

	private static void renderWithShift() {
		if (getGameState() == GameState.PLAYING) {
			world.render();
		}
	}

	public static void setGameState(GameState gameState) {
		Game.gameState = gameState;
		Logger.log("Changed gameState to " + gameState);
	}

	public static void setUserInterface(UserInterface userInterface) {
		Game.userInterface = userInterface;
	}

	public static void setWorld(World world) {
		Game.world = world;
	}

	public static void start() {
		Logger.log("Starting game");
		Game.setGameState(GameState.PLAYING);
		lightInit();
	}

	private static void update(double time) {
		if (Input.getKeyDown(Keyboard.KEY_ESCAPE)) {
			switch (getGameState()) {
			case DEAD:
				break;
			case LOADING:
				break;
			case MAIN_MENU:
				break;
			case PAUSED:
				setGameState(GameState.PLAYING);
				break;
			case PLAYING:
				setGameState(GameState.PAUSED);
				break;
			}
		}
		if (getGameState() == GameState.PLAYING) {
			world.update(time);
		}
		userInterface.update(time);
	}
}
