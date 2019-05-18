package com.libgdx.spaceshooter;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class SpaceShooterMain implements ApplicationListener {
	String TAG_LIFECYCLE = "LIFECYCLE";
	public WorldController controller;
	public WorldRenderer renderer;

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.app.debug(TAG_LIFECYCLE, "Created");
		controller = new WorldController();
		renderer = new WorldRenderer(controller);
	}

	@Override
	public void render () {
		controller.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render();

	}

	@Override
	public void dispose () {
		Gdx.app.debug(TAG_LIFECYCLE, "Disposed");
	}

	@Override
	public void pause() {
		Gdx.app.debug(TAG_LIFECYCLE, "Paused");
	}

	@Override
	public void resume() {
		Gdx.app.debug(TAG_LIFECYCLE, "Resumed");
	}

	@Override
	public void resize(int width, int height) {
		renderer.resize(width,height);
		Gdx.app.debug(TAG_LIFECYCLE, "Resized to: "+ height + "x" + width);
	}
}
