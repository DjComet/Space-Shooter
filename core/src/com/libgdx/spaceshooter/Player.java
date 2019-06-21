package com.libgdx.spaceshooter;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Player extends GameObject {
    public int health;
    public int maxHealth = 10;
    public int lives;
    public int maxLives = 3;
    public float respawnTime = 1.5f;
    float timerToRespawn = 0f;
    float invencibilityTimer = 0f;
    public float maxSpeed = 150f;
    public float acceleration = 500f;
    public float roll = 0;
    public boolean dead = false;
    private Vector2 speed;
    public GameObject bg;

    float shotTimer = 0f;
    float specialShotTimer = 0f;
    public float shotInterval = 0.3f;
    public float specialShotInterval = 1f;
    Vector2 shootingPosL;
    Vector2 shootingPosR;
    public float shotSpeed = 200f;

    float accelX = 0;
    float accelY = 0;
    boolean secondPlayer = false;

    InputManager InputMgr;
    float dt;
    float renderStrobeTimer = 0;
    boolean render = true;


    public Player(float posX, float posY) {
        position = new Vector2(posX, posY);
        rotation = 0;

        width= 32;
        height=32;

        scale = new Vector2(1,1);

        speed = new Vector2(0f,0f);
        layerTag = Layer.LayerNames.PLAYER;
        rectangle = new Rectangle();

        shootingPosL = new Vector2(-9.8f,3);
        shootingPosR = new Vector2( 1.3f,3);
        InputMgr = WorldController.instance.inputMgr;
        lives = maxLives;
        health = maxHealth;


    }

    @Override
    public void draw(SpriteBatch batch) {
        animateRoll(batch);
    }

    @Override
    public void update(float delta) {

        dt = delta;
        if(!dead)
        {
            move(delta);
            shoot(delta);
        }
            checkHit();


    }

    void move(float delta)
    {
        int horizontal = 0;
        int vertical = 0;
        bg = WorldController.instance.getCurrentLevel().getBg();
        if(Gdx.app.getType() == Application.ApplicationType.Desktop)
        {
            if(!secondPlayer)
            {
                if (InputMgr.keyLeft) horizontal = -1;
                else if (InputMgr.keyRight) horizontal = 1;
                else horizontal = 0;

                if (InputMgr.keyUp) vertical = 1;
                else if (InputMgr.keyDown) vertical = -1;
                else vertical = 0;
            }
            else
            {
                if (InputMgr.keyLeftP2) horizontal = -1;
                else if (InputMgr.keyRightP2) horizontal = 1;
                else horizontal = 0;

                if (InputMgr.keyUpP2) vertical = 1;
                else if (InputMgr.keyDownP2) vertical = -1;
                else vertical = 0;
            }

        }

        if(Gdx.app.getType() == Application.ApplicationType.Android)
        {
            accelX = -Gdx.input.getAccelerometerX();
            accelY = -Gdx.input.getAccelerometerY();
            if(accelX > 0.7) horizontal = 1;
            else if(accelX < -0.7) horizontal = -1;
            else horizontal = 0;

            if(accelY < -6) vertical = -1;
            else if(accelY > -5) vertical = 1;
            else vertical = 0;


        }



        Vector2 targetSpeed = new Vector2(maxSpeed * horizontal, maxSpeed * vertical);
        Vector2 offsetSpeed = new Vector2(targetSpeed.x - speed.x, targetSpeed.y - speed.y);
        offsetSpeed.x = MathUtils.clamp(offsetSpeed.x, -acceleration * delta, acceleration * delta);
        offsetSpeed.y = MathUtils.clamp(offsetSpeed.y, -acceleration * delta, acceleration * delta);

        speed.x += offsetSpeed.x;
        speed.y += offsetSpeed.y;




        position.x += speed.x * delta;
        position.y += speed.y * delta;


        position.x = MathUtils.clamp(position.x, -bg.width/2 , bg.width/2 - width );
        position.y = MathUtils.clamp(position.y, -bg.height/2.13f, bg.height/2.13f - height);
        if(WorldController.instance.currentLevel==4)
        {
            position.y = MathUtils.clamp(position.y, -bg.height/2.13f, bg.height/8 - height);
        }

        roll = speed.x/maxSpeed;
    }
    void shoot(float delta) {
        shotTimer += delta;
        specialShotTimer += delta;

        boolean touchShootNormal = InputMgr.pointBut!=null && InputMgr.pointBut.x>100;
        boolean touchShootSpecial = InputMgr.pointBut!=null && InputMgr.pointBut.x<100;//arreglar





        if(Gdx.app.getType() == Application.ApplicationType.Android)
        {
            if (touchShootNormal)
            {
                spawnNormalShot();
            }
            else if (touchShootSpecial)
            {
                spawnSpecialShot();
            }
        }
        else
        {
            if(!secondPlayer)
            {
                if (InputMgr.keyShootN)
                {
                     spawnNormalShot();
                }
                else if(InputMgr.keyShootS)
                {
                    spawnSpecialShot();
                }
            }
            else
            {
                if ((InputMgr.keyShootNP2 || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) && shotTimer >= shotInterval)
                {
                    spawnNormalShot();
                }
                else if ((InputMgr.keyShootSP2 || Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) && specialShotTimer >= specialShotInterval)
                {
                    spawnSpecialShot();
                }
            }
        }

    }

    void spawnNormalShot()
    {
        if(shotTimer >= shotInterval)
        {
            WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.PLNORMAL, position.x + width / 2 + shootingPosR.x, position.y + height / 2 + shootingPosR.y, shotSpeed, 1, 0));
            WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.PLNORMAL, position.x + width / 2 + shootingPosL.x, position.y + height / 2 + shootingPosL.y, shotSpeed, 1, 0));
            SoundManager.playSounds(1);
            shotTimer = 0f;
        }

    }
    void spawnSpecialShot()
    {
        if(specialShotTimer >= specialShotInterval)
        {
            WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.PLSPECIAL, position.x + width / 2 + shootingPosL.x - 2, position.y + width / 2, shotSpeed / 4, 5, 0));
            SoundManager.playSounds(2);
            specialShotTimer = 0f;
        }
    }

    void checkHit()
    {

        rectangle.set(position.x+9, position.y+9, width*scale.x -18, height*scale.y-18);

        invencibilityTimer -= dt;

        if(!dead && invencibilityTimer> 0)
        {
            renderStrobeTimer += dt;
            if(renderStrobeTimer>= 0.1f)
            {
                render = !render;
                renderStrobeTimer = 0;
            }

        }
        else if(!dead && invencibilityTimer<= 0)
        {
            render = true;
            renderStrobeTimer = 0;
        }

        for (GameObject shot: WorldController.instance.getCurrentLevel().getLayerList(Layer.LayerNames.ENEMYSHOT))
        {
            Shot temp = (Shot) shot;
            if(CollisionHelper.CheckCollision(this, shot))
            {
                WorldController.instance.getCurrentLevel().Despawn(shot);
                if(!dead && invencibilityTimer<= 0)
                {
                    health -= temp.damage;//Make it so that the damage of the shot is subtracted here
                    health = MathUtils.clamp(health, 0, maxHealth);
                    SoundManager.playSounds(15);
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
        if(!dead)
        {
            WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x - width/2, position.y-height/2, false));
            SoundManager.playSounds(3);
            lives --;
            SoundManager.playSounds(16);
            System.out.println("Lives: "+ lives);
        }
        dead = true;

        timerToRespawn += dt;

        if(timerToRespawn >= 0.5f)
        {
            render = false;
            position = new Vector2(-16,-WorldController.instance.getCurrentLevel().getBg().height/4);
        }

        if(timerToRespawn >= respawnTime)
        {
            health = maxHealth;
            SoundManager.playSounds(5);
            dead = false;
            timerToRespawn = 0;
            invencibilityTimer = 3f;
            render = true;
            if(lives <= 0)
            {
                WorldController.instance.getCurrentLevel().Despawn(this);
                //insert replay pop up logic
            }

        }
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
        }
        else if(roll > 0 && roll <= 0.2f)
        {
            i=4;
        }
        else if(roll > 0.2f && roll <= 0.4f) //Right hand turn
        {
            i=5;
        }
        else if(roll > 0.4)
        {
            i=6;
        }
        else if(roll < 0  && roll >= -0.2f)
        {
            i=2;
        }
        else if(roll < -0.2f && roll >= -0.4f) //left hand turn
        {
            i=1;
        }
        else if(roll < -0.4)
        {
            i=0;
        }


        if(render)
        {
            if(!secondPlayer)
            batch.draw(texRegionToDraw(i),position.x,position.y,0,0,width,height,scale.x,scale.y,rotation);
            else
            batch.draw(texRegionToDraw(i+8),position.x,position.y,0,0,width,height,scale.x,scale.y,rotation);

        }


    }

    TextureRegion texRegionToDraw(int i)
    {
        return Assets.getInstance().playerTexRegions[i];
    }
}
