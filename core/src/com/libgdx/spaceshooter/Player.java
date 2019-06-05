package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Player extends GameObject {
    public int lives = 5;
    public float maxSpeed = 20f;
    public float acceleration = 80f;
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
    public float shotSpeed = 40f;

    public Player(float posX, float posY) {
        position = new Vector2(posX, posY);
        rotation = 0;

        width= 7;
        height=7;

        scale = new Vector2(1,1);

        speed = new Vector2(0f,0f);
        layerTag = Layer.LayerNames.PLAYER;
        rectangle = new Rectangle();

        shootingPosL = new Vector2(-1.2f,2);
        shootingPosR = new Vector2( 1.2f,2);


    }

    @Override
    public void draw(SpriteBatch batch) {
        animateRoll(batch);
    }

    @Override
    public void update(float delta) {

        move(delta);
        shoot(delta);
        //checkHit();

    }

    void move(float delta)
    {
        int horizontal = 0;
        int vertical = 0;
        bg = WorldController.instance.getCurrentLevel().getBg();
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
        position.y += speed.y * delta;

        position.x = MathUtils.clamp(position.x, -bg.width/2 , bg.width/2 - width );
        position.y = MathUtils.clamp(position.y, -bg.height/2.13f, bg.height/2.13f - height);

        //Gdx.app.debug("speed: "+speed, ", position: "+position);
        roll = speed.x/maxSpeed;
    }
    void shoot(float delta) {
        shotTimer += delta;
        specialShotTimer += delta;

        if((WorldController.instance.inputMgr.keyDown(Input.Keys.SPACE) || Gdx.input.isKeyPressed(Input.Buttons.LEFT))  && shotTimer>= shotInterval)
        {
            WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.PLNORMAL,position.x+width/2+shootingPosR.x,position.y+height/2+shootingPosR.y, shotSpeed, 1,0));
            WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.PLNORMAL,position.x+width/2+shootingPosL.x,position.y+height/2+shootingPosL.y, shotSpeed, 1, 0));
            shotTimer = 0f;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && specialShotTimer>= specialShotInterval)
        {
            WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.PLSPECIAL,position.x+width/2+shootingPosL.x+0.2f,position.y+width/2, shotSpeed/4, 5,0));

            specialShotTimer = 0f;
        }
    }

    void checkHit()
    {
        for (GameObject shot: WorldController.instance.getCurrentLevel().getLayerList(Layer.LayerNames.ENEMYSHOT))
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
        WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x - width/2, position.y-height/2));
        dead = true;
        //insert replay pop up logic
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



        batch.draw(texRegionToDraw(i),position.x,position.y,0,0,width,height,scale.x,scale.y,rotation);
        //batch.draw(Assets.getInstance().test,0,0,5,5);
        //batch.draw(texRegionToDraw(3),position.x,position.y,0,0,width,height,scale.x,scale.y,rotation);
    }

    TextureRegion texRegionToDraw(int i)
    {
        return Assets.getInstance().playerTexRegions[i];
    }
}
