package com.martipops.beatthebird;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.martipops.beatthebird.GameMain;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main(String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(GameInterface.SCREEN_WIDTH, GameInterface.SCREEN_HEIGHT);
		config.setResizable(false);
		config.setForegroundFPS(60);
		config.setTitle("Beat The Bird");
		new Lwjgl3Application(new GameMain(), config);
	}
}
