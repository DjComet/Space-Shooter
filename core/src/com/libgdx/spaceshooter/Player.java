package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {
    public int lives = 5;
    public float maxSpeed = 5f;
    public float roll = 0;
    public boolean dead = false;
    public Vector2 speed;

    public Player(float posX, float posY) {
        position = new Vector2(posX, posY);
        rotation = 0;


        width = Assets.getInstance().playerTexRegions[3].getRegionWidth();
        height = Assets.getInstance().playerTexRegions[3].getRegionHeight();

        scale.x = 1.0f;
        scale.y = 1.0f;

        speed.x = 1.0f;
        speed.y = 1.0f;
    }

    @Override
    public void draw(SpriteBatch batch) {
        animateRoll(batch);
    }

    @Override
    public void update(float delta) {
        int horizontal = 0;
        int vertical = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.A)) horizontal = -1;
        else if(Gdx.input.isKeyPressed(Input.Keys.D)) horizontal = 1;
        else horizontal = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.W)) vertical = 1;
        else if(Gdx.input.isKeyPressed(Input.Keys.S)) vertical = -1;
        else vertical = 0;

        //doAcceleration

        position.x += horizontal * speed.x * delta;
        position.y += vertical * speed.y* delta;

        roll = speed.x/ maxSpeed;


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
        return Assets.getInstance().playerTexRegions[i];
    }
}
