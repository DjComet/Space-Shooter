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

    public String TAG_KEYS = "KEYS";
    public int selectedSprite;
    public ArrayList<GameObject> objects;
    public Assets assets = Assets.getInstance();
    public Level level1 = new Level(new Background());

    public WorldController(){
        Gdx.input.setInputProcessor(this);
        objects = new ArrayList<GameObject>();
        ch = new CameraHelper();
        init();
    }

    public void init()
    {
        level1.Instantiate(new Player(0,0));





    }

    public void update(float deltaTime){

        level1.update(deltaTime);
        ch.followGO(level1.getPlayer());

    }



}
