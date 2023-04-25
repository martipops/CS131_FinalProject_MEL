package com.martipops.beatthebird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public class GameUI extends Group implements GameInterface {

    BitmapFont font;

    /**
     * Class PopUpText:
     * Creates a bit of text on screen that fades away and destroys itself.
     */
    class PopUpText extends Actor {
        String text;
        int x, y;
        float alpha;

        PopUpText(String text, int x, int y) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.alpha = 1;
        }

        PopUpText(String text) {
            this(text, 200, SCREEN_HEIGHT - 200);
        }

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

    public GameUI() {
        super();
        this.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        font = new BitmapFont(Gdx.files.internal("bubblefont.fnt"));
        this.setTouchable(Touchable.disabled);
        PopUpText p = new PopUpText("Welcome!");
        this.addActor(p);
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        return null;
    }

    public void showPopUp(String displayText) {
        addActor(new PopUpText(displayText));
    }

}
