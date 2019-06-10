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


    public TextureAtlas advancedEnemy;
    public TextureRegion[] aeTexRegions;

    public TextureAtlas ovni;
    public TextureRegion[] ovniTexRegions;


    public TextureAtlas shot;
    public TextureRegion[] shotTexRegions;

    public TextureAtlas expl;
    public TextureRegion[] explTexRegions;
    public Animation<TextureRegion> explosion;

    public Texture bg;
    public TextureRegion tiledBg;

    public Texture canyon;
    public TextureRegion tiledCanyon;

    private Assets() {

        //BACKGROUND---------------------------------------------------------------------------------------------------------------------
        bg = new Texture (Gdx.files.internal("WaterTile.png"));
        bg.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);
        float bgFactorWidth = bg.getWidth() * 0.03f;        //this means that my Texture should cover 1 world unit width.

        float bgFactorHeight = bg.getHeight() * 0.03f;   //this means that my Texture should cover 1 world unit height.

        float widthInWorldUnits = 200;                        //I am creating a world of 25 units width (my viewport width is around 10)

        float heightInWorldUnits = 400;                     //I am creating a world of 20 units height (my viewport height is fixed to 10)

        tiledBg = new TextureRegion(bg, Math.round(widthInWorldUnits*bgFactorWidth),Math.round(heightInWorldUnits*bgFactorHeight));

        canyon = new Texture (Gdx.files.internal("CanyonWall.png"));
        canyon.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.Repeat);
        float canyonFactorWidth = canyon.getWidth() * 0.02f;

        float canyonFactorHeight = canyon.getHeight() * 0.02f;

        float canyonWidthInWorldUnits = 32;

        float canyonHeightInWorldUnits = 400;

        tiledCanyon = new TextureRegion(canyon, Math.round(canyonWidthInWorldUnits*canyonFactorWidth),Math.round(canyonHeightInWorldUnits*canyonFactorHeight));

        //EXPLOSION----------------------------------------------------------------------------------------------------------------------

        expl = new TextureAtlas("Explosion.atlas");
        int expRegNumbers = expl.getRegions().size;
        explTexRegions = new TextureRegion[expRegNumbers];
        for(int i = 0; i<expRegNumbers; i++)
        {
            explTexRegions[i] = SpriteHelper.textureFromTextureAtlas("Explosion"+(i), expl);
        }
        explosion = new Animation<TextureRegion>(0.05f,explTexRegions[0],explTexRegions[1],explTexRegions[2],explTexRegions[3],explTexRegions[4],explTexRegions[5],explTexRegions[6],explTexRegions[7]);
        explosion.setPlayMode(Animation.PlayMode.NORMAL);

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



        //ADVANCED ENEMY-------------------------------------------------------------------------------------------------------------------------------

        advancedEnemy = new TextureAtlas("AdvancedEnemy.atlas");
        int aeRegionsNumber = advancedEnemy.getRegions().size;
        aeTexRegions = new TextureRegion[aeRegionsNumber];

        for(int i = 0; i<aeRegionsNumber; i++)
        {
            aeTexRegions[i] = SpriteHelper.textureFromTextureAtlas("AdvancedEnemy"+(i), advancedEnemy);
        }

        //OVNI--------------------------------------------------------------------------------------------------------------------------------------
        ovni = new TextureAtlas("Ovni.atlas");
        int ovniRegNum = ovni.getRegions().size;
        ovniTexRegions = new TextureRegion[ovniRegNum];

        for(int i = 0; i<ovniRegNum; i++)
        {
            ovniTexRegions[i] = SpriteHelper.textureFromTextureAtlas("Ovni"+(i), ovni);
        }


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
