package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Background extends GameObject {

    public Background()
    {
        rotation = 0;

        width= 200;
        height=1000;

        position = new Vector2(-width/2, -height/2);

        scale = new Vector2(1,1);



    }

    public void update(float delta)
    {
        TextureRegion tr = Assets.getInstance().tiledBg;
        float speedX = 0;

        float speedY = -200;

        int amountX = (int)(speedX*delta);

        int amountY = (int)(speedY*delta);

        tr.setRegionX(tr.getRegionX()+amountX);

        //after modifying the X coordinate, the width of the TextureRegion has shrink (it has the same value as before the change) so we need to manually update it.

        tr.setRegionWidth(tr.getRegionWidth()+amountX);

        tr.setRegionY(tr.getRegionY()+amountY);

        tr.setRegionHeight(tr.getRegionHeight()+amountY);
    }

    public void draw(SpriteBatch batch)
    {

        batch.draw(Assets.getInstance().tiledBg,position.x,position.y,width,height);
    }


}
