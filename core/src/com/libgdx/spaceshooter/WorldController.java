package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.libgdx.spaceshooter.HUD.HUD;
import com.libgdx.spaceshooter.HUD.HUDElement;
import com.libgdx.spaceshooter.HUD.TextButton;

import java.util.ArrayList;

public class WorldController extends InputAdapter {

    public CameraHelper ch;

    public static WorldController instance;
    public Assets assets = Assets.getInstance();
    public ArrayList<Level> levels = new ArrayList<Level>();
    public int currentLevel = 3;
    public InputManager inputMgr = new InputManager();
    public int difficulty;
    float restartTimer = 8f;


    public HUD hud;
    public BitmapFont font;
    public ArrayList<HUDElement> lifeP1, lifeP2;
    public HUDElement healthP1, healthP2, gameEnd;






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
        font = Assets.getInstance().gameFont;
        ch = new CameraHelper();
        init();
    }

    public void init()
    {



        difficulty = 4;
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
                    if(canRender)batch.draw(Assets.getInstance().livesTexRegions[0],-Gdx.graphics.getWidth()/2f+healthWidth/4, Gdx.graphics.getHeight()/4+(healthHeight *(finalI+1)) ,healthWidth,healthHeight);
                }
            });
            hud.addHe(lifeP1.get(i));
        }

        healthP1 = new HUDElement() {
            @Override
            public void render(SpriteBatch batch) {
                if(WorldController.instance.getCurrentLevel().getPlayer()!=null)
                font.draw(batch,"HP\n"+ WorldController.instance.getCurrentLevel().getPlayer().health,-Gdx.graphics.getWidth()/2f + healthWidth/1.5f,0,healthWidth/10f, Align.center,false);
                else
                font.draw(batch,"HP\n 0",-Gdx.graphics.getWidth()/2f + healthWidth/1.5f,0,healthWidth/10f, Align.center,false);
            }
        };
        hud.addHe(healthP1);

        lifeP2 = new ArrayList<HUDElement>();
        if(MAIN_GAME.instance.gameScreen.twoPlayers) {
            for (int i = 0; i < getCurrentLevel().getPlayer2().lives; i++) {
                final int finalI = i;
                lifeP2.add(new HUDElement() {
                    @Override
                    public void render(SpriteBatch batch) {
                        if(canRender)batch.draw(Assets.getInstance().livesTexRegions[1], Gdx.graphics.getWidth() / 2f - healthWidth, Gdx.graphics.getHeight() / 4 + (healthHeight * (finalI + 1)), healthWidth, healthHeight);
                    }
                });
                hud.addHe(lifeP2.get(i));
            }
            healthP2 = new HUDElement() {
                @Override
                public void render(SpriteBatch batch) {
                    if(WorldController.instance.getCurrentLevel().getPlayer2()!=null)
                        font.draw(batch,"HP\n"+ WorldController.instance.getCurrentLevel().getPlayer2().health,Gdx.graphics.getWidth()/2f - healthWidth/1.5f ,0,healthWidth/10f, Align.center,false);
                    else
                        font.draw(batch,"HP\n 0",Gdx.graphics.getWidth()/2f - healthWidth/1.5f,0,healthWidth/10f, Align.center,false);
                }
            };
            hud.addHe(healthP2);

            gameEnd = new HUDElement() {
                @Override
                public void render(SpriteBatch batch) {
                    if(WorldController.instance.getCurrentLevel().gameWon)
                        font.draw(batch,"YOU WON!",0 ,0,healthWidth, Align.center,false);
                    else if (WorldController.instance.getCurrentLevel().gameLost)
                        font.draw(batch,"YOU LOST... :(",0 ,0,healthWidth, Align.center,false);
                }
            };
            hud.addHe(gameEnd);
        }





    }

    void updateHud()
    {

        for(int i = 0; i< lifeP1.size(); i++)
        {
            int livesRendering = 0;
            for(int j = 0; j < lifeP1.size(); j++)
            {
                if(lifeP1.get(j).canRender)
                {
                    livesRendering ++;
                }

            }

            if(getCurrentLevel().getPlayer()!= null && livesRendering>getCurrentLevel().getPlayer().lives)
            {
                lifeP1.get(livesRendering-1).canRender = false;
            }
        }



        if(MAIN_GAME.instance.gameScreen.twoPlayers) //IF PLAYER TWO
        {
            for (int i = 0; i < lifeP2.size(); i++)
            {
                int livesRendering = 0;
                for (int j = 0; j < lifeP2.size(); j++)
                {
                    if (lifeP2.get(j).canRender)
                    {
                        livesRendering++;
                    }

                }

                if (getCurrentLevel().getPlayer2()!= null && livesRendering > getCurrentLevel().getPlayer2().lives)
                {
                    lifeP2.get(livesRendering - 1).canRender = false;
                }
            }
        }
    }


    public void update(float deltaTime){

        if(getCurrentLevel().getLayerList(Layer.LayerNames.PLAYER).size()==0)
        {
            System.out.println("Oh shit you've lost!");
            getCurrentLevel().gameLost = true;
            restartTimer-=deltaTime;

        }
        else if(getCurrentLevel().gameWon)
        {
            System.out.println("Oh shit you won!");
            restartTimer-=deltaTime;
        }
        levels.get(currentLevel).update(deltaTime);
        updateHud();
        if(restartTimer <= 0)
        {
            MAIN_GAME.instance.setScreen(MAIN_GAME.instance.menuScreen);
            restartTimer = 8;
        }

        //ch.followGO(levels.get(currentLevel).getPlayer());

    }

    public Level getCurrentLevel()
    {
        return levels.get(currentLevel);
    }


}
