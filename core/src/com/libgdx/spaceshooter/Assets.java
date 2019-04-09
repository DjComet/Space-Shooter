package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private static Assets instance = null;
    public Texture player;
    public Texture simpleEnemy;
    public TextureRegion[] textureRegions;
    public Animation<TextureRegion> anim;
    private Assets() {
        player = new Texture("Player.png");
        simpleEnemy = new Texture("SimpleEnemy.png");
        textureRegions = new TextureRegion[Constants.TEXREG_NUMBER];
        for(int i=0; i<Constants.TEXREG_NUMBER; i++)
        {
            textureRegions[i] = SpriteHelper.textureFromTextureAtlas("mario"+(i+1));
        }
        anim = new Animation<TextureRegion>(0.1f,textureRegions);
        anim.setPlayMode(Animation.PlayMode.LOOP);
    }

    public static Assets getInstance() {
        if(instance == null) {
            instance = new Assets();
        }
        return instance;
    }

}
