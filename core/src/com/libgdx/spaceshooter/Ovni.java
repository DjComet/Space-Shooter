package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Ovni extends GameObject {


    int lives = 100;

    float innerRingRotation = 0f;
    float outerRingRotation = 0f;
    public Vector2[] shootingPositions;

    public Ovni()
    {
        super();
        width = 199f;
        height = 199f;

        shootingPositions = new Vector2[] {new Vector2(1,1)  /*.......*/   };//hacer aqui toa la mierda de poner las posiciones de los cañones

        layerTag = Layer.LayerNames.ENEMY;

    }


    @Override
    public void update(float delta) {

        innerRingRotation += delta *10;
        outerRingRotation -= delta * 20;

    }

    @Override
    public void draw(SpriteBatch batch) {


        batch.draw(texRegionToDraw(0),position.x,position.y,width/2+1,height/2+1,width,height,scale.x,scale.y,outerRingRotation);
        batch.draw(texRegionToDraw(1),position.x,position.y,width/2+1,height/2+1,width,height,scale.x,scale.y,innerRingRotation);
        batch.draw(texRegionToDraw(2),position.x,position.y,width/2+1,height/2+1,width,height,scale.x,scale.y,rotation);
    }

    TextureRegion texRegionToDraw(int i)
    {
        return Assets.getInstance().ovniTexRegions[i];
    }

}
