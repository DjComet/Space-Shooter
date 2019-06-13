package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputManager implements InputProcessor {

    public boolean keyRight = false;
    public boolean keyLeft = false;
    public boolean keyUp = false;
    public boolean keyUpBool = false;
    public boolean keyDown = false;
    public boolean keyDownBool = false;
    public boolean keyShootN = false;
    public boolean keyShootNBool = false;
    public boolean keyShootS = false;
    public Vector3 pointBut = null;
    public Vector2 mousePos = null;


    public void resetInputs()
    {
        keyUpBool = false;
        keyDownBool = false;
        keyShootNBool = false;
    }

    public boolean keyDown (int keycode) {

        switch(keycode)
        {
            case Input.Keys.D: keyRight = true;
                break;
            case Input.Keys.A: keyLeft = true;
                break;
            case Input.Keys.W: keyUp = true; keyUpBool = true;
                break;
            case Input.Keys.S: keyDown  = true; keyDownBool = true;
                break;
            case Input.Keys.SPACE: keyShootN = true; keyShootNBool = true;
                break;
            case Input.Keys.SHIFT_LEFT: keyShootS = true;
                break;
        }



        return true;
    }

    public boolean keyUp (int keycode) {

        switch(keycode)
        {
            case Input.Keys.D: keyRight = false;
                break;
            case Input.Keys.A: keyLeft = false;
                break;
            case Input.Keys.W: keyUp = false;
                break;
            case Input.Keys.S: keyDown  = false;
                break;
            case Input.Keys.SPACE: keyShootN = false;
                break;
            case Input.Keys.SHIFT_LEFT: keyShootS = false;
                break;
        }

        return true;
    }

    public boolean keyTyped (char character) {
        return false;
    }

    public boolean touchDown (int x, int y, int pointer, int button) {

        pointBut = new Vector3(x,y,0);

        //check if the click is for the HUD
        if(MAIN_GAME.instance.getScreen() == MAIN_GAME.instance.menuScreen)
        {
            MenuController.instance.ch.hudCamera.unproject(pointBut);
            if (!MenuController.instance.hud.click(pointBut.x, pointBut.y))
            {
                System.out.println("MENU_CLICC");
            }
        }
        else {

            WorldController.instance.ch.hudCamera.unproject(pointBut);
            if (!WorldController.instance.hud.click(pointBut.x, pointBut.y))
            {
                System.out.println("WORLD_CLICC");
            }
        }
        return true;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {

        pointBut=null;
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        mousePos = new Vector2(x,y);
        Vector3 temp = new Vector3(mousePos.x,mousePos.y,0);
        if(MAIN_GAME.instance.getScreen() == MAIN_GAME.instance.menuScreen)
        {
            MenuController.instance.ch.hudCamera.unproject(temp);
            if (!MenuController.instance.hud.onMouseOver(temp.x, temp.y))
            {
                System.out.println("Menu_MouseMoved");
            }
        }
        else
        {
            WorldController.instance.ch.hudCamera.unproject(temp);
            if (!WorldController.instance.hud.onMouseOver(temp.x, temp.y))
            {
                System.out.println("World_MouseMoved");
            }
        }
        return false;
    }

    public boolean scrolled (int amount) {
        return false;
    }
}
