package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Ovni extends GameObject {

    float dt;
    int health;
    int maxHealth = 100;
    boolean isOnPos = false;

    //Shooting
    float innerRingRotation = 0f;
    float outerRingRotation = 0f;
    public Vector2[] normalShootPos;
    public Vector2[] laserShootPos;

    //Death
    boolean quarter3 = false;
    boolean quarter2 = false;
    boolean quarter1 = false;
    boolean permissionToDieALittle = false;

    boolean hit = false;
    int explosionNumber = 20;
    int milestoneExplosionNumber = 10;
    boolean dead = false;
    float explosionTimer =0f;
    int redFrameCounter = 0;


    public Ovni()
    {
        super();
        width = 199f;
        height = 199f;
        position.x = -width/2;
        position.y = 200;

        normalShootPos = new Vector2[] {new Vector2(1,1)  /*.......*/   };//hacer aqui toa la mierda de poner las posiciones de los cañones

        layerTag = Layer.LayerNames.ENEMY;
        health = maxHealth;



    }

    void checkHit()
    {
        rectangle.set(position.x +47,position.y+47,108,108);
        if(hit)
        {
            redFrameCounter --;
            if(redFrameCounter<=0)
                hit = false;//make it last longer in red
        }

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

        for (GameObject explosion: WorldController.instance.getCurrentLevel().getLayerList(Layer.LayerNames.EXPLOSION))
        {
            Explosion exp = (Explosion) explosion;
            if(CollisionHelper.CheckCollision(this, exp))
            {
                if(exp.isBomb)
                {
                    exp.isBomb = false;
                    health -= 10;//the damage of the shot is subtracted here
                    System.out.println("bomb: health left = " + health);
                    hit = true;
                    redFrameCounter = 3;
                    break;
                }
            }
        }

        if(health <= (maxHealth * 0.75) && !quarter3)
        {
            milestoneExplosionNumber = 4;
            System.out.println("hola?");
            permissionToDieALittle = true;
            quarter3 = true;
        }
        if(health <= maxHealth * 0.5 && !quarter2)
        {
            milestoneExplosionNumber = 4;
            permissionToDieALittle = true;
            quarter2 = true;
        }
        if(health <= maxHealth * 0.25 && !quarter1)
        {
            milestoneExplosionNumber = 4;
            permissionToDieALittle = true;
            quarter1 = true;
        }

        if(permissionToDieALittle) {permissionToDieALittle = dieALittle();}

        if(health <= 0)
        {
            die();
        }
    }

    void die()
    {
        System.out.println("Ovni dying");
        explosionTimer+= dt;

        if(explosionNumber<=0)
        {
            dead = true;
        }
        else if(explosionTimer >= 0.2f)
        {
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x + 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            explosionNumber--;
            scale = scale.scl(0.98f);
            explosionTimer=0;
        }
        if(dead)
        {
            WorldController.instance.getCurrentLevel().Despawn(this);
        }
    }

    boolean dieALittle()
    {
        System.out.println("Ovni dying a little");
        explosionTimer+= dt;

        if(milestoneExplosionNumber<=0)
        {
            return false;
        }
        else if(explosionTimer >= 0.3f)
        {
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x+ 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            milestoneExplosionNumber--;

            explosionTimer=0;
        }
        return true;
    }

    public Vector2 randomPos()
    {
        float max =  60;
        float min =  -60;
        Vector2 position = new Vector2(((float)Math.random() * ((max - min) + 1)) + min, ((float)Math.random() * ((max - min) + 1)) + min);


        return position;
    }

    @Override
    public void update(float delta) {

        dt = delta;
        if(!isOnPos)
        {
            position.y -= 50*dt;
            if(position.y <= 20)
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
