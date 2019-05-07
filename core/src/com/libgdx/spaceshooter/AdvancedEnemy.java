package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;


public class AdvancedEnemy extends GameObject {

    int lives;
    public float maxSpeed;
    public float roll = 0f;
    public boolean dead = false;
    Vector2[] waypoints;
    enum WaypointType {curveL, curveR, loopL, loopR}
    WaypointType waypointType;
    public Vector2 speed;

    public AdvancedEnemy(float posX, float posY, WaypointType wpt){
        position = new Vector2(posX, posY);
        waypointType = wpt;
        rotation = 0;

        width = Assets.getInstance().aeTexRegions[0].getRegionWidth();
        height = Assets.getInstance().aeTexRegions[0].getRegionHeight();

        scale.x = 1.0f;
        scale.y = 1.0f;

        speed.x = 0f;
        speed.y = 0f;

        createWaypointPath(waypointType);
        tag = "ENEMY";
    }

    @Override
    public void draw(SpriteBatch batch) {
        animateRoll(batch);
    }

    @Override
    public void update(float delta) {

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
        roll = speed.x/maxSpeed;
        position = position.interpolate(waypoints[i],t, Interpolation.linear);
    }

    void animateRoll(SpriteBatch batch)
    {
        int i = 0;

        if(roll ==0)//This is for precise control over the animation (allows for quick direction changes with proper frame correspondance)
        {
            i = 3;
        }
        else if(roll>0 && roll<=0.2f)
        {
            i=4;
        }
        else if(roll>0.2f && roll<=0.7f) //Right hand turn
        {
            i=5;
        }
        else if(roll>0.7)
        {
            i=6;
        }
        else if(roll<0 && roll>=-0.2f)
        {
            i=2;
        }
        else if(roll<-0.2f && roll>=-0.7f) //left hand turn
        {
            i=1;
        }
        else if(roll<-0.7)
        {
            i=0;
        }

        if(dead)
        {
            i=7;
        }

        batch.draw(texRegionToDraw(i),position.x,position.y,0,0,width,height,scale.x,scale.y,rotation);
    }

    TextureRegion texRegionToDraw(int i)
    {
        return Assets.getInstance().aeTexRegions[i];
    }
}
