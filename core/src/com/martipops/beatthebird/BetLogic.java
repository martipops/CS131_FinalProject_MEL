package com.martipops.beatthebird;

import java.util.LinkedList;

/**
 * BetLogic class extends LinkedList, representing the logic for the game's
 * betting system.
 */
public class BetLogic extends LinkedList<BetSpot> {

    /**
     * Calculates the total winnings for all BetSpot objects in the list.
     * 
     * @param spot The number of spots that were hit in the game.
     * @return The total amount of winnings from all BetSpot objects.
     */
    public int calculateWinnings(int spot) {
        int amtWon = 0;
        for (BetSpot t : this)
            amtWon += t.getWinnings(spot);
        GameMain.ui.showPopUp("Won " + amtWon + " Diamonds!");
        return amtWon;
    }

    /**
     * Resets the total bet amounts for all BetSpot objects in the list to 0.
     */
    public void clearBets() {
        for (BetSpot spot : this)
            spot.setTotal(0);
    }

}