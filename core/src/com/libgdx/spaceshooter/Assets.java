package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private static Assets instance = null;
    public TextureAtlas player;
    public TextureRegion[] playerTexRegions;
    public Animation<TextureRegion> bankLeft;
    public Animation<TextureRegion> bankRight;

    public TextureAtlas simpleEnemy;
    public TextureRegion[] simpleETexRegions;

    public TextureAtlas advancedEnemy;
    public TextureRegion[] advancedETexRegions;



    private Assets() {
        player = new TextureAtlas("Player.atlas");
        playerTexRegions = new TextureRegion[Constants.PLAYER_TEXREG_NUMBER];

        for(int i = 0; i<Constants.PLAYER_TEXREG_NUMBER; i++)
        {
            playerTexRegions[i] = SpriteHelper.textureFromTextureAtlas("player"+(i), player);
        }

        bankLeft = new Animation<TextureRegion>(0.1f,playerTexRegions[0],playerTexRegions[1],playerTexRegions[2]);
        bankLeft.setPlayMode(Animation.PlayMode.REVERSED);
        bankRight = new Animation<TextureRegion>(0.1f,playerTexRegions[4],playerTexRegions[5],playerTexRegions[6]);
        bankRight.setPlayMode(Animation.PlayMode.NORMAL);

    }

    public static Assets getInstance() {
        if(instance == null) {
            instance = new Assets();
        }
        return instance;
    }

}
