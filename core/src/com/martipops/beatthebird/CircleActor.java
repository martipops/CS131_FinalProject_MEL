package com.martipops.beatthebird;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * CircleActor class extends Actor, representing a circular actor in the game.
 * Overrides the hit method to detect circular hit collisions.
 */
public class CircleActor extends Actor {

	/**
	 * Overrides Actor's hit method to detect circular hit collisions.
	 *
	 * @param x         The x coordinate to check for a hit.
	 * @param y         The y coordinate to check for a hit.
	 * @param touchable Whether the hit detection should take into account
	 *                  touchability.
	 * @return This actor if the specified point is within the bounds of the circle,
	 *         null otherwise.
	 */
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		// Find the center of the actor
		float centerX = getOriginX();
		float centerY = getOriginY();

		// Calculate the radius of the circle based on the center coordinates (uses the
		// leastmost length)
		float radius = Math.min(centerX, centerY);

		// Calculate the distance between the hit coordinates and the center coordinates
		float dx = x - centerX;
		float dy = y - centerY;

		// Check if the hit location is within the circle's bounds using the pythagoran
		// theorem
		return dx * dx + dy * dy <= radius * radius ? this : null;
	}
}