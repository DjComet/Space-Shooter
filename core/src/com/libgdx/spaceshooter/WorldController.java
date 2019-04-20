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
    public Sprite spr;
    public Sprite[] sprites;
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

        objects.add(new Player(Constants.VIEWPORT_WIDTH/2, Constants.VIEWPORT_HEIGHT/4));

    }

    public void update(float deltaTime){

        //ch.moveCamera(delta);
        int directionX = 0;
        int directionY = 0;

        /*if(Gdx.input.isKeyPressed(Input.Keys.A)){directionX = -1;}
        if(Gdx.input.isKeyPressed(Input.Keys.D)){directionX = 1;}
        if(Gdx.input.isKeyPressed(Input.Keys.W)){directionY = 1;}
        if(Gdx.input.isKeyPressed(Input.Keys.S)){directionY = -1;}
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){ selectedSprite++;}
        if(Gdx.input.isKeyPressed(Input.Keys.Z)) {ch.zoomIn(deltaTime);}

        if( selectedSprite >= sprites.length-1 ) selectedSprite = 0;

        float x = sprites[selectedSprite].getX();
        float y = sprites[selectedSprite].getY();
        x += directionX * deltaTime;
        y += directionY * deltaTime;
        sprites[selectedSprite].setX(x);
        sprites[selectedSprite].setY(y);*/


        for(Iterator<GameObject> iter = objects.iterator(); iter.hasNext();)
        {
            GameObject element = iter.next();
            element.update(deltaTime);
        }

        ch.followGO(objects.get(0));

    }

    @Override
    public boolean keyDown (int keycode){
        Gdx.app.debug(TAG_KEYS, keycode+" has been pressed");
        if(keycode==Input.Keys.I)
        {
            Gdx.app.debug("SPRITE", spr.getBoundingRectangle()+"");
        }
        return false;
    }
}
