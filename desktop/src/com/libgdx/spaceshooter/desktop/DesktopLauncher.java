package com.libgdx.spaceshooter.desktop;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.libgdx.spaceshooter.MAIN_GAME;
import com.libgdx.spaceshooter.SpaceShooterMainScene;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		Graphics.DisplayMode desktop = LwjglApplicationConfiguration.getDesktopDisplayMode();
		config.setFromDisplayMode(desktop);
		config.fullscreen = true;
		config.width = desktop.width - 100;
		config.height = desktop.height - 100;

		new LwjglApplication(new MAIN_GAME(), config);
	}
}
