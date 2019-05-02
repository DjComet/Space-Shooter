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


    public void followGO(GameObject go)
    {
        camera.position.x = go.position.x+go.width/2;
        camera.position.y = go.position.y+go.height/2;

        //Gdx.app.debug(TAG_CAMERA, "Supposed position of GO.x " + (go.position.x));
        //Gdx.app.debug(TAG_CAMERA, "Supposed position of GO.y" + (go.position.y));

        camera.update();
    }
}
