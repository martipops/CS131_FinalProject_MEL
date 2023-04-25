package com.martipops.beatthebird;

/**
 * GameInterface interface defines constants for the game.
 */
public interface GameInterface {

	/**
	 * The width of the game screen.
	 */
	final int SCREEN_WIDTH = 1200;

	/**
	 * The height of the game screen.
	 */
	final int SCREEN_HEIGHT = 800;

	/**
	 * The values of the spots on the wheel, not including the 1 spots.
	 */
	final int[] WHEEL_SPOTS = { 3, 10, 3, 5, 3, 20, 3, 5, 3, 10, 3, 5, 5 };

	/**
	 * Number of yellow spots on the wheel
	 */
	final double YELLOW_SPOTS = 12;

	/**
	 * Number of other numbered spots on the wheel
	 */
	final double OTHER_NUMBERED_SPOTS = 13;

	/**
	 * The ratio of yellow spots to the total number of spots.
	 */
	final double YELLOW_SPOT_THRESHOLD = YELLOW_SPOTS / (YELLOW_SPOTS + OTHER_NUMBERED_SPOTS);

	/**
	 * The total number of sectors in the wheel.
	 * There are a little over 12 sectors in the wheel.
	 */
	final double TOTAL_SECTORS = YELLOW_SPOTS + (1 - YELLOW_SPOT_THRESHOLD);

	/**
	 * The angle between each sector in degrees.
	 */
	final double SECTOR_ANGLE = 360.0 / TOTAL_SECTORS;
}