package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class SimpleEnemy extends GameObject {

    public float maxSpeed = 50f;
    public Vector2 speed;
    public float acceleration = 300f;
    public float roll = 0f;
    public boolean dead = false;


    float realTime;
    float delay;
    float period = 2f;
    float amplitude = 1f;
    Vector2 direction;
    Vector2 shootingPosL;
    Vector2 shootingPosR;
    public float shotSpeed = 30f;
    float timer = 0f;
    float timeToShoot;

    public SimpleEnemy(float posX, float posY) {
        position = new Vector2(posX, posY);

        rotation = 180;
        direction = new Vector2(0, -1);
        width = 32;
        height = 32;

        scale = new Vector2(1,1);

        speed = new Vector2(0f, maxSpeed);

        shootingPosL = new Vector2(0.1f,2);
        shootingPosR = new Vector2( 1.2f,2);

        rectangle = new Rectangle();

        layerTag = Layer.LayerNames.ENEMY;
        timeToShoot =  1 + (float) Math.random() * 2;
        delay = (float) Math.random();


    }

    @Override
    public void draw(SpriteBatch batch) {
        animateRoll(batch);
    }

    @Override
    public void update(float delta) {

        move(delta);

        if(position.y < -WorldController.instance.getCurrentLevel().getBg().height/2) {
            WorldController.instance.getCurrentLevel().Despawn(this);
        }

        checkHit();
    }

    void move(float delta)
    {
        realTime += delta;
        realTime %= period;



        float sine = (float)Math.sin((realTime + delay) * 2 * MathUtils.PI/period) * amplitude;//Movimiento armónico simple

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
        timer += delta;
        if(timer > timeToShoot)
        {
            shoot();
            timer = 0;
        }

    }

    void checkHit()
    {
        for (GameObject shot: WorldController.instance.getCurrentLevel().getLayerList(Layer.LayerNames.PLAYERSHOT))
        {
            if(CollisionHelper.CheckCollision(this, shot))
            {
                WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x - width/2, position.y-height/2));
                WorldController.instance.getCurrentLevel().Despawn(shot);
                dead = true;
                WorldController.instance.getCurrentLevel().Despawn(this);
            }
        }

    }

    void shoot() {

            WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.SE,position.x-width/2,position.y-height/2-shootingPosR.y, -shotSpeed, 1, 180));
            WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.SE,position.x-(width/2) +2f,position.y-height/2-shootingPosL.y, -shotSpeed, 1,180));

    }

    void animateRoll(SpriteBatch batch)
    {
        int i = 0;

        if(dead)
        {
            i=7;
        }
        else if(roll == 0)//This is for precise control over the animation (allows for quick direction changes with proper frame correspondance)
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


        batch.draw(texRegionToDraw(i),position.x,position.y,0,0,width,height,scale.x,scale.y,rotation);
    }

    TextureRegion texRegionToDraw(int i)
    {
        return Assets.getInstance().seTexRegions[i];
    }
}
