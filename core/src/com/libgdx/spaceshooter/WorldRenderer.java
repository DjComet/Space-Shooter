package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;

public class WorldRenderer {

    public SpriteBatch batch;
    public WorldController controller;
    public String TAG_TIME = "TIMES";
    public float elapsedTime;
    Viewport viewport;

    public WorldRenderer(WorldController wc){
        this.controller = wc;
        init();
    }

    public void init(){
        batch = new SpriteBatch();
        viewport = new FitViewport(50,75, controller.ch.camera);
        viewport.apply();
        //controller.ch.camera.position.set(controller.ch.camera.viewportWidth/2, controller.ch.camera.viewportHeight/2,0);
        controller.ch.camera.position.set(0,0,0);
        controller.ch.camera.update();
    }

    public void render(){

        batch.setProjectionMatrix(controller.ch.camera.combined);

        elapsedTime += Gdx.graphics.getDeltaTime();
        long t0 = System.nanoTime();


        batch.begin();

        updateArrays();


        batch.end();

        long elapsed = System.nanoTime() - t0;

        float elapsedMs = elapsed / 1000000;

        //Gdx.app.debug(TAG_TIME, elapsedMs + "ms - " + batch.maxSpritesInBatch + " - " + batch.renderCalls);


    }

    void updateArrays()
    {
        for(int i = 0; i<WorldController.instance.getCurrentLevel().Layers.size(); i++)
        {
            for(int j = 0; j<WorldController.instance.getCurrentLevel().Layers.get(i).list.size(); j++)
            {
                WorldController.instance.getCurrentLevel().Layers.get(i).list.get(j).draw(batch);
            }
        }
    }

    public void resize(int width, int height){
        viewport.update(width,height);
        //controller.ch.camera.viewportWidth = (Constants.VIEWPORT_HEIGHT/height)*width;
        //controller.ch.camera.position.set(controller.ch.camera.viewportWidth/2, controller.ch.camera.viewportHeight/2,0);
        controller.ch.camera.update();
        System.out.println("ViewportW: "+controller.ch.camera.viewportWidth + " - ViewportH: "+controller.ch.camera.viewportHeight);

    }
}
