package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player extends GameObject {
    public int lives = 5;
    public float maxSpeed = 30f;
    public float acceleration = 80f;
    public float roll = 0;
    public boolean dead = false;
    public Vector2 speed;
    public TextureAtlas texAtlas = Assets.getInstance().player;
    public TextureRegion[] texregs= Assets.getInstance().playerTexRegions;

    public Player(float posX, float posY) {
        position = new Vector2(posX, posY);
        rotation = 0;

        width= 10;
        height=10;

        scale = new Vector2(1,1);

        speed = Vector2.Zero;
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

        Vector2 targetSpeed = new Vector2(maxSpeed * horizontal, maxSpeed * vertical);
        Vector2 offsetSpeed = new Vector2(targetSpeed.x - speed.x, targetSpeed.y - speed.y);
        offsetSpeed.x = MathUtils.clamp(offsetSpeed.x, -acceleration * delta, acceleration * delta);
        offsetSpeed.y = MathUtils.clamp(offsetSpeed.y, -acceleration * delta, acceleration * delta);

        speed.x += offsetSpeed.x;
        speed.y += offsetSpeed.y;

        position.x += speed.x * delta;
        position.y += speed.y* delta;

        //Gdx.app.debug("speed: "+speed, ", position: "+position);
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
        else if(roll>0.2f && roll<=0.4f) //Right hand turn
        {
            i=5;
        }
        else if(roll>0.4)
        {
            i=6;
        }
        else if(roll<0 && roll>=-0.2f)
        {
            i=2;
        }
        else if(roll<-0.2f && roll>=-0.4f) //left hand turn
        {
            i=1;
        }
        else if(roll<-0.4)
        {
            i=0;
        }

        if(dead)
        {
            i=7;
        }

        batch.draw(texRegionToDraw(i),position.x,position.y,0,0,width,height,scale.x,scale.y,rotation);
        //batch.draw(Assets.getInstance().test,0,0,5,5);
        //batch.draw(texRegionToDraw(3),position.x,position.y,0,0,width,height,scale.x,scale.y,rotation);
    }

    TextureRegion texRegionToDraw(int i)
    {
        return Assets.getInstance().playerTexRegions[i];
    }
}
