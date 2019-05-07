package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    }

    public void draw(SpriteBatch batch)
    {

        batch.draw(Assets.getInstance().tiledBg,position.x,position.y,width,height);
    }


}
