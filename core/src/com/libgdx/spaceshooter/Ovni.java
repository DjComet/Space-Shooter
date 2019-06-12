package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Ovni extends GameObject {

    float dt;
    int health = 100;
    boolean isOnPos = false;

    //Shooting
    float innerRingRotation = 0f;
    float outerRingRotation = 0f;
    public Vector2[] normalShootPos;
    public Vector2[] laserShootPos;

    //Death
    boolean hit = false;
    int explosionNumber = 20;
    boolean dead = false;
    float explosionTimer =0f;
    int redFrameCounter = 0;


    public Ovni()
    {
        super();
        position.x = -WorldController.instance.getCurrentLevel().getBg().width/2;
        position.y = 200;
        width = 199f;
        height = 199f;

        normalShootPos = new Vector2[] {new Vector2(1,1)  /*.......*/   };//hacer aqui toa la mierda de poner las posiciones de los cañones

        layerTag = Layer.LayerNames.ENEMY;


        //rectangle.setCenter(0,0);

    }

    void checkHit()
    {
        if(hit)
        {
            redFrameCounter --;
            if(redFrameCounter<=0)
                hit = false;//make it last longer in red
        }

        rectangle.set(position.x +47,position.y+47,108,108);
        for (GameObject shot: WorldController.instance.getCurrentLevel().getLayerList(Layer.LayerNames.PLAYERSHOT))
        {
            if(CollisionHelper.CheckCollision(this, shot))
            {
                WorldController.instance.getCurrentLevel().Despawn(shot);
                health -= 1;//the damage of the shot is subtracted here
                hit = true;
                redFrameCounter = 3;

            }
        }
        for (GameObject explosion: WorldController.instance.getCurrentLevel().getLayerList(Layer.LayerNames.DEFAULT))
        {
            Explosion exp = (Explosion) explosion;
            if(CollisionHelper.CheckCollision(this, exp))
            {
                if(exp.isBomb)
                {
                    health -= 10;//the damage of the shot is subtracted here
                    hit = true;
                    redFrameCounter = 3;
                }

            }
        }
        if(health <= 0)
        {

            die();

        }
    }

    void die()
    {
        explosionTimer+= dt;

        if(explosionNumber<=0)
        {
            dead = true;
        }
        else if(explosionTimer >= 0.2f)
        {
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x+ 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            explosionNumber--;
            explosionTimer=0;
        }
        if(dead)
        {
            WorldController.instance.getCurrentLevel().Despawn(this);
        }
    }

    public Vector2 randomPos()
    {
        float max =  60;
        float min =  -60;
        Vector2 position = new Vector2(((float)Math.random() * ((max - min) + 1)) + min, ((float)Math.random() * ((max - min) + 1)) + min);
        System.out.println("Position provided: " + position + ", min: " + min + ", max: " + max);

        return position;
    }

    @Override
    public void update(float delta) {

        dt = delta;
        if(!isOnPos)
        {
            position.y -= 50*dt;
            if(position.y<= 20)
            {
                isOnPos=true;
                WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.OVNINORMAL,position.x+100-16,position.y+100-16,-50,1,0));
            }
        }
        innerRingRotation += dt *10;
        outerRingRotation -= dt * 20;
        checkHit();



    }

    @Override
    public void draw(SpriteBatch batch) {


        batch.draw(texRegionToDraw(0),position.x,position.y,100,100,width,height,scale.x,scale.y,outerRingRotation);
        batch.draw(texRegionToDraw(1),position.x,position.y,100,100,width,height,scale.x,scale.y,innerRingRotation);

        if(!hit)
        batch.draw(texRegionToDraw(2),position.x,position.y,100,100,width,height,scale.x,scale.y,rotation);
        else
        batch.draw(texRegionToDraw(3),position.x,position.y,100,100,width,height,scale.x,scale.y,rotation);

    }

    TextureRegion texRegionToDraw(int i)
    {
        return Assets.getInstance().ovniTexRegions[i];
    }

}
