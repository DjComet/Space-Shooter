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

    public Explosion(float posX, float posY)
    {
        position = new Vector2(posX, posY);
        rotation = 0;

        width= 10;
        height=10;

        scale = new Vector2(1,1);
        layerTag = Layer.LayerNames.DEFAULT;
        explosion = Assets.getInstance().explosion;
    }

    @Override
    public void update(float delta) {
        for (GameObject enemy: WorldController.instance.getCurrentLevel().getLayerList(Layer.LayerNames.ENEMY))
        {
            if(Vector2.dst2(enemy.getCenterPos().x, enemy.getCenterPos().y, getCenterPos().x, getCenterPos().y)<=radius*radius)
            {
                //take enemy and tell him it's been hit.
            }
        }

        stateTime += delta;

        if(stateTime > 0.4f)
        {
            WorldController.instance.getCurrentLevel().Despawn(this);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {


        batch.draw(Assets.getInstance().explosion.getKeyFrame(stateTime),position.x,position.y,0, 0, width, height, scale.x, scale.y, rotation);
    }
}
