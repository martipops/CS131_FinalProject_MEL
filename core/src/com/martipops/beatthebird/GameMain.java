package com.martipops.beatthebird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameMain extends ApplicationAdapter implements InputProcessor, GameInterface {

	// Variables are static to make the objects globally accessable
	public static WheelActor wheel;
	public static Texture wheelTexture, coinTenTexture, numbersTexture, triangleTexture, backgroundTexture;
	public static Inventory playerInventory;
	public static BetSpot betSpotOne, betSpotThree, betSpotFive, betSpotTen, betSpotTwenty;
	public static Stage stage;
	public static Actor touchActor;
	public static TextureRegion wheelRegion, coinTenRegion, numbersRegion, triangleRegion, backgroundRegion;
	public static Image triangleImage, numbersImage, backgroundImage;
	public static BetLogic bets;
	public static GameUI ui;

	/**
	 * ApplicationAdapter override that will be called on the creation of the window
	 * Constructs textures, game actors, and map input processors.
	 * Also sets the player inventory to 100.
	 */
	@Override
	public void create() {
		stage = new Stage();
		loadTextures();
		createActors();
		playerInventory.setTotal(100);
		/**
		 * Set up multiple input processors to account for objects on stage as well as
		 * touch events inhereted from InputProcessor (self)
		 */
		InputMultiplexer multiplexer = new InputMultiplexer(stage, this);
		Gdx.input.setInputProcessor(multiplexer);
	}

	/**
	 * This method renders the stage by clearing the screen and drawing all actors
	 * on the stage. Called for each frame.
	 */
	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1); // Set the color to clear the screen with
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
		stage.act(Gdx.graphics.getDeltaTime()); // Update all actors on the stage
		stage.draw(); // Draw all actors on the stage
	}

	/**
	 * Disposes textures from memory. Called on exit
	 */
	@Override
	public void dispose() {
		wheelTexture.dispose();
		coinTenTexture.dispose();
		numbersTexture.dispose();
		stage.dispose();
	}

	/**
	 * Loads texture files into memory for rendering use.
	 */
	private void loadTextures() {
		wheelTexture = new Texture(Gdx.files.internal("wheel.png"));
		coinTenTexture = new Texture(Gdx.files.internal("diamond.png"));
		numbersTexture = new Texture(Gdx.files.internal("numbers.png"));
		triangleTexture = new Texture(Gdx.files.internal("triangle.png"));
		backgroundTexture = new Texture(Gdx.files.internal("background.png"));
	}

	/**
	 * Creates game objects, like actors and Texture Regions and adds them to
	 * respective locations on a stage, built from the lowest layer to the highest
	 * layer
	 */
	private void createActors() {
		wheelRegion = new TextureRegion(wheelTexture);
		coinTenRegion = new TextureRegion(coinTenTexture);
		numbersRegion = new TextureRegion(numbersTexture);
		triangleRegion = new TextureRegion(triangleTexture);
		backgroundRegion = new TextureRegion(backgroundTexture);

		// sets the background image and bounds
		backgroundImage = new Image(backgroundRegion);
		backgroundImage.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		wheel = new WheelActor(wheelRegion);
		wheel.setScale(1.2f);
		wheel.setPosition(GameInterface.SCREEN_WIDTH / 6, GameInterface.SCREEN_HEIGHT / 2.5f);

		// sets triangle image and location
		triangleImage = new Image(triangleRegion);
		triangleImage.setBounds(362, 683, 50, 50);

		// set the bet spots image and location
		numbersImage = new Image(numbersRegion);
		numbersImage.setBounds(100, 100, numbersRegion.getRegionWidth(), numbersRegion.getRegionHeight());

		// set inventory values
		playerInventory = new Inventory(GameInterface.SCREEN_WIDTH / 1.6f, 25, GameInterface.SCREEN_WIDTH - 25,
				GameInterface.SCREEN_HEIGHT - 25);
		playerInventory.setName("Player");

		// set values for bet spots
		betSpotOne = new BetSpot(115, 120, 215, 225, 1);
		betSpotOne.setName("One");
		betSpotThree = new BetSpot(225, 120, 325, 225, 3);
		betSpotThree.setName("Three");
		betSpotFive = new BetSpot(335, 120, 435, 225, 5);
		betSpotFive.setName("Five");
		betSpotTen = new BetSpot(445, 120, 545, 225, 10);
		betSpotTen.setName("Ten");
		betSpotTwenty = new BetSpot(555, 120, 655, 225, 20);
		betSpotTwenty.setName("Twenty");

		// creates the UI
		ui = new GameUI();

		// set initial bets
		bets = new BetLogic();
		bets.add(betSpotOne);
		bets.add(betSpotThree);
		bets.add(betSpotFive);
		bets.add(betSpotTen);
		bets.add(betSpotTwenty);

		// add actors to stage
		stage.addActor(backgroundImage);
		stage.addActor(wheel);
		stage.addActor(numbersImage);
		stage.addActor(triangleImage);
		stage.addActor(playerInventory);
		stage.addActor(betSpotOne);
		stage.addActor(betSpotThree);
		stage.addActor(betSpotFive);
		stage.addActor(betSpotTen);
		stage.addActor(betSpotTwenty);
		stage.addActor(ui);

	}

	/**
	 * Handles logic for when the wheel stops, calculatse the winnings then clears
	 * the bets
	 */
	public static void wheelLanded(int spot) {
		int winamt = bets.calculateWinnings(spot);
		playerInventory.addTotal(winamt);
		bets.clearBets();
	}

	public Actor touchHit() {
		touchActor = stage.hit(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), false);
		return touchActor;
	}

	/**
	 * This method is used to register a bet by adding or subtracting coins from the
	 * player's inventory. It checks if the wheel is currently spinning and if the
	 * hit object is an instance of Inventory. If the hit object is the player's
	 * inventory or the wheel is spinning, it returns false. Otherwise, it transfers
	 * the specified amount of coins to the hit inventory using the transferCoin
	 * method of the player's inventory.
	 * 
	 * @param amount the amount of coins to add or subtract from the hit inventory
	 * @return true if the coin is transferred.
	 */
	public boolean registerBet(int amount) {
		if (wheel.isSpinning())
			return false;
		Actor a = touchHit();
		if (!(a instanceof Inventory))
			return false;
		Inventory i = ((Inventory) a);
		if (i.getName().equals("player"))
			return false;
		playerInventory.transferCoin(i, amount);
		return false;
	}

	/**
	 * 
	 * This method is called when the user touches (clicks) the screen. It hits the
	 * stage at the touch coordinates to detect if an object is clicked. If an actor
	 * is hit, touchActor is set to that actor. If no actor is hit, touchActor is
	 * set to null.
	 * Calls registerBet() based on right or left click
	 * 
	 * @param screenX the x-coordinate of the touch on the screen
	 * @param screenY the y-coordinate of the touch on the screen
	 * @param pointer the pointer for the event
	 * @param button  the button for the event
	 * @return always returns false
	 */
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		touchHit();
		registerBet(button * 2 - 1); // -1 if right click, 1 if left
		return false;
	}

	/**
	 * Register bets when user scrolls while the cursor is over an inventory spot
	 * 
	 * @param amountX the amount of horizontal scrolling
	 * @param amountY the amount of vertical scrolling
	 * @return always false
	 */
	@Override
	public boolean scrolled(float amountX, float amountY) {
		registerBet((amountY < 0) ? -1 : 1);
		return false;
	}

	/**
	 * Override from InputProcessor interface
	 * Modified to allow for debugging
	 */
	@Override
	public boolean keyTyped(char character) {
		switch (character) {
			case 'c':
				playerInventory.setTotal(100);
				super.render();
				break;
			case 'l':
				System.out.println(Gdx.input.getX() + ", " + (Gdx.graphics.getHeight() - Gdx.input.getY()));
				break;
		}
		return false;
	}

	/**
	 * Resets the touchActor to null when a touch (click) is released
	 */
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		touchActor = null;
		return false;
	}

	/**
	 * Override from InputProcessor interface
	 */
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	/**
	 * Override from InputProcessor interface
	 */
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	/**
	 * Override from InputProcessor interface
	 */
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	/**
	 * Override from InputProcessor interface
	 */
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

}
