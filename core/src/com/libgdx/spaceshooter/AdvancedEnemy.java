package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;


public class AdvancedEnemy extends GameObject {

    int lives;
    Vector2[] waypoints;
    enum WaypointType {curveL, curveR, loopL, loopR}
    WaypointType waypointType;
    Vector2 speed;

    public AdvancedEnemy(float posX, float posY, WaypointType wpt){
        position = new Vector2(posX, posY);
        waypointType = wpt;
        rotation = 0;

        //width = Assets.getInstance().textureRegions[0].getRegionWidth();
        //height = Assets.getInstance(.textureRegions[0].getRegionHeight();

        width = 1.0f;
        height = 1.0f;

        scale.x = 1.0f;
        scale.y = 1.0f;

        speed.x = 0f;
        speed.y = 0f;

        createWaypointPath(waypointType);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(Assets.getInstance().aeTexRegions[0],position.x,position.y,0,0,width,height,scale.x,scale.y,rotation);
    }

    @Override
    public void update(float delta) {
        int directionX = 1;
        int directionY = 0;

        move();


    }

    public void createWaypointPath(WaypointType wpt){

        Vector2[]wpCL = {new Vector2(0,0), new Vector2(0,0), new Vector2(0,0), new Vector2(0,0)};
        Vector2[]wpCR = {new Vector2(0,0), new Vector2(0,0), new Vector2(0,0), new Vector2(0,0)};
        Vector2[]wpLL = {new Vector2(0,0), new Vector2(0,0), new Vector2(0,0), new Vector2(0,0), new Vector2(0,0), new Vector2(0,0)};
        Vector2[]wpLR = {new Vector2(0,0), new Vector2(0,0), new Vector2(0,0), new Vector2(0,0), new Vector2(0,0), new Vector2(0,0)};

        switch (wpt)
        {
            case curveL: waypoints = wpCL;
                break;

            case curveR: waypoints = wpCR;
                break;

            case loopL: waypoints = wpLL;
                break;

            case loopR: waypoints = wpLR;
                break;
        }
    }

    public void move()
    {
        int i=0;
        float t=0;
        position = position.interpolate(waypoints[i],t, Interpolation.linear);
    }
}
