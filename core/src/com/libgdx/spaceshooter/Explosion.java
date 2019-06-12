package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Explosion extends GameObject{

    public int damage = 60;
    public float radius = 7.5f;
    public Animation<TextureRegion> explosion;
    float stateTime = 0f;
    boolean isBomb = false;

    public Explosion(float posX, float posY, boolean bomb)
    {
        position = new Vector2(posX, posY);
        rotation = 0;

        width = 64;
        height= 64;

        scale = new Vector2(1,1);
        layerTag = Layer.LayerNames.EXPLOSION;
        explosion = Assets.getInstance().explosion;
        isBomb = bomb;

    }

    @Override
    public void update(float delta) {

        stateTime += delta;

        if(stateTime > 0.4f)
        {
            WorldController.instance.getCurrentLevel().Despawn(this);
        }
        rectangle.set(position.x,position.y,width,height);
    }

    @Override
    public void draw(SpriteBatch batch) {


        batch.draw(Assets.getInstance().explosion.getKeyFrame(stateTime),position.x,position.y,0, 0, width, height, scale.x, scale.y, rotation);
    }
}
