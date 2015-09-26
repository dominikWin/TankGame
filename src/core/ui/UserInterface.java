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
import org.newdawn.slick.TrueTypeFont;

import core.Game;
import core.ui.menus.MainMenu;
import core.util.Logger;

public class UserInterface {
	private static final boolean ANTI_ALIAS_FONT = false;
	Menu mainMenu;
	Font font;
	ArrayList<TrueTypeFont> fonts;
	ArrayList<Integer> fontSizes;

	public UserInterface() {
		loadFonts();
		mainMenu = new MainMenu();
	}

	public TrueTypeFont getFont(int size) {
		for (int i = 0; i < fontSizes.size(); i++) {
			if (fontSizes.get(i) == size)
				return fonts.get(i);
		}
		return addFont(size);
	}

	TrueTypeFont addFont(float size) {
		Logger.log("Adding font in size " + size);
		fonts.add(new TrueTypeFont(font.deriveFont(size), ANTI_ALIAS_FONT));
		fontSizes.add((int) size);
		return fonts.get(fonts.size() - 1);
	}

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

	public void render() {
		GL11.glEnable(GL11.GL_BLEND);
		switch (Game.getGameState()) {
		case DEAD:
			break;
		case LOADING:
			break;
		case MAIN_MENU:
			mainMenu.render();
			break;
		case PAUSED:
			break;
		case PLAYING:
			break;
		}
		GL11.glDisable(GL11.GL_BLEND);
	}

	public void update(double time) {
		switch (Game.getGameState()) {
		case DEAD:
			break;
		case LOADING:
			break;
		case MAIN_MENU:
			mainMenu.update(time);
			break;
		case PAUSED:
			break;
		case PLAYING:
			break;
		}
	}
}
