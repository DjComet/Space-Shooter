package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private static Assets instance = null;
    public TextureAtlas player;
    public TextureRegion[] playerTexRegions;
    public Animation<TextureRegion> p_bankLeft;
    public Animation<TextureRegion> p_bankRight;

    public TextureAtlas simpleEnemy;
    public TextureRegion[] seTexRegions;
    public Animation<TextureRegion> se_bankLeft;
    public Animation<TextureRegion> se_bankRight;

    public TextureAtlas advancedEnemy;
    public TextureRegion[] aeTexRegions;
    public Animation<TextureRegion> ae_bankLeft;
    public Animation<TextureRegion> ae_bankRight;

    public TextureAtlas shot;
    public TextureRegion[] shotTexRegions;

    public Texture bg;


    private Assets() {

        bg = new Texture (Gdx.files.internal("background.jpg"));
        //PLAYER-------------------------------------------------------------------------------------------------------------------------
        player = new TextureAtlas("Player.atlas");
        int playerRegionsNumber = player.getRegions().size;
        playerTexRegions = new TextureRegion[playerRegionsNumber];


        for(int i = 0; i<playerRegionsNumber; i++)
        {
            playerTexRegions[i] = SpriteHelper.textureFromTextureAtlas("Player"+(i), player);
        }

        p_bankLeft = new Animation<TextureRegion>(0.1f,playerTexRegions[0],playerTexRegions[1],playerTexRegions[2]);
        p_bankLeft.setPlayMode(Animation.PlayMode.REVERSED);
        p_bankRight = new Animation<TextureRegion>(0.1f,playerTexRegions[4],playerTexRegions[5],playerTexRegions[6]);
        p_bankRight.setPlayMode(Animation.PlayMode.NORMAL);

        //SIMPLE ENEMY------------------------------------------------------------------------------------------------------------------------------

        simpleEnemy = new TextureAtlas("SimpleEnemy.atlas");
        int seRegionsNumber = simpleEnemy.getRegions().size;
        seTexRegions = new TextureRegion[seRegionsNumber];

        for(int i = 0; i<seRegionsNumber; i++)
        {
            seTexRegions[i] = SpriteHelper.textureFromTextureAtlas("SimpleEnemy"+(i), simpleEnemy);
        }

        se_bankLeft = new Animation<TextureRegion>(0.1f, seTexRegions[0], seTexRegions[1], seTexRegions[2]);
        se_bankLeft.setPlayMode(Animation.PlayMode.REVERSED);
        se_bankRight = new Animation<TextureRegion>(0.1f, seTexRegions[4], seTexRegions[5], seTexRegions[6]);
        se_bankRight.setPlayMode(Animation.PlayMode.NORMAL);

        //ADVANCED ENEMY-------------------------------------------------------------------------------------------------------------------------------

        advancedEnemy = new TextureAtlas("AdvancedEnemy.atlas");
        int aeRegionsNumber = advancedEnemy.getRegions().size;
        aeTexRegions = new TextureRegion[aeRegionsNumber];

        for(int i = 0; i<aeRegionsNumber; i++)
        {
            aeTexRegions[i] = SpriteHelper.textureFromTextureAtlas("AdvancedEnemy"+(i), advancedEnemy);
        }

        ae_bankLeft = new Animation<TextureRegion>(0.1f, aeTexRegions[0], aeTexRegions[1], aeTexRegions[2]);
        ae_bankLeft.setPlayMode(Animation.PlayMode.REVERSED);
        ae_bankRight = new Animation<TextureRegion>(0.1f, aeTexRegions[4], aeTexRegions[5], aeTexRegions[6]);
        ae_bankRight.setPlayMode(Animation.PlayMode.NORMAL);

        //SHOT--------------------------------------------------------------------------------------------------------------------------------

        shot = new TextureAtlas("shot.atlas");
        int shotRegionsNumber = shot.getRegions().size;
        shotTexRegions = new TextureRegion[shotRegionsNumber];

        for(int i = 0; i<shotRegionsNumber; i++)
        {
            shotTexRegions[i] = SpriteHelper.textureFromTextureAtlas("Shot"+(i), shot);
        }
    }

    public static Assets getInstance() {
        if(instance == null) {
            instance = new Assets();
        }
        return instance;
    }

}
