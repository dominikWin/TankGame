package core.ui;

import core.Game;

public class UserInterface {
	public UserInterface() {

	}

	public void render() {
		switch (Game.gameState) {
		case DEAD:
			break;
		case LOADING:
			break;
		case MAIN_MENU:
			break;
		case PAUSED:
			break;
		case PLAYING:
			break;
		}
	}

	public void update(double time) {
		switch (Game.gameState) {
		case DEAD:
			break;
		case LOADING:
			break;
		case MAIN_MENU:
			break;
		case PAUSED:
			break;
		case PLAYING:
			break;
		}
	}
}
