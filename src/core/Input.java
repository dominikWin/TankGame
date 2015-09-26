package core;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import core.util.Vector2d;

/**
 * @author Dominik Handles input
 */
public class Input {
	private static ArrayList<Integer> currentKeys = new ArrayList<Integer>();
	private static ArrayList<Integer> currentMouse = new ArrayList<Integer>();
	private static ArrayList<Integer> downKeys = new ArrayList<Integer>();
	private static ArrayList<Integer> downMouse = new ArrayList<Integer>();
	public static final int NUM_KEYCODES = 256;
	public static final int NUM_MOUSEBUTTONS = 5;
	private static ArrayList<Integer> upKeys = new ArrayList<Integer>();
	private static ArrayList<Integer> upMouse = new ArrayList<Integer>();

	/**
	 * @param keyCode
	 * @return state of key matching keyCode
	 */
	public static boolean getKey(int keyCode) {
		return Keyboard.isKeyDown(keyCode);
	}

	/**
	 * @param keyCode
	 * @return true if key pressed last update
	 */
	public static boolean getKeyDown(int keyCode) {
		return Input.downKeys.contains(keyCode);
	}

	/**
	 * @param keyCode
	 * @return true if key released last update
	 */
	public static boolean getKeyUp(int keyCode) {
		return Input.upKeys.contains(keyCode);
	}

	/**
	 * @param mouseButton
	 * @return state of mouse button matching key
	 */
	public static boolean getMouse(int mouseButton) {
		return Mouse.isButtonDown(mouseButton);
	}

	/**
	 * @param mouseButton
	 * @return true if key pressed last update
	 */
	public static boolean getMouseDown(int mouseButton) {
		return Input.downMouse.contains(mouseButton);
	}

	/**
	 * @return change in mouse X position
	 */
	public static int getMouseDX() {
		return Mouse.getDX();
	}

	/**
	 * @return change in mouse Y position
	 */
	public static int getMouseDY() {
		return Mouse.getDY();
	}

	/**
	 * @return Vector2d at mouse position
	 */
	public static Vector2d getMousePosition() {
		return new Vector2d(Mouse.getX(), Game.HEIGHT - Mouse.getY());
	}

	/**
	 * @param mouseButton
	 * @return true if key pressed last update
	 */
	public static boolean getMouseUp(int mouseButton) {
		return Input.upMouse.contains(mouseButton);
	}

	/**
	 * @return X at mouse position
	 */
	public static int getMouseX() {
		return Mouse.getX();
	}

	/**
	 * @return Y at mouse position
	 */
	public static int getMouseY() {
		return Mouse.getY();
	}

	/**
	 * @param show
	 *            Sets mouse visibility
	 */
	public static void showMouse(boolean show) {
		Mouse.setGrabbed(true);
	}

	/**
	 * Updates keyboard input
	 */
	public static void update() {
		Input.upKeys.clear();
		for (int i = 0; i < Input.NUM_KEYCODES; i++)
			if (!Input.getKey(i) && Input.currentKeys.contains(i)) {
				Input.upKeys.add(i);
			}
		Input.downKeys.clear();
		for (int i = 0; i < Input.NUM_KEYCODES; i++)
			if (Input.getKey(i) && !Input.currentKeys.contains(i)) {
				Input.downKeys.add(i);
			}
		Input.currentKeys.clear();
		for (int i = 0; i < Input.NUM_KEYCODES; i++)
			if (Input.getKey(i)) {
				Input.currentKeys.add(i);
			}
		Input.upMouse.clear();
		for (int i = 0; i < Input.NUM_MOUSEBUTTONS; i++)
			if (!Input.getMouse(i) && Input.currentMouse.contains(i)) {
				Input.upMouse.add(i);
			}
		Input.downMouse.clear();
		for (int i = 0; i < Input.NUM_MOUSEBUTTONS; i++)
			if (Input.getMouse(i) && !Input.currentMouse.contains(i)) {
				Input.downMouse.add(i);
			}
		Input.currentMouse.clear();
		for (int i = 0; i < Input.NUM_MOUSEBUTTONS; i++)
			if (Input.getMouse(i)) {
				Input.currentMouse.add(i);
			}
	}
}
