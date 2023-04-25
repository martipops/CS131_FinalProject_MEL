package com.martipops.beatthebird;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * CoinActor class extends Image, representing a coin object in the game.
 */
public class CoinActor extends Image {

	/**
	 * The value of the coin.
	 */
	public final int value;

	/**
	 * Constructor for creating a CoinActor with a specified texture region and
	 * value.
	 *
	 * @param region The texture region for the coin image.
	 * @param value  The value of the coin.
	 */
	public CoinActor(TextureRegion region, int value) {
		super(region);
		this.setTouchable(Touchable.disabled);
		this.value = value;
		setWidth(region.getRegionWidth() / 4f);
		setHeight(region.getRegionHeight() / 4f);
	}

	/**
	 * Constructor for creating a CoinActor with a specified texture region, value,
	 * and Inventory object.
	 * Randomizes the coin's initial coordinates within the Inventory.
	 *
	 * @param region The texture region for the coin image.
	 * @param value  The value of the coin.
	 * @param inv    The Inventory object to randomize the coin's initial
	 *               coordinates within.
	 */
	public CoinActor(TextureRegion region, int value, Inventory inv) {
		this(region, value);
		randomizeCoords(inv);
	}

	/**
	 * Randomizes the coin's coordinates within the specified Inventory object.
	 *
	 * @param i The Inventory object to randomize the coin's coordinates within.
	 */
	public void randomizeCoords(Inventory i) {
		float[] coords = generateRandomCoords(i);
		setX(coords[0]);
		setY(coords[1]);
	}

	public float[] generateRandomCoords(Inventory i) {
		float[] coords = new float[2];
		coords[0] = MathUtils.random(0, i.getWidth() / 2f);
		coords[1] = MathUtils.random(0, i.getHeight() / 2f);
		return coords;
	}

	/**
	 * Overrides Image's hit method to disable touchability.
	 *
	 * @param x         The x coordinate to check for a hit.
	 * @param y         The y coordinate to check for a hit.
	 * @param touchable Whether the hit detection should take into account
	 *                  touchability.
	 * @return null, since touchability is disabled for the CoinActor.
	 */
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		return null;
	}

}