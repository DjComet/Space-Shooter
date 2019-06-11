package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;


public class CameraHelper {
    public OrthographicCamera camera;
    public OrthographicCamera hudCamera;
    public String TAG_CAMERA = "CAMERA";
    private float maxDistanceToPlayer = 20f;


    public CameraHelper(){
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        hudCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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


    public void followGO(GameObject go)//To follow a gameobject (player normally)
    {
        Vector2 goCenterPos = go.getCenterPos() ;

        //keep the camera within a certain distance to the player-----------------------------------------------------------------
        Vector2 direction = new Vector2((camera.position.x - goCenterPos.x <0?-1:1),(camera.position.y - goCenterPos.y <0?-1:1));
        if(Math.abs(camera.position.x - goCenterPos.x) > maxDistanceToPlayer)
        {
            if(direction.x > 0)
                camera.position.x = goCenterPos.x + maxDistanceToPlayer;
            else
                camera.position.x = goCenterPos.x -maxDistanceToPlayer;
        }

        if(Math.abs(camera.position.y - goCenterPos.y) > maxDistanceToPlayer)
        {
            if(direction.y > 0)
                camera.position.y = goCenterPos.y + maxDistanceToPlayer;
            else
                camera.position.y = goCenterPos.y - maxDistanceToPlayer;
        }

        //Keep camera within bounds-----------------------------------------------------------------------------------------------------
        //camera.position.x = MathUtils.clamp(camera.position.x, -bg.width/2 + camera.width, bg.width/2 - width );
        //camera.position.y = MathUtils.clamp(camera.position.y, -bg.height/2, WorldController.instance.getCurrentLevel().gameObjects.get(0).height/2);

        //Gdx.app.debug(TAG_CAMERA, "Supposed position of GO.x " + (go.position.x));
        //Gdx.app.debug(TAG_CAMERA, "Supposed position of GO.y" + (go.position.y));

        camera.update();
    }
}
