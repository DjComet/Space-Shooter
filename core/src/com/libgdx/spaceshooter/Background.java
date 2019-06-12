package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Background extends GameObject {

    public float speedX = 0;
    public float speedY = -200;
    TextureRegion tr;
    public Background()
    {
        rotation = 0;

        width = 200;
        height = 400;

        position = new Vector2(-width/2, -height/2);

        scale = new Vector2(1,1);
        tr = Assets.getInstance().tiledBg;


    }

    public void update(float delta)
    {





        int amountX = (int)(speedX*delta);

        int amountY = (int)(speedY*delta);

        tr.setRegionX(tr.getRegionX()+amountX);

        //after modifying the X coordinate, the width of the TextureRegion has shrunk (it has the same value as before the change) so we need to manually update it.

        tr.setRegionWidth(tr.getRegionWidth()+amountX);

        tr.setRegionY(tr.getRegionY()+amountY);

        tr.setRegionHeight(tr.getRegionHeight()+amountY);
    }

    public void draw(SpriteBatch batch)
    {

        batch.draw(Assets.getInstance().tiledBg,position.x,position.y,width,height);
    }


}
