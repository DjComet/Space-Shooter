package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

abstract public class GameObject {

    public Vector2 position;
    public float rotation;
    public Vector2 scale;
    public float width, height;


    abstract public void draw(SpriteBatch batch);
    abstract public void update(float delta);

}
