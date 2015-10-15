package core.ui;

/*
 * Interface for a game menu.
 */
public interface Menu {
	/**
	 * Renders the menu.
	 */
	public void render();

	/**
	 * Updated the menu.
	 * 
	 * @param time
	 */
	public void update(double time);
}
