package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Canyon extends Background {


    public Canyon(float posX, float posY, boolean flipped)
    {
        rotation = 0;

        width = 32;
        height = 400;

        position = new Vector2(posX, posY);
        speedY = -100f;

        if(flipped)
        {
            scale = new Vector2(-1,1);
        }
        else
        {
            scale = new Vector2(1,1);
        }
        tr = Assets.getInstance().tiledCanyon;

    }

    @Override
    public void update(float delta) {



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
