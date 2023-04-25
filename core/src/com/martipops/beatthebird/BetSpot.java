package com.martipops.beatthebird;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * BetSpot class extends Inventory, representing a betting spot in the game.
 */
public class BetSpot extends Inventory {

    /**
     * The value of the betting spot.
     */
    int spotValue;

    /**
     * Constructor for creating a BetSpot with a specified hitbox and spot value.
     *
     * @param x1        Leftmost coordinate of the BetSpot's hitbox.
     * @param y1        Uppermost coordinate of the BetSpot's hitbox.
     * @param x2        Rightmost coordinate of the BetSpot's hitbox.
     * @param y2        Lowermost coordinate of the BetSpot's hitbox.
     * @param spotValue The value of the BetSpot.
     */
    public BetSpot(float x1, float y1, float x2, float y2, int spotValue) {
        super(x1, y1, x2, y2);
        this.setBounds(x1, y1, (x2 - x1) * 2f, (y2 - y1) * 2f);
        setScale(0.5f);
        this.spotValue = spotValue;
    }

    /**
     * Calculates the winnings for the BetSpot based on the spot landed and the
     * total bet amount.
     *
     * @param spotLanded The number of spots that were hit in the game.
     * @return The total amount of winnings for the BetSpot.
     */
    public int getWinnings(int spotLanded) {
        return (spotLanded == spotValue) ? (total * spotValue) + total : 0;
    }

    /**
     * Draw the objects inside the spot at half transparency
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, 0.5f);
    }

}