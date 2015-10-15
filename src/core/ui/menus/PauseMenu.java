package core.ui.menus;

import core.Game;
import core.ui.Menu;
import core.ui.UserInterface;

/**
 * Menu to show when the player pauses the game.
 * 
 * @author Dominik Winecki
 *
 * 
 */
public class PauseMenu implements Menu {

	@Override
	public void render() {
		Game.getUserInterface().drawText(100, 100, UserInterface.PRIMARY_FONT_SIZE, "Paused");
	}

	@Override
	public void update(double time) {
		// TODO Auto-generated method stub

	}

}
