package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

public class Ovni extends GameObject {

    float dt;
    int health;
    int maxHealth = 10;
    boolean isOnPos = false;


    //Shooting
    Vector2 ovniCenterPos;
    float innerRingRotation = 0f;
    float outerRingRotation = 0f;
    Vector2 originNormalShootPos;
    Vector2 originLaserShootPos;
    public Vector2[] normalShootPos;
    public Vector2[] laserShootPos;
    float normalShotInterval = 0.5f;
    float normalShotTimer = 0f;
    float laserShotInterval = 0.1f;
    float laserShotTimer = 0f;
    boolean reverse = false;

    //Death
    boolean quarter3 = false;
    boolean quarter2 = false;
    boolean quarter1 = false;
    boolean permissionToDieALittle = false;

    boolean hit = false;
    int explosionNumber = 20;
    int milestoneExplosionNumber = 10;
    boolean dead = false;
    boolean dying = false;
    float explosionTimer =0f;
    int redFrameCounter = 0;

    public enum OvniState {IDLE, NORMAL, LASER};
    OvniState state;
    boolean hasDoneNormal = false;

    float st_normalTime = 15f;
    float st_laserTime = 10f;
    float st_idleTime = 3f;
    float st_normalTimer = 0;
    float st_laserTimer = 0;
    float st_idleTimer = 0;



    public Ovni()
    {
        super();
        width = 199f;
        height = 199f;
        position.x = -width/2;
        position.y = 200;
        ovniCenterPos = new Vector2(position.x + width/2, position.y + height/2);
        layerTag = Layer.LayerNames.ENEMY;
        health = maxHealth;
        state = OvniState.NORMAL;

        if(MAIN_GAME.instance.gameScreen.twoPlayers) health *=2;



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

        explosionTimer+= dt;
        dying = true;
        SoundManager.principalTheme.stop();
        if(!SoundManager.victorySong.isPlaying() && explosionNumber<=10 )
        {
            SoundManager.playVictoryMusic();
        }

        if(explosionNumber<=0)
        {
            dead = true;
        }
        else if(explosionTimer >= 0.2f)
        {
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x + 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x + 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x + 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x + 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x + 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x + 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x + 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x + 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            SoundManager.playSounds(13);
            explosionNumber--;
            scale = scale.scl(0.98f);
            explosionTimer=0;
        }
        if(dead)
        {
            SoundManager.playSounds(14);
            System.out.println("Game WON!!");
            WorldController.instance.getCurrentLevel().gameWon = true;
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
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x + 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x + 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x + 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x + 100 + randomPos().x-32, position.y+ 100 + randomPos().y-32, false));
            SoundManager.playSounds(12);
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

    void moveShootPositions()
    {

        /* = new Vector2[]{new Vector2(ovniCenterPos.x - 8, ovniCenterPos.y -8 -97),
                                             new Vector2(ovniCenterPos.x - 8, ovniCenterPos.y -8 -97),
                                             new Vector2(ovniCenterPos.x - 8, ovniCenterPos.y -8 -97),
                                             new Vector2(ovniCenterPos.x - 8, ovniCenterPos.y -8 -97),
                                             new Vector2(ovniCenterPos.x - 8, ovniCenterPos.y -8 -97),
                                             new Vector2(ovniCenterPos.x - 8, ovniCenterPos.y -8 -97),
                                             new Vector2(ovniCenterPos.x - 8, ovniCenterPos.y -8 -97),
                                             new Vector2(ovniCenterPos.x - 8, ovniCenterPos.y -8 -97)};*/
        originNormalShootPos = new Vector2(ovniCenterPos.x - 8, ovniCenterPos.y -8 -97);
        originLaserShootPos = new Vector2(ovniCenterPos.x - 4, ovniCenterPos.y -4 -60);


        normalShootPos = getPositionArray(originNormalShootPos,false);

        laserShootPos = getPositionArray(originLaserShootPos,true);

    }
    Vector2[] getPositionArray(Vector2 originalPosition, boolean internal)//ONLY LENGTH 8 ARRAYS!
    {
        Vector2[] temp = new Vector2[8];
        if(internal)
        {
            for(int i = 0; i<temp.length; i++)
            {
                temp[i] = getRotatedPos(angleToRads(innerRingRotation + (45 * i)), originalPosition);
            }
        }
        else
        {
            for(int i = 0; i<temp.length; i++)
            {
                temp[i] = getRotatedPos(angleToRads(outerRingRotation + (45 * i)), originalPosition);
            }
        }

        return  temp;
    }

    Vector2 getRotatedPos(float angle, Vector2 position)
    {
        float rotatedX = (float) (Math.cos(angle) * (position.x - ovniCenterPos.x) - Math.sin(angle) * (position.y - ovniCenterPos.y) + ovniCenterPos.x);
        float rotatedY = (float) (Math.sin(angle) * (position.x - ovniCenterPos.x) + Math.cos(angle) * (position.y - ovniCenterPos.y) + ovniCenterPos.y);

        return new Vector2(rotatedX, rotatedY);
    }

    void shoot()
    {
        //Add timed switch between lasers and normal shots
        switch (state)
        {
            case NORMAL:
                st_normalTimer += dt;
                if(st_normalTimer < st_normalTime)
                {
                    normalShotTimer += dt;
                    if (normalShotTimer >= normalShotInterval)
                    {
                        for (int i = 0; i < normalShootPos.length; i++)
                        {
                            WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.OVNINORMAL, normalShootPos[i].x, normalShootPos[i].y, 50, 1, outerRingRotation + (45 * i)));

                            normalShotTimer = 0;
                        }
                        SoundManager.playSounds(11);

                    }
                    hasDoneNormal = true;
                }
                else
                {
                    state = OvniState.IDLE;
                    st_normalTimer = 0;
                }
                break;

            case LASER:
                if(st_laserTimer==0)
                SoundManager.playSounds(10);
                st_laserTimer += dt;
                if(st_laserTimer < st_laserTime)
                {

                    laserShotTimer += dt;
                    if (laserShotTimer >= laserShotInterval)
                    {
                        for (int i = 0; i < laserShootPos.length; i++)
                        {
                            WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.OVNILASER, laserShootPos[i].x, laserShootPos[i].y, 70, 1, innerRingRotation + (45 * i)));
                            laserShotTimer = 0;
                        }
                    }
                    hasDoneNormal = false;
                }
                else
                {
                    state = OvniState.IDLE;
                    st_laserTimer = 0;
                    reverse = !reverse;
                    SoundManager.stopLaser();
                }
                break;

            case IDLE:
                st_idleTimer+=dt;
                if(st_idleTimer >= st_idleTime)
                {
                    if(hasDoneNormal)
                    {
                        state = OvniState.LASER;
                    }
                    else
                    {
                        state = OvniState.NORMAL;
                    }
                    st_idleTimer = 0;
                }
                break;
        }






        //Same for lasers
    }

    float angleToRads(float angle)
    {
        return (float) ((angle) * (Math.PI/180));
    }

    @Override
    public void update(float delta) {

        dt = delta;

        innerRingRotation += dt *10 *(reverse? -1 :1);
        outerRingRotation -= dt * 20 * (reverse? -1 :1);
        ovniCenterPos = new Vector2(position.x + width/2, position.y + height/2);
        moveShootPositions();
        if(!isOnPos)
        {
            position.y -= 50*dt;
            if(position.y <= 20)
            {
                isOnPos=true;

            }
        }
        else
        {
            if(!dying)
            shoot();
        }


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
