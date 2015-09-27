package core.ui.menus;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;

import core.Game;
import core.Game.GameState;
import core.Input;
import core.ui.Menu;
import core.ui.UserInterface;

public class DeathMenu implements Menu {

	@Override
	public void render() {
		Game.getUserInterface().drawTextCentered(Game.WIDTH / 2, 150, UserInterface.PRIMARY_FONT_SIZE, "You died!");
		Game.getUserInterface().drawTextCentered(Game.WIDTH / 2, 200, UserInterface.SECONDARY_FONT_SIZE,
				"Don't do that", new Color(.003f, .003f, .003f));
		Game.getUserInterface().drawTextCentered(Game.WIDTH / 2, 250, UserInterface.SECONDARY_FONT_SIZE,
				"Press ENTER to continue...");
	}

	@Override
	public void update(double time) {
		if (Input.getKeyDown(Keyboard.KEY_RETURN)) {
			Game.setGameState(GameState.MAIN_MENU);
		}
	}

}
