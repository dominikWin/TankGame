package core.ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import core.Game;
import core.ui.menus.DeathMenu;
import core.ui.menus.MainMenu;
import core.ui.menus.PauseMenu;
import core.util.Logger;

/**
 * Class for managing the User Interface for the game.
 * 
 * @author Dominik Winecki
 *
 * 
 */
public class UserInterface {
	private static final boolean ANTI_ALIAS_FONT = true;
	public static final int PRIMARY_FONT_SIZE = 32;
	public static final int SECONDARY_FONT_SIZE = 24;

	Menu deathMenu;
	Font font;
	ArrayList<TrueTypeFont> fonts;
	ArrayList<Integer> fontSizes;
	Menu mainMenu;
	Menu pauseMenu;

	/**
	 * Created a user interface with default settings.
	 */
	public UserInterface() {
		loadFonts();
		mainMenu = new MainMenu();
		pauseMenu = new PauseMenu();
		deathMenu = new DeathMenu();
	}

	/**
	 * @param size
	 * @return a TrueTypeFont of the specified size.
	 */
	private TrueTypeFont addFont(float size) {
		Logger.log("Adding font in size " + size);
		fonts.add(new TrueTypeFont(font.deriveFont(size), ANTI_ALIAS_FONT));
		fontSizes.add((int) size);
		return fonts.get(fonts.size() - 1);
	}

	/**
	 * Draws text to the screen with the given position and size.
	 * 
	 * @param x
	 * @param y
	 * @param size
	 * @param text
	 */
	public void drawText(float x, float y, int size, String text) {
		drawText(x, y, size, text, Color.white);
	}

	/**
	 * Draws text to the screen with the given position and size in the given
	 * color.
	 * 
	 * @param x
	 * @param y
	 * @param size
	 * @param text
	 * @param color
	 */
	public void drawText(float x, float y, int size, String text, Color color) {
		getFont(size).drawString(x, y, text, color);
	}

	/**
	 * Draws text to the screen and centers it on the x.
	 * 
	 * @param x
	 * @param y
	 * @param size
	 * @param text
	 */
	public void drawTextCentered(float x, float y, int size, String text) {
		drawTextCentered(x, y, size, text, Color.white);
	}

	/**
	 * Draws text to the screen in the given color and centers it on the x.
	 * 
	 * @param x
	 * @param y
	 * @param size
	 * @param text
	 * @param color
	 */
	public void drawTextCentered(float x, float y, int size, String text, Color color) {
		float _x = x - getFont(size).getWidth(text) / 2;
		float _y = y - getFont(size).getHeight(text) / 2;
		drawText(_x, _y, size, text, color);
	}

	/**
	 * @param size
	 * @return a TrueTypeFont in the size given.
	 */
	private TrueTypeFont getFont(int size) {
		for (int i = 0; i < fontSizes.size(); i++) {
			if (fontSizes.get(i) == size)
				return fonts.get(i);
		}
		return addFont(size);
	}

	/**
	 * Loads the fonts from the font files.
	 */
	private void loadFonts() {
		fonts = new ArrayList<>();
		fontSizes = new ArrayList<>();
		try {
			InputStream inputStream = new FileInputStream(new File("res/fonts/opensans/OpenSans-Regular.ttf"));
			Font tmpFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			font = tmpFont;
		} catch (FileNotFoundException e) {
			Logger.log("Can't find font file", Logger.ERROR);
			e.printStackTrace();
		} catch (FontFormatException e) {
			Logger.log("Font format error", Logger.ERROR);
			e.printStackTrace();
		} catch (IOException e) {
			Logger.log("IOException when creating font", Logger.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * Renders the user interface and all components.
	 */
	public void render() {
		GL11.glEnable(GL11.GL_BLEND);
		switch (Game.getGameState()) {
		case DEAD:
			deathMenu.render();
			break;
		case LOADING:
			break;
		case MAIN_MENU:
			mainMenu.render();
			break;
		case PAUSED:
			pauseMenu.render();
			break;
		case PLAYING:
			break;
		}
		GL11.glDisable(GL11.GL_BLEND);
	}

	/**
	 * Updates the menu and all components.
	 * 
	 * @param time
	 */
	public void update(double time) {
		switch (Game.getGameState()) {
		case DEAD:
			deathMenu.update(time);
			break;
		case LOADING:
			break;
		case MAIN_MENU:
			mainMenu.update(time);
			break;
		case PAUSED:
			pauseMenu.update(time);
			break;
		case PLAYING:
			break;
		}
	}
}
