package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SimpleEnemy extends GameObject {

    public float maxSpeed = 5f;
    public Vector2 speed;
    public float roll = 0f;
    public boolean dead = false;
    private Texture texture;


    public SimpleEnemy(float posX, float posY) {
        position = new Vector2(posX, posY);

        rotation = 180;

        width = Assets.getInstance().seTexRegions[0].getRegionWidth();
        height = Assets.getInstance().seTexRegions[0].getRegionHeight();

        scale.x = 1.0f;
        scale.y = 1.0f;

        speed.x = 1.0f;
        speed.y = 1.0f;
        texture = new Texture(Gdx.files.internal("SimpleEnemy.png"));
    }

    @Override
    public void draw(SpriteBatch batch) {
        animateRoll(batch);
    }

    @Override
    public void update(float delta) {
        Vector2 direction = new Vector2(0, 1);

        position.x += direction.x * speed.x * delta;
        position.y += direction.y * speed.y * delta;

        roll = speed.x/maxSpeed;

    }

    void shoot() {

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
        return Assets.getInstance().playerTexRegions[i];
    }
}
