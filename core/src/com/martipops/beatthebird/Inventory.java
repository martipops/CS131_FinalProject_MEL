package com.martipops.beatthebird;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Inventory class extends Group, representing the player's inventory
 * for collecting coins in the game.
 */
public class Inventory extends Group {

	/**
	 * The font for displaying the total value of coins in the inventory.
	 */
	private BitmapFont font;

	/**
	 * The total value of coins currently in the inventory.
	 */
	int total = 0;

	/**
	 * The texture region for the inventory's coins.
	 */
	TextureRegion coinRegion = GameMain.coinTenRegion;

	/**
	 * Constructor for creating an inventory with a specified hitbox.
	 *
	 * @param x1 Leftmost coordinate of the inventory's hitbox.
	 * @param y1 Uppermost coordinate of the inventory's hitbox.
	 * @param x2 Rightmost coordinate of the inventory's hitbox.
	 * @param y2 Lowermost coordinate of the inventory's hitbox.
	 */
	public Inventory(float x1, float y1, float x2, float y2) {
		// Set the inventory's position and size based on the hitbox coordinates
		this.setBounds(x1, y1, x2 - x1, y2 - y1);
		font = new BitmapFont();
		font.setColor(Color.WHITE);
	}

	/**
	 * Adds an actor to the inventory.
	 * Overrides Group's addActor method to randomize the coordinates of a new
	 * CoinActor being added to the inventory.
	 *
	 * @param actor The actor to add to the inventory.
	 */
	@Override
	public void addActor(Actor actor) {
		if (actor instanceof CoinActor) {
			// Randomize the position of the new CoinActor within the inventory's hitbox
			CoinActor c = ((CoinActor) actor);
			c.randomizeCoords(this);
			super.addActor(c);
		}
	}

	/**
	 * Removes an actor from the inventory.
	 * Overrides Group's removeActor method to only remove a CoinActor from the
	 * inventory.
	 *
	 * @param actor The actor to remove from the inventory.
	 * @return true if the actor was removed from the inventory, false otherwise.
	 */
	@Override
	public boolean removeActor(Actor actor) {
		if (actor instanceof CoinActor) {
			// Remove the CoinActor from the inventory and return true
			super.removeActor(actor);
			return true;
		}
		// Return false if the actor is not a CoinActor
		return false;
	}

	/**
	 * Returns the CoinActor at the specified index in the inventory's children.
	 *
	 * @param i The index of the CoinActor to retrieve.
	 * @return The CoinActor at the specified index, or null if there is no
	 *         CoinActor at that index.
	 */
	public CoinActor getCoin(int i) {
		return this.getChild(i) instanceof CoinActor ? (CoinActor) this.getChild(i) : null;
	}

	/**
	 * Transfers coins from this inventory to a destination inventory.
	 *
	 * @param destination The inventory to transfer coins to.
	 * @param amount      The amount of coins to transfer.
	 */
	public boolean transferCoin(Inventory destination, int amount) {
		int iterations = Math.abs(amount);
		for (int i = 0; i < iterations; i++) {
			CoinActor coin;
			Inventory source = amount < 0 ? this : destination;
			Inventory target = source == this ? destination : this;
			if (source.total < 10)
				return false;
			coin = source.getCoin(0);
			source.total -= coin.value;
			target.total += coin.value;
			source.removeActor(coin);
			target.addActor(coin);
		}
		return true;
	}

	/**
	 * Sets the total value of coins in the inventory to a specified value.
	 *
	 * @param total The total value to set the inventory to.
	 */
	public void setTotal(int total) {
		this.total = 0;
		this.clearChildren();
		addTotal(total);
	}

	/**
	 * Adds a specified amount to the total value of coins in the inventory.
	 * Adds new CoinActors to the inventory to represent any additional coins added.
	 *
	 * @param amt The amount to add to the total value of coins in the inventory.
	 */
	public void addTotal(int amt) {
		this.total += amt;
		int numCoins = amt / 10;
		addCoins(numCoins);
	}

	/**
	 * Adds a specified number of CoinActors to the inventory.
	 *
	 * @param numCoins The number of CoinActors to add to the inventory.
	 */
	private void addCoins(int numCoins) {
		for (int i = 0; i < numCoins; i++) {
			CoinActor coin = new CoinActor(GameMain.coinTenRegion, 10, this);
			this.addActor(coin);
		}
	}

	/**
	 * Spawns a new CoinActor in the inventory with the coin texture and a value of
	 * 10.
	 */
	public void spawnCoin() {
		this.addActor(new CoinActor(coinRegion, 10));
	}

	/**
	 * Overrides the parent draw method that is called every frame to draw a text
	 * object to keep track of the total
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		font.draw(batch, this.getName() + ": " + total, this.getX(), this.getY());
	}

}