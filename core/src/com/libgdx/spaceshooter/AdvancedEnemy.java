package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class AdvancedEnemy extends GameObject {

    int lives;
    public float maxSpeed;
    public float roll = 0f;
    public boolean dead = false;


    public Vector2 speed;
    float t = 0;

    public AdvancedEnemy(float posX, float posY){
        position = new Vector2(posX, posY);

        rotation = 180;

        width = 8;
        height = 8;

        scale = new Vector2(1,1);

        speed = new Vector2(0f,-20f);

        rectangle = new Rectangle();

        tag = "ENEMY";
    }

    @Override
    public void draw(SpriteBatch batch) {
        animateRoll(batch);
    }

    @Override
    public void update(float delta) {

        move(delta);

    }



    public void move(float delta)
    {
        int i=0;

        float distanceX = WorldController.instance.getCurrentLevel().getPlayer().position.x - position.x;


        position.y += speed.y * delta;
        roll = speed.x/maxSpeed;


        //speed.x *= Math.cos(rotation);
        //speed.y *= Math.sin(rotation);

        position.x += speed.x * delta;
        position.y += speed.y * delta;
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
