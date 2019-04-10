package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class SpriteHelper {
    /**
     * Generate a sprite for testing purposes
     * @return the generated sprite has a size and a position.
     */




    public static SimpleEnemy generateSimpleEnemy(){
        //TO IMPLEMENT
        return null;
    }



    public static TextureRegion textureFromTextureAtlas(String regionName, TextureAtlas txAtlas){
        TextureRegion txreg = txAtlas.findRegion(regionName);
        return txreg;
    }
}
