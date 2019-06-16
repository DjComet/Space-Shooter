package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdx.spaceshooter.HUD.HUD;
import com.libgdx.spaceshooter.HUD.HUDElement;

import java.util.ArrayList;

public class WorldController extends InputAdapter {

    public CameraHelper ch;

    public static WorldController instance;
    public Assets assets = Assets.getInstance();
    public ArrayList<Level> levels = new ArrayList<Level>();
    public int currentLevel = 4;
    public InputManager inputMgr = new InputManager();
    public int difficulty;
    float restartTimer = 5f;


    public HUD hud;
    public ArrayList<HUDElement> lifeP1, lifeP2;




    public WorldController(){
        if(WorldController.instance ==null)
        {
            instance = this;
        }
        else if(WorldController.instance != this)
        {
            WorldController.instance = null;
        }
        Gdx.input.setInputProcessor(inputMgr);
        hud = new HUD();
        ch = new CameraHelper();
        init();
    }

    public void init()
    {



        difficulty = 1;
        levels.add(new Level(difficulty));//Level 0
        levels.add(new Level(difficulty));//Level 1
        levels.add(new Level(difficulty));//Level 2
        levels.add(new Level(difficulty));//Level 3
        levels.add(new Level(5));//Final
        //add new level

        initHud();

            //getCurrentLevel().Instantiate(new Ovni());

    }

    void initHud()
    {
        final float healthWidth = Gdx.graphics.getWidth()/20;
        final float healthHeight = Gdx.graphics.getHeight()/20;

        lifeP1 = new ArrayList<HUDElement>();
        for(int i = 0; i<getCurrentLevel().getPlayer().lives;i++)
        {
            final int finalI = i;
            lifeP1.add(new HUDElement() {
                @Override
                public void render(SpriteBatch batch) {
                    batch.draw(Assets.getInstance().livesTexRegions[0],-Gdx.graphics.getWidth()/2f+healthWidth/4, Gdx.graphics.getHeight()/4-(healthHeight *(finalI+1)) ,healthWidth,healthHeight);
                }
            });
            hud.addHe(lifeP1.get(i));
        }

        lifeP2 = new ArrayList<HUDElement>();
        if(MAIN_GAME.instance.gameScreen.twoPlayers) {
            for (int i = 0; i < getCurrentLevel().getPlayer2().lives; i++) {
                final int finalI = i;
                lifeP2.add(new HUDElement() {
                    @Override
                    public void render(SpriteBatch batch) {
                        batch.draw(Assets.getInstance().livesTexRegions[1], Gdx.graphics.getWidth() / 2f - healthWidth, Gdx.graphics.getHeight() / 4 - (healthHeight * (finalI + 1)), healthWidth, healthHeight);
                    }
                });
                hud.addHe(lifeP2.get(i));
            }
        }



    }

    void updateHud()
    {
        for(HUDElement he:lifeP1)
        {
            if(getCurrentLevel().getPlayer().lives<getCurrentLevel().getPlayer().lives)
        }

    }


    public void update(float deltaTime){

        if(getCurrentLevel().getLayerList(Layer.LayerNames.PLAYER).size()==0)
        {
            System.out.println("Oh shit you've lost!");
            restartTimer-=deltaTime;


        }
        levels.get(currentLevel).update(deltaTime);
        if(restartTimer <= 0)
        {
            MAIN_GAME.instance.setScreen(MAIN_GAME.instance.menuScreen);
        }

        //ch.followGO(levels.get(currentLevel).getPlayer());

    }

    public Level getCurrentLevel()
    {
        return levels.get(currentLevel);
    }


}
