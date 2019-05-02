package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Background extends GameObject {

    public Background()
    {
        rotation = 0;

        width= 100;
        height=100;

        position = new Vector2(-width/2, -height/2);

        scale = new Vector2(1,1);



    }

    public void update(float delta)
    {

    }

    public void draw(SpriteBatch batch)
    {
        batch.draw(Assets.getInstance().bg,position.x,position.y,width,height);
    }


}
