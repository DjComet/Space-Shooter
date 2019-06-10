package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Canyon extends GameObject {

    public Canyon(float posX, float posY, boolean flipped)
    {
        rotation = 0;

        width = 32;
        height = 400;

        position = new Vector2(posX, posY);


        if(flipped)
        {
            scale = new Vector2(-1,1);
        }
        else
        {
            scale = new Vector2(1,1);
        }


    }

    @Override
    public void update(float delta) {
        TextureRegion tr = Assets.getInstance().tiledCanyon;
        float speedX = 0;

        float speedY = -100;

        int amountX = (int)(speedX*delta);

        int amountY = (int)(speedY*delta);

        tr.setRegionX(tr.getRegionX()+amountX);

        //after modifying the X coordinate, the width of the TextureRegion has shrunk (it has the same value as before the change) so we need to manually update it.

        tr.setRegionWidth(tr.getRegionWidth()+amountX);

        tr.setRegionY(tr.getRegionY()+amountY);

        tr.setRegionHeight(tr.getRegionHeight()+amountY);
    }

    @Override
    public void draw(SpriteBatch batch) {

        batch.draw(Assets.getInstance().tiledCanyon,position.x,position.y,width/2, height/2, width,height, scale.x, scale.y, rotation);
    }
}
