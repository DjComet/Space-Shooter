package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class SimpleEnemy extends GameObject {

    public float maxSpeed = 15f;
    public Vector2 speed;
    public float acceleration = 100f;
    public float roll = 0f;
    public boolean dead = false;

    float shotTimer = 0f;
    public float shotInterval = 1.5f;
    float realTime;
    float period = 1f;
    float amplitude = 1f;
    Vector2 direction;
    Vector2 shootingPosL;
    Vector2 shootingPosR;
    public float shotSpeed = 10f;

    public SimpleEnemy(float posX, float posY) {
        position = new Vector2(posX, posY);

        rotation = 180;
        direction = new Vector2(0, -1);
        width = 7;
        height = 7;

        scale = new Vector2(1,1);

        speed = new Vector2(0f, maxSpeed-5f);

        shootingPosL = new Vector2(-3,2);
        shootingPosR = new Vector2( 3,2);

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



        float sine = (float)Math.sin(realTime * 2 * MathUtils.PI/period) * amplitude;//Movimiento armónico simple

        if(sine<0) direction.x  = -1;
        else if(sine>0) direction.x  = 1;
        else direction.x  = 0;

        float targetSpeed = maxSpeed * direction.x;
        float offsetSpeed = targetSpeed - speed.x;
        offsetSpeed = MathUtils.clamp(offsetSpeed, -acceleration * delta, acceleration * delta);
        speed.x += offsetSpeed;

        position.x += speed.x * delta;
        position.y += speed.y * direction.y * delta;

        roll = -speed.x/maxSpeed;
        //shoot(delta);
    }

    void shoot(float delta) {
        shotTimer += delta;

        if(shotTimer>= shotInterval)
        {
            WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.SE,position.x+width/2+shootingPosR.x,position.y+width/2+shootingPosR.y, shotSpeed, 1, 180));
            WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.SE,position.x+width/2+shootingPosL.x,position.y+width/2+shootingPosL.y, shotSpeed, 1,180));

        }
    }

    void animateRoll(SpriteBatch batch)
    {
        int i = 0;

        if(roll ==0)//This is for precise control over the animation (allows for quick direction changes with proper frame correspondance)
        {
            i = 3;
            shootingPosL = new Vector2(-3,2);
            shootingPosR = new Vector2( 3,2);
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
