package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class SimpleEnemy extends GameObject {

    public Vector2 speed;
    private Texture texture;


    public SimpleEnemy(float posX, float posY) {
        position = new Vector2(posX, posY);

        rotation = 180;

        //width = Assets.getInstance().textureRegions[0].getRegionWidth();
        //height = Assets.getInstance(.textureRegions[0].getRegionHeight();

        width = 1.0f;
        height = 1.0f;

        scale.x = 1.0f;
        scale.y = 1.0f;

        speed.x = 1.0f;
        speed.y = 1.0f;
        texture = new Texture(Gdx.files.internal("SimpleEnemy.png"));
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(Assets.getInstance().seTexRegions[0], position.x, position.y, 0, 0, width, height, scale.x, scale.y, rotation);
    }

    @Override
    public void update(float delta) {
        Vector2 direction = new Vector2(0, 1);

        position.x += direction.x * speed.x * delta;
        position.y += direction.y * speed.y * delta;


    }

    void shoot() {

    }
}
