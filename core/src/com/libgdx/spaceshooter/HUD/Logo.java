package com.libgdx.spaceshooter.HUD;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.spaceshooter.Assets;

public class Logo extends HUDElement {

    public Logo()
    {

        position = new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/1.3f);
        dimension = new Vector2(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()/2);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(Assets.getInstance().logo,position.x-dimension.x/2,position.y-dimension.y/2,dimension.x,dimension.y);
    }
}
