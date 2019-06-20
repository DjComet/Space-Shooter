package com.libgdx.spaceshooter;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class SpaceShooterMenu implements Screen {
    String TAG_LIFECYCLE = "LIFECYCLE";
    public MenuController controller;
    public MenuRenderer renderer;

    @Override
    public void show() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Gdx.app.debug(TAG_LIFECYCLE, "Created");
        controller = new MenuController();
        MenuController.instance = controller;
        renderer = new MenuRenderer(controller);
    }

    @Override
    public void render(float delta) {
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
    public void hide() { Gdx.app.debug(TAG_LIFECYCLE, "Hidden");}

    @Override
    public void resize(int width, int height) {
        renderer.resize(width,height);
        Gdx.app.debug(TAG_LIFECYCLE, "Resized to: "+ height + "x" + width);
    }
}
