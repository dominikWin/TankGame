
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Game {

	private static int WIDTH;
	private static int  HEIGHT;

	public static void main(String[] args) {
		createDisplay();
		init();
		gameLoop();
	}

	private static void init() {
		glInit();
	}

	private static void gameLoop() {
		while(!Display.isCloseRequested()) {
			
		}
		Display.destroy();
	}

	private static void glInit() {
		glEnable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 1, 1, 0, 1, -1);
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
			System.out.println(Display.getDisplayMode());
		} catch (LWJGLException e) {
			e.printStackTrace();
		};
		try {
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

	}

}
