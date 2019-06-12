package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.libgdx.spaceshooter.HUD.HUD;

import java.util.ArrayList;

public class WorldController extends InputAdapter {

    public CameraHelper ch;

    public static WorldController instance;
    public Assets assets = Assets.getInstance();
    public ArrayList<Level> levels = new ArrayList<Level>();
    public int currentLevel = 4;
    public InputManager inputMgr = new InputManager();
    public int difficulty;
    public HUD hud;

    float restartTimer = 5f;



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



            //getCurrentLevel().Instantiate(new Ovni());

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
