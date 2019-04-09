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



    public static Sprite generateSprite(){

        Pixmap pm = new Pixmap(40,40,Pixmap.Format.RGBA8888);

        pm.setColor(1.0f,0.0f,0.0f,1.0f);
        pm.drawLine(0,0,9,9);
        pm.drawLine(0,9,9,0);
        pm.drawRectangle(0,0,10,10);

        Texture t = new Texture(pm);

        Sprite s = new Sprite(t);
        s.setSize(1,1);
        s.setPosition(MathUtils.random(-1,1),MathUtils.random(-1,1));
        s.setOriginCenter();

        return s;
    }

    public static Sprite generateSprTexture(){

        Texture t = Assets.getInstance().player;

        Sprite s = new Sprite(t);
        s.setSize(1,1);
        s.setPosition(1,1);
        s.setOriginCenter();

        return s;
    }

    public static Sprite spriteFromTextureRegion(String regionName)
    {

        TextureAtlas txAtlas = new TextureAtlas("mario/pack1.atlas");
        TextureRegion txreg = txAtlas.findRegion("mario1");
        Sprite spr = new Sprite(txreg);
        spr.setSize(1, 1);
        spr.setPosition(1, 1);
        spr.setOriginCenter();
        return spr;
    }



    public static TextureRegion textureFromTextureAtlas(String regionName){

        TextureAtlas txAtlas = new TextureAtlas("mario/pack1.atlas");
        TextureRegion txreg = txAtlas.findRegion(regionName);
        return txreg;
    }
}
