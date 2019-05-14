package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Iterator;

public class WorldRenderer {

    public SpriteBatch batch;
    public WorldController controller;
    public String TAG_TIME = "TIMES";
    public float elapsedTime;

    public WorldRenderer(WorldController wc){
        this.controller = wc;
        init();
    }

    public void init(){
        batch = new SpriteBatch();
        controller.ch.camera.position.set(0,0,0);
        controller.ch.camera.update();
    }

    public void render(){

        batch.setProjectionMatrix(controller.ch.camera.combined);

        elapsedTime += Gdx.graphics.getDeltaTime();
        long t0 = System.nanoTime();


        batch.begin();

        for(Iterator<GameObject> iter = controller.getCurrentLevel().gameObjects.iterator(); iter.hasNext();)
        {
            GameObject element = iter.next();
            element.draw(batch);
        }


        batch.end();

        long elapsed = System.nanoTime() - t0;

        float elapsedMs = elapsed / 1000000;

        //Gdx.app.debug(TAG_TIME, elapsedMs + "ms - " + batch.maxSpritesInBatch + " - " + batch.renderCalls);


    }

    public void resize(int width, int height){
        controller.ch.camera.viewportWidth = (Constants.VIEWPORT_HEIGHT/height)*width;
        controller.ch.camera.update();
        System.out.println("ViewportW: "+controller.ch.camera.viewportWidth + " - ViewportH: "+controller.ch.camera.viewportHeight);

    }
}
