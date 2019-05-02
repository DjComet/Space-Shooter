package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Iterator;

public class WorldController extends InputAdapter {

    public CameraHelper ch;

    public String TAG_KEYS = "KEYS";
    public int selectedSprite;
    public ArrayList<GameObject> objects;
    public Assets assets = Assets.getInstance();

    public WorldController(){
        Gdx.input.setInputProcessor(this);
        objects = new ArrayList<GameObject>();
        ch = new CameraHelper();
        init();
    }

    public void init()
    {

        objects.add(new Background());
        objects.add(new Player(0, 0));


    }

    public void update(float deltaTime){

        ch.moveCamera(deltaTime);

        for(Iterator<GameObject> iter = objects.iterator(); iter.hasNext();)
        {
            GameObject element = iter.next();
            element.update(deltaTime);
        }

        ch.followGO(objects.get(1));

    }

}
