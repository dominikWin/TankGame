package core.ui.menus;

import org.newdawn.slick.Color;

import core.Game;
import core.ui.Menu;

public class MainMenu implements Menu {
	
	

	@Override
	public void update(double time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		Game.getUserInterface().getFont(24).drawString(0, 0, "Main Menu", Color.white);
	}

}
