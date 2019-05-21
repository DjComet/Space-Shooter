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


    public WorldController(){
        if(WorldController.instance ==null)
        {
            instance = this;
        }
        else if(WorldController.instance != this)
        {
            WorldController.instance = null;
        }
        Gdx.input.setInputProcessor(this);
        ch = new CameraHelper();
        init();
    }

    public void init()
    {
        levels.add(new Level(new Background()));//Level 0
        levels.add(new Level(new Background()));//Level 1
        levels.add(new Level(new Background()));//Level 2
        levels.add(new Level(new Background()));//Level 3


        //levels.Instantiate(new Shot(ShotType.PLNORMAL, 0,0,30, 10, 0));
        //for(int i = 0; i<10; i++)
            getCurrentLevel().Instantiate(new SimpleEnemy(-getCurrentLevel().background.width/2 + (float)Math.random()* getCurrentLevel().background.width,50));

    }

    public void update(float deltaTime){

        levels.get(currentLevel).update(deltaTime);


        ch.followGO(levels.get(currentLevel).getPlayer());

    }

    public Level getCurrentLevel()
    {
        return levels.get(currentLevel);
    }


}
