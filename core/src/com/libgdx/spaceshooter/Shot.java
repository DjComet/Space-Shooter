package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Shot extends GameObject{
    public int damage;
    public Vector2 speed;

    public Shot (float posX, float posY, float speedX, float speedY, int dmg)
    {
        position = new Vector2(posX, posY);
        rotation = 0;

        //width = Assets.getInstance().textureRegions[0].getRegionWidth();
        //height = Assets.getInstance(.textureRegions[0].getRegionHeight();

        width = 1.0f;
        height = 1.0f;

        scale.x = 1.0f;
        scale.y = 1.0f;

        speed.x = speedX;
        speed.y = speedY;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(Assets.getInstance().shotTexRegions[0],position.x,position.y,0,0,width,height,scale.x,scale.y,rotation);
    }

    @Override
    public void update(float delta) {
        int directionX = 1;
        int directionY = 0;

        position.x += directionX * speed.x * delta;
        position.y += directionY * speed.y* delta;


    }
}
