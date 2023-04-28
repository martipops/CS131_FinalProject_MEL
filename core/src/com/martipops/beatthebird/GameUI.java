package com.martipops.beatthebird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;

/**
 * Class GameUI:
 * Extends a Group of actors, representing the user interface for the game.
 */
public class GameUI extends Group implements GameInterface {

    BitmapFont font;

    /**
     * Class PopUpText:
     * Creates a bit of text on screen that fades away and destroys itself.
     * Used for displaying messages to the player.
     */
    class PopUpText extends Actor {
        String text;
        int x, y;
        float alpha;

        /**
         * Constructor for creating a PopUpText object.
         * 
         * @param text The text to display.
         * @param x    The x-coordinate of the text.
         * @param y    The y-coordinate of the text.
         */
        PopUpText(String text, int x, int y) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.alpha = 1;
        }

        /**
         * Constructor for creating a PopUpText object.
         * 
         * @param text The text to display.
         */
        PopUpText(String text) {
            this(text, 200, SCREEN_HEIGHT - 200);
        }

        /**
         * Draws the text on screen.
         * 
         * @param batch       The batch to draw the text on.
         * @param parentAlpha The alpha value of the parent.
         */
        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            font.setColor(1f, 1f, 1f, alpha);
            font.draw(batch, text, x, y);
            alpha = alpha - 0.01f;
            this.y++;
            if (alpha < 0f) {
                this.remove();
                this.clear();
            }
        }

    }

    /**
     * Constructor for creating a GameUI object.
     */
    public GameUI() {
        super();
        this.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        font = new BitmapFont(Gdx.files.internal("bubblefont.fnt"));
        this.setTouchable(Touchable.disabled);
        PopUpText p = new PopUpText("Welcome!");
        this.addActor(p);
    }

    /**
     * Draws the inventory on screen.
     * 
     * @param batch       The batch to draw the inventory on.
     * @param parentAlpha The alpha value of the parent.
     */
    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return null;
    }

    /**
     * Adds an actor to the inventory.
     * Overrides Group's addActor method to randomize the coordinates of a new
     * CoinActor being added to the inventory.
     *
     * @param actor The actor to add to the inventory.
     */
    public void showPopUp(String displayText) {
        addActor(new PopUpText(displayText));
    }

}
