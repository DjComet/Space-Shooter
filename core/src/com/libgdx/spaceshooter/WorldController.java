package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

import java.util.ArrayList;

public class WorldController extends InputAdapter {

    public CameraHelper ch;

    public static WorldController instance;
    public Assets assets = Assets.getInstance();
    public ArrayList<Level> levels = new ArrayList<Level>();
    public int currentLevel = 1;//Set to 0 when the menu is done
    public InputManager inputMgr = new InputManager();
    public int difficulty;



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
        levels.add(new Level(difficulty));//Final
        //add new level



            //getCurrentLevel().Instantiate(new Ovni());

    }

    public void update(float deltaTime){

        levels.get(currentLevel).update(deltaTime);


        //ch.followGO(levels.get(currentLevel).getPlayer());

    }

    public Level getCurrentLevel()
    {
        return levels.get(currentLevel);
    }


}
