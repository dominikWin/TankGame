package core.ui.menus;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import core.Game;
import core.Input;
import core.ui.Menu;
import core.ui.UserInterface;

public class MainMenu implements Menu {
	static final int OPTION_COUNT = 2;
	int selectedOption;

	public MainMenu() {
		selectedOption = 0;
	}

	private void buttonPressed() {
		if (selectedOption == 0) {
			Game.start();
		}
		if (selectedOption == 1) {
			Game.exit();
		}
	}

	@Override
	public void render() {
		Game.getUserInterface().drawTextCentered(Game.WIDTH / 2, 100, UserInterface.PRIMARY_FONT_SIZE, "Tank Game",
				Color.white);
		Game.getUserInterface().drawTextCentered(Game.WIDTH / 2, 150, UserInterface.SECONDARY_FONT_SIZE, "Singleplayer",
				selectedOption == 0 ? Color.red : Color.white);
		Game.getUserInterface().drawTextCentered(Game.WIDTH / 2, 200, UserInterface.SECONDARY_FONT_SIZE, "Exit",
				selectedOption == 1 ? Color.red : Color.white);
	}

	@Override
	public void update(double time) {
		if (Input.getKeyDown(Keyboard.KEY_W) || Input.getKeyDown(Keyboard.KEY_UP)) {
			if (selectedOption > 0) {
				selectedOption--;
			}
		}
		if (Input.getKeyDown(Keyboard.KEY_S) || Input.getKeyDown(Keyboard.KEY_DOWN)) {
			if (selectedOption < 1) {
				selectedOption++;
			}
		}

		if (Input.getKeyDown(Keyboard.KEY_RETURN)) {
			buttonPressed();
		}
	}

}
