package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;


public class CameraHelper {
    public OrthographicCamera camera;
    public String TAG_CAMERA = "CAMERA";

    public CameraHelper(){
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
    }

    public void zoomIn(float delta)
    {
        camera.zoom -= 1*delta;
        camera.update();
        Gdx.app.debug(TAG_CAMERA, "Camera has a zoom value of: " + camera.zoom);
    }

    public void zoomOut(float delta)
    {
        camera.zoom += 1*delta;
        camera.update();
        Gdx.app.debug(TAG_CAMERA, "Camera has a zoom value of: " + camera.zoom);
    }

    public void moveCamera(float delta)
    {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){camera.position.x -= 1*delta;}
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){camera.position.x += 1*delta;}
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){camera.position.y += 1*delta;}
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){camera.position.y -= 1*delta;}
        camera.update();
    }

    public void followGO(GameObject go)
    {
        camera.position.x = go.position.x;
        camera.position.y = go.position.y;

        Gdx.app.debug(TAG_CAMERA, "Supposed position of GO.x " + (go.position.x));
        Gdx.app.debug(TAG_CAMERA, "Supposed position of GO.y" + (go.position.y));

        camera.update();
    }
}
