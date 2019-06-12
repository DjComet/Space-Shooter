package com.libgdx.spaceshooter;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.libgdx.spaceshooter.HUD.HUD;
import com.libgdx.spaceshooter.HUD.HUDElement;
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
    public HUDElement marker;
    public TextButton b1Player, b2Players, bExit;

    float firstHeight = Gdx.graphics.getHeight()/3;
    float secondHeight = firstHeight - (Gdx.graphics.getHeight()/9);
    float thirdHeight = firstHeight - (Gdx.graphics.getHeight()/9)*2;
    float markerHeight = 100000000;
    int max = 3;
    int i = 3;
    boolean start = false;
    float t = 0;

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
        initHud();
        levels.add(new Level(difficulty));//Level 0

    }

    public void initHud()
    {
        logo = new Logo();


        marker = new HUDElement() {
            @Override
            public void render(SpriteBatch batch) {
                batch.draw(Assets.getInstance().marker,Gdx.graphics.getWidth()/2 - (Gdx.graphics.getWidth()/3)/2,markerHeight,Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/14);
            }
        };
        b1Player = new TextButton("I PLAYER",Gdx.graphics.getWidth()/2 - (Gdx.graphics.getWidth()/3)/2,firstHeight,Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/14)
        {
            @Override
            public void click() {
                Controllers.clearListeners();
                start = true;
            }

            @Override
            public void onMouseOver() {
                i=0;
            }
        };
        b2Players = new TextButton("II PLAYERS",Gdx.graphics.getWidth()/2 - (Gdx.graphics.getWidth()/3)/2,secondHeight,Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/14)
        {
            @Override
            public void click() {
                Controllers.clearListeners();

                MAIN_GAME.instance.gameScreen.twoPlayers = true;
                start = true;
            }

            @Override
            public void onMouseOver() {i=1;}
        };
        bExit = new TextButton("EXIT",Gdx.graphics.getWidth()/2 - (Gdx.graphics.getWidth()/3)/2,thirdHeight,Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/14)
        {
            @Override
            public void click() {
                Controllers.clearListeners();
                MAIN_GAME.instance.dispose();
                Gdx.app.exit();
            }
            @Override
            public void onMouseOver() {
                i=2;
            }
        };


        hud.addHe(logo);
        hud.add(b1Player);
        if(Gdx.app.getType() != Application.ApplicationType.Android)
        hud.add(b2Players);
        hud.add(bExit);
        hud.addHe(marker);
        System.out.println("HUD LOADED UP WITH STUFF");
    }

    public void update(float deltaTime){


        updateMarker();
        startGame(deltaTime);
        levels.get(currentLevel).update(deltaTime);


        //ch.followGO(levels.get(currentLevel).getPlayer());

    }

    void updateMarker()
    {
        float[] markerpositions = new float[]{firstHeight,secondHeight,thirdHeight,100000000};

        if(inputMgr.keyDownBool)
        {
            if(markerHeight==markerpositions[3])
            {
                i=0;
                max = 2;
            }
            else i++;


        }
        else if(inputMgr.keyUpBool)
        {
            if(markerHeight==markerpositions[3])
            {
                i=0;
                max = 2;
            }
            else i--;
        }
        i = MathUtils.clamp(i, 0, max);
        markerHeight = markerpositions[i];

        if(markerHeight == markerpositions[0] && inputMgr.keyShootNBool)
        {
            Controllers.clearListeners();
            start = true;
        }
        else if(markerHeight == markerpositions[1] && inputMgr.keyShootNBool)
        {
            Controllers.clearListeners();
            start = true;
            MAIN_GAME.instance.gameScreen.twoPlayers = true;
            //ADD 2nd player control;
        }
        else if(markerHeight == markerpositions[2] && inputMgr.keyShootNBool)
        {
            Controllers.clearListeners();
            MAIN_GAME.instance.dispose();
            Gdx.app.exit();
        }

    }

    void startGame(float deltatime)
    {
        Background main = (Background) getCurrentLevel().getBg();
        Background canyonL = (Background)getCurrentLevel().getLayerList(Layer.LayerNames.BACKGROUND).get(1);
        Background canyonR = (Background)getCurrentLevel().getLayerList(Layer.LayerNames.BACKGROUND).get(2);
        if(start)
        {
            t += deltatime/2 ;
            if(t>= 1) {
                MAIN_GAME.instance.setScreen(MAIN_GAME.instance.gameScreen);
                start = false;
                t =0;
            }
        }
        canyonL.speedY = MathUtils.lerp(-200,-100, t);
        canyonR.speedY = MathUtils.lerp(-200,-100, t);
        main.speedY = MathUtils.lerp(-400,-200, t);
        System.out.println("Speed: "+canyonL.speedY);

    }


    public Level getCurrentLevel()
    {
        return levels.get(currentLevel);
    }


}
