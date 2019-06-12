package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuRenderer {

    public SpriteBatch batch;
    public MenuController controller;
    public String TAG_TIME = "TIMES";
    public float elapsedTime;
    Viewport viewport;

    public MenuRenderer(MenuController mc){
        controller = mc;
        init();
    }

    public void init(){
        batch = new SpriteBatch();
        viewport = new FitViewport(350,360, controller.ch.camera);
        viewport.apply();
        //controller.ch.camera.position.set(controller.ch.camera.viewportWidth/2, controller.ch.camera.viewportHeight/2,0);
        controller.ch.camera.position.set(0,0,0);
        controller.ch.camera.update();
    }

    public void render(){

        elapsedTime += Gdx.graphics.getDeltaTime();
        long t0 = System.nanoTime();

        batch.setProjectionMatrix(controller.ch.camera.combined);
        batch.begin();
        updateArrays();
        batch.end();


        batch.setProjectionMatrix(controller.ch.hudCamera.combined);
        batch.begin();
        controller.hud.render(batch);
        batch.end();

        long elapsed = System.nanoTime() - t0;

        float elapsedMs = elapsed / 1000000;

        //Gdx.app.debug(TAG_TIME, elapsedMs + "ms - " + batch.maxSpritesInBatch + " - " + batch.renderCalls);


    }

    void updateArrays()
    {
        for(int i = 0; i<MenuController.instance.getCurrentLevel().Layers.size(); i++)
        {
            for(int j = 0; j<MenuController.instance.getCurrentLevel().Layers.get(i).list.size(); j++)
            {
                MenuController.instance.getCurrentLevel().Layers.get(i).list.get(j).draw(batch);
            }
        }
    }

    public void resize(int width, int height){
        viewport.update(width,height);
        //controller.ch.camera.viewportWidth = (Constants.VIEWPORT_HEIGHT/height)*width;
        //controller.ch.camera.position.set(controller.ch.camera.viewportWidth/2, controller.ch.camera.viewportHeight/2,0);
        controller.ch.camera.update();
        System.out.println("ViewportW: "+controller.ch.camera.viewportWidth + " - ViewportH: "+controller.ch.camera.viewportHeight);

        controller.ch.hudCamera.position.x = width/2;
        controller.ch.hudCamera.position.y = height/2;
        controller.ch.hudCamera.update();

    }
}
