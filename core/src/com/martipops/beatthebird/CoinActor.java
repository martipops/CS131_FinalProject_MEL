package com.martipops.beatthebird;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
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
	 * The texture region for the coin image.
	 */
	TextureRegion region = GameMain.coinTenRegion;

	/**
	 * Whether the coin is currently moving.
	 */
	boolean moving = false;

	/**
	 * The x coordinate of the coin's destination.
	 */
	float destX;

	/**
	 * The y coordinate of the coin's destination.
	 */
	float destY;

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
		this.setX(MathUtils.random(0, i.getWidth() / 2f));
		this.setY(MathUtils.random(0, i.getHeight() / 2f));
		this.setZIndex(0);
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