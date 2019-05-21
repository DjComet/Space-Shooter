package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;


public class AdvancedEnemy extends GameObject {

    int lives = 6;
    public float maxSpeed = 20f;
    public float roll = 0f;
    public boolean dead = false;
    public float acceleration = 60f;
    public Vector2 direction = new Vector2(0,-1);

    public Vector2 speed;
    float distanceToStopFollow = 20f;
    float timesToShoot;
    float numberOfShots=0f;
    Vector2 shootingPosL;
    Vector2 shootingPosR;
    public float shotSpeed = 30f;
    boolean startShooting = false;
    int counter = 0;

    public AdvancedEnemy(float posX, float posY){
        position = new Vector2(posX, posY);

        rotation = 180;

        width = 8;
        height = 8;

        scale = new Vector2(1,1);

        speed = new Vector2(0f,maxSpeed/2);
        shootingPosL = new Vector2(0.1f,2);
        shootingPosR = new Vector2( 1.2f,2);

        rectangle = new Rectangle();
        timesToShoot =  10;

        tag = "ENEMY";
    }

    @Override
    public void draw(SpriteBatch batch) {
        animateRoll(batch);
    }

    @Override
    public void update(float delta) {

        move(delta);

        if (position.y < -WorldController.instance.getCurrentLevel().background.height / 2) {
            despawn();
        }
        checkHit();
    }

    void despawn()
    {

        Iterator<GameObject> iter = WorldController.instance.getCurrentLevel().enemyGos.iterator();
        while (iter.hasNext())
        {
            GameObject element = iter.next();
            if(element == this)
            {
                WorldController.instance.getCurrentLevel().enemyGos.remove(element);
                WorldController.instance.getCurrentLevel().refresh();
            }
        }

    }

    void checkHit()
    {
        for (GameObject shot: WorldController.instance.getCurrentLevel().playerShots)
        {
            if(CollisionHelper.CheckCollision(this, shot))
            {
                WorldController.instance.getCurrentLevel().playerShots.remove(shot);
               lives -= 1;//Make it so that the damage of the shot is substracteddddd here
               if(lives <= 0)
               {

                   die();

               }
            }
        }

    }

    void die()
    {
        WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x - width/2, position.y-height/2));
        despawn();
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
            //shoot();
            numberOfShots ++;
            counter = 0;
        }
        else if(counter<=20)
        {
            counter++;
        }


        checkHit();

    }

    void shoot() {

        WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.AE,position.x-width/2,position.y-height/2-shootingPosR.y, -shotSpeed, 1, 180));
        WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.AE,position.x-(width/2) +2f,position.y-height/2-shootingPosL.y, -shotSpeed, 1,180));

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
