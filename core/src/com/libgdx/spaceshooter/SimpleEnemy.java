package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class SimpleEnemy extends GameObject {

    public float maxSpeed = 100f;
    public Vector2 speed;
    public float acceleration = 300f;
    public float roll = 0f;
    public boolean dead = false;
    boolean checked = false;

    float realTime = 0;
    float delay;
    float period = 1f;
    float amplitude = 2f;
    Vector2 direction;
    Vector2 shootingPosL;
    Vector2 shootingPosR;
    public float shotSpeed = 250f;
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

        shootingPosL = new Vector2(-2,4);
        shootingPosR = new Vector2( 8,4);

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
        System.out.println("Sine: "+ sine);


        if(sine <= -amplitude+0.05) direction.x  = 1;
        else if(sine >= amplitude-0.05) direction.x  = -1;


        float targetSpeed = maxSpeed * direction.x;
        float offsetSpeed = targetSpeed - speed.x;
        offsetSpeed = MathUtils.clamp(offsetSpeed, -acceleration * delta, acceleration * delta);
        speed.x += offsetSpeed;

        position.x += sine;
        position.y += speed.y * direction.y * delta;

        roll = -speed.x/maxSpeed;
        timer += delta;

        if(timer > timeToShoot)
        {
            shoot();
            timer = 0;
        }

        if(!checked)
        {
            System.out.println("Position checked: " +position.x + ", "+ position.y);
            checked = true;
        }

    }

    void checkHit()
    {
        if(rotation==0)
            rectangle.set(position.x, position.y, width*scale.x, height*scale.y);
        else rectangle.set(position.x-width*scale.x, position.y-height*scale.y, width,height);

        for (GameObject shot: WorldController.instance.getCurrentLevel().getLayerList(Layer.LayerNames.PLAYERSHOT))
        {
            Shot temp = (Shot)shot;//As we know that there are only Shot gameobjects in that layer, we can cast it like this without fear of trouble.
            if(CollisionHelper.CheckCollision(this, shot) && temp.shotType != ShotType.PLSPECIAL)
            {
                WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x - width/2 -32, position.y-height/2-32, false));
                WorldController.instance.getCurrentLevel().Despawn(shot);
                dead = true;
                WorldController.instance.getCurrentLevel().Despawn(this);
            }
        }
        for (GameObject explosion: WorldController.instance.getCurrentLevel().getLayerList(Layer.LayerNames.EXPLOSION))
        {
            Explosion exp = (Explosion) explosion;
            if(CollisionHelper.CheckCollision(this, exp))
            {
                if(exp.isBomb)
                {
                    WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x - width/2 -32, position.y-height/2-32, false));
                    dead = true;
                    WorldController.instance.getCurrentLevel().Despawn(this);
                    SoundManager.playSounds(7);
                }

            }
        }

    }

    void shoot() {

            WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.SE,position.x-width/2 + shootingPosR.x,position.y-height/2-shootingPosR.y, -shotSpeed, 1, 180));
            WorldController.instance.getCurrentLevel().Instantiate(new Shot(ShotType.SE,position.x-width/2 +shootingPosL.x,position.y-height/2-shootingPosL.y, -shotSpeed, 1,180));
            SoundManager.playSounds(6);

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
