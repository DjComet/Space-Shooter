package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

abstract public class GameObject {

    public Vector2 position;
    public float rotation;
    public Vector2 scale;
    public float width, height;
    public String tag;
    public Rectangle rectangle;
    abstract public void draw(SpriteBatch batch);
    abstract public void update(float delta);

    public Vector2 getCenterPos()
    {
        return new Vector2(this.position.x+ width/2,this.position.y+ height/2);
    }

    public Rectangle GetRectangle(){
        return rectangle.set(position.x,position.y,width,height);
    }

}
