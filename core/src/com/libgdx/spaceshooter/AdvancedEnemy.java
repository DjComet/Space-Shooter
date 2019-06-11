package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class AdvancedEnemy extends GameObject {

    int lives = 6;
    public float maxSpeed = 100f;
    public float roll = 0f;
    public boolean dead = false;
    public float acceleration = 300f;
    public Vector2 direction = new Vector2(0,-1);

    public Vector2 speed;
    float distanceToStopFollow = 200f;
    float timesToShoot;
    float numberOfShots=0f;
    Vector2 shootingPosL;
    Vector2 shootingPosR;
    public float shotSpeed = 300f;
    boolean startShooting = false;
    int counter = 0;

    public AdvancedEnemy(float posX, float posY){
        position = new Vector2(posX, posY);

        rotation = 180;

        width = 32;
        height = 32;

        scale = new Vector2(1.2f,1.2f);

        speed = new Vector2(0f,maxSpeed/2);
        shootingPosL = new Vector2(-3,2);
        shootingPosR = new Vector2( 8f,2);

        rectangle = new Rectangle();
        timesToShoot =  10;

        layerTag = Layer.LayerNames.ENEMY;
    }

    @Override
    public void draw(SpriteBatch batch) {
        animateRoll(batch);
    }

    @Override
    public void update(float delta) {

        move(delta);

        if (position.y < -WorldController.instance.getCurrentLevel().getBg().height / 2) {
            WorldController.instance.getCurrentLevel().Despawn(this);
        }
        checkHit();
    }

    void checkHit()
    {
        for (GameObject shot: WorldController.instance.getCurrentLevel().getLayerList(Layer.LayerNames.PLAYERSHOT))
        {
            if(CollisionHelper.CheckCollision(this, shot))
            {
                WorldController.instance.getCurrentLevel().Despawn(shot);
               lives -= 1;//Make it so that the damage of the shot is subtracted here

            }
        }
        if(lives <= 0)
        {

            die();

        }
    }

    void die()
    {
        WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x - width/2-32, position.y-height/2-32));
        dead = true;
        WorldController.instance.getCurrentLevel().Despawn(this);
    }

    public void move(float delta)
    {


        float distanceX = (WorldController.instance.getCurrentLevel().getPlayer().position.x + WorldController.instance.getCurrentLevel().getPlayer().width/2) - (position.x - width/2);
        float distanceY = (WorldController.instance.getCurrentLevel().getPlayer().position.y + WorldController.instance.getCurrentLevel().getPlayer().height/2) - (position.y - height/2);

        if(distanceX < -0.5) direction.x  = -1;
        else if(distanceX > 0.5) direction.x  = 1;
        else direction.x  = 0;

        if(startShooting) direction.x = 0;

        float targetSpeed = maxSpeed * direction.x;
        float offsetSpeed = targetSpeed - speed.x;
        offsetSpeed = MathUtils.clamp(offsetSpeed, -acceleration * delta, acceleration * delta);
        speed.x += offsetSpeed;



        position.x += speed.x * delta;
        position.y += speed.y * direction.y * delta;

        roll = -speed.x/maxSpeed;

        if(Math.abs(distanceY) <= distanceToStopFollow && !startShooting)
        {
            startShooting = true;
        }

        if(startShooting && numberOfShots < timesToShoot && counter > 20)
        {
            shoot();
            numberOfShots ++;
            counter = 0;
        }
        else if(counter<=20)
        {
            counter++;
        }




    }

    void shoot() {

        WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.AE,position.x-width/2 + shootingPosR.x,position.y-height/2-shootingPosR.y, -shotSpeed, 1, 180));
        WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.AE,position.x-(width/2) + shootingPosL.x,position.y-height/2-shootingPosL.y, -shotSpeed, 1,180));

    }

    void animateRoll(SpriteBatch batch)
    {
        int i = 0;
        if(dead)
        {
            i=7;
        }
        else if(roll ==0)//This is for precise control over the animation (allows for quick direction changes with proper frame correspondance)
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



        batch.draw(texRegionToDraw(i),position.x,position.y,0,0,width,height,scale.x,scale.y,rotation);
    }

    TextureRegion texRegionToDraw(int i)
    {
        return Assets.getInstance().aeTexRegions[i];
    }
}
