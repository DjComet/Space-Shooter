package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class SimpleEnemy extends GameObject {

    public float maxSpeed = 5f;
    public Vector2 speed;
    public float roll = 0f;
    public boolean dead = false;

    float shotTimer = 0f;
    public float shotInterval = 0.5f;
    float realTime;
    float period = 5f;
    float amplitude = 3f;
    Vector2 direction = Vector2.Zero;

    public SimpleEnemy(float posX, float posY) {
        position = new Vector2(posX, posY);

        rotation = 180;
        direction = new Vector2(0, -1);
        width = 10;
        height = 10;

        scale = new Vector2(1,1);

        speed = new Vector2(1f, 3f);

        tag = "ENEMY";
    }

    @Override
    public void draw(SpriteBatch batch) {
        animateRoll(batch);
    }

    @Override
    public void update(float delta) {


        realTime += delta;
        realTime %= period;



        direction.x = (float)Math.sin(realTime * 2 * MathUtils.PI/period) * amplitude;

        direction.x = MathUtils.clamp(direction.x,-1, 1);





        position.x += speed.x * direction.x * delta;
        position.y += speed.y * direction.y * delta;

        roll = speed.x/maxSpeed;

    }

    void shoot(float delta) {
        shotTimer += delta;

        if(shotTimer>= shotInterval)
        {
            //WorldController.instance.level1.Instantiate(new Shot(se,0,0));
        }
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
        return Assets.getInstance().seTexRegions[i];
    }
}
