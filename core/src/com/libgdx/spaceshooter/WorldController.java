package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;

public class WorldController extends InputAdapter {

    public CameraHelper ch;
    public static WorldController instance;
    public String TAG_KEYS = "KEYS";
    public int selectedSprite;
    public ArrayList<GameObject> objects;
    public Assets assets = Assets.getInstance();
    public Level level1 = new Level(new Background());
    float seTimer = 0f;
    float aeTimer = 0f;

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
        objects = new ArrayList<GameObject>();
        ch = new CameraHelper();
        init();
    }

    public void init()
    {
        level1.Instantiate(new Player(0,0));
        for(int i = 0; i<10; i++)
        level1.Instantiate(new SimpleEnemy(-level1.background.width/2 + (float)Math.random()*level1.background.width +1,80));

    }

    public void update(float deltaTime){

        level1.update(deltaTime);







        ch.followGO(level1.getPlayer());

    }



}
