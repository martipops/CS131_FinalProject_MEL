package com.martipops.beatthebird;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Class WheelActor:
 * The WheelActor class extends the CircleActor class and represents a wheel
 * that can be spun by the user. It has a texture region, rotation speed, and a
 * boolean value to determine if it is spinning or not.
 * 
 * The WheelActor class also includes methods to spin the wheel, stop the wheel
 * from spinning, detect which spot the wheel has landed on, and detect
 * collision with other objects.
 * 
 * Also uses GameInterface for usage in {@link #getSpot() getSpot}
 */
public class WheelActor extends CircleActor implements GameInterface {

	/**
	 * The texture region of the wheel
	 */
	private TextureRegion region;
	/**
	 * The speed at which the wheel is rotating
	 */
	private float rotationSpeed;
	/**
	 * A boolean value that represents if the wheel is currently spinning
	 */
	private boolean spinning;
	/**
	 * The factor by which the rotation speed decreases over time when the wheel is
	 * spinning
	 */
	private float slowdownFactor = 0.99f;

	/**
	 * Constructs a new WheelActor object with the specified texture region.
	 * 
	 * @param textureRegion the texture region of the wheel
	 */
	public WheelActor(TextureRegion textureRegion) {
		this.spinning = false;
		this.region = textureRegion;
		setWidth(textureRegion.getRegionWidth());
		setHeight(textureRegion.getRegionHeight());
		setOrigin(getWidth() / 2f, getHeight() / 2f);
	}

	/**
	 * 
	 * The act method is called every frame that this actor is on a stage.
	 * 
	 * The method updates the wheel's rotation if it is spinning. It also decreases
	 * the rotation speed over time until it stops.
	 * 
	 * @param delta the time elapsed since the last frame
	 */
	@Override
	public void act(float delta) {
		super.act(delta);
		// Update the wheel's rotation if it is spinning
		if (spinning) {
			setRotation(getRotation() + rotationSpeed * delta);
			// Slow down the rotation speed over time
			rotationSpeed *= slowdownFactor;
			// slow it down to a stop when below certain speed
			if (rotationSpeed < 10.0f) {
				slowdownFactor -= 0.01f;
				if (rotationSpeed < 0.2f) {
					// Stop spinning when the rotation speed is too slow
					stop();
					GameMain.wheelLanded(getSpot());
				}
			}
		}
	}

	/**
	 * This method is called to draw the texture region of the wheel on the screen
	 * in its respective bounds
	 * 
	 * @param batch       the batch to draw the texture region with
	 * @param parentAlpha the alpha value of the parent
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// Draw the wheel's texture region at the actor's position and rotation
		batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
				getScaleY(), getRotation());
	}

	/**
	 * Collision Detector
	 * This is called on collision with other objects
	 * IN THIS CASE: The object is called when it is clicked by a mouse. it calls
	 * if the detected actor matches the touch actor mapped to the
	 * InputProcessor in GameMain
	 */
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		Actor g = super.hit(x, y, touchable); // use circular hit detections
		if (g != null && g.equals(GameMain.touchActor)) {
			this.spin();
		}
		return g;
	}

	/**
	 * This is called when a player clicks the wheel. It allows for the animation
	 * process of the wheel to begin with a specified speed.
	 * 
	 * @param rotationSpeed the speed at which the wheel should spin
	 */
	public void spin(int rotationSpeed) {
		if (!spinning) {
			// The wheel is not already spinning, so start spinning it
			spinning = true;
			this.rotationSpeed = rotationSpeed;
			slowdownFactor = 0.99f;
		}
	}

	/**
	 * This is called when a player clicks the wheel. It allows for the animation
	 * process of the wheel to begin with a random speed.
	 */
	public void spin() {
		int rspeed = MathUtils.random(500, 1000);
		spin(rspeed);
	}

	/**
	 * Accounts for the game logic for when the wheel comes to a stop
	 */
	public void stop() {
		// Stop the wheel from spinning
		spinning = false;
		rotationSpeed = 0;
	}

	/**
	 * Determines the spot the wheel landed on based on its rotation angle.
	 *
	 * The wheel has 12 yellow spots (labeled '1') and 13 other-numbered spots. The
	 * yellow spots are slightly larger than the other-numbered spots. This method
	 * calculates the sector the wheel lands on and then determines if the spot is a
	 * yellow '1' or an other-numbered spot based on a relative location threshold.
	 *
	 * @return The value of the spot the wheel landed on. If the spot is a yellow
	 *         '1', it will be labeled as 1. Otherwise, it will be labeled with the
	 *         value of the spot.
	 */

	public int getSpot() {
		// get remainder of 360 to determine its relative angle
		double rotationAngle = getRotation() % 360.0;
		// determine sector
		double loc = rotationAngle / SECTOR_ANGLE;
		// determine which half of the sector
		int spot = ((loc % 1) < YELLOW_SPOT_THRESHOLD) ? WHEEL_SPOTS[(int) loc] : 1;
		return spot;
	}

	/**
	 * Getter for spinning
	 * 
	 * @return true if wheel is spinning
	 */
	public boolean isSpinning() {
		return spinning;
	}

}
