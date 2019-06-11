package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.libgdx.spaceshooter.HUD.HUD;
import com.libgdx.spaceshooter.HUD.Logo;
import com.libgdx.spaceshooter.HUD.TextButton;

import java.util.ArrayList;

public class MenuController extends InputAdapter {

    public CameraHelper ch;

    public static MenuController instance;
    public Assets assets = Assets.getInstance();
    public ArrayList<Level> levels = new ArrayList<Level>();//MENUS
    public int currentLevel = 0;
    public InputManager inputMgr = new InputManager();
    public int difficulty = 0;
    public HUD hud;
    public Logo logo;
    public TextButton b1Player, b2Players, bExit;




    public MenuController(){
        if(MenuController.instance ==null)
        {
            instance = this;
        }
        else if(MenuController.instance != this)
        {
            MenuController.instance = null;
        }
        Gdx.input.setInputProcessor(inputMgr);
        ch = new CameraHelper();
        hud = new HUD();

        init();
    }

    public void init()
    {
        logo = new Logo();
        b1Player = new TextButton("1 PLAYER",Gdx.graphics.getWidth()/2 - (Gdx.graphics.getWidth()/2)/2,Gdx.graphics.getHeight()/4,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/10)
        {
            @Override
            public void click() {
                Controllers.clearListeners();
                MAIN_GAME.instance.setScreen(MAIN_GAME.instance.gameScreen);
            }
        };
        b2Players = new TextButton("2 PLAYERS",Gdx.graphics.getWidth()/2 - (Gdx.graphics.getWidth()/2)/2,Gdx.graphics.getHeight()/4 - (Gdx.graphics.getHeight()/9),Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/10)
        {
            @Override
            public void click() {
                Controllers.clearListeners();
                MAIN_GAME.instance.setScreen(MAIN_GAME.instance.gameScreen);
            }
        };
        bExit = new TextButton("EXIT",Gdx.graphics.getWidth()/2 - (Gdx.graphics.getWidth()/2)/2,Gdx.graphics.getHeight()/4 - (Gdx.graphics.getHeight()/9)*2,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/10)
        {
            @Override
            public void click() {
                Controllers.clearListeners();
                MAIN_GAME.instance.dispose();
            }
        };


        hud.addHe(logo);
        hud.add(b1Player);
        hud.add(b2Players);
        hud.add(bExit);
        System.out.println("HUD LOADED UP WITH STUFF");
        levels.add(new Level(difficulty));//Level 0

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
