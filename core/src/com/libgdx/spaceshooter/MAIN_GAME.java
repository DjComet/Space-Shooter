package com.libgdx.spaceshooter;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;

public class MAIN_GAME extends Game {

    SpaceShooterMenu menuScreen;
    SpaceShooterMainScene gameScreen;
    public static MAIN_GAME instance;

    @Override
    public void create() {
        if(MAIN_GAME.instance ==null)
        {
            instance = this;
        }
        else if(MAIN_GAME.instance != this)
        {
            MAIN_GAME.instance = null;
        }
        menuScreen = new SpaceShooterMenu();
        gameScreen = new SpaceShooterMainScene();

        if(Gdx.app.getType() == Application.ApplicationType.Android)
            System.out.println("Estoy en android");
        else if (Gdx.app.getType() == Application.ApplicationType.Desktop){

            if(Controllers.getControllers().size > 0)
            {

            }
        }

        setScreen(menuScreen);
    }


}
