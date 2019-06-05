package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class InputManager implements InputProcessor {

    public boolean keyRight = false;
    public boolean keyLeft = false;
    public boolean keyUp = false;
    public boolean keyDown = false;
    public boolean keyShootN = false;
    public boolean keyShootS = false;




    public boolean keyDown (int keycode) {

        keyRight = (keycode == Input.Keys.D ? true: false);
        keyLeft = (keycode == Input.Keys.A ? true: false);
        keyUp = (keycode == Input.Keys.W ? true: false);
        keyDown = (keycode == Input.Keys.S ? true: false);
        keyShootN = (keycode == Input.Keys.SPACE ? true: false);
        keyShootS = (keycode == Input.Keys.SHIFT_LEFT ? true: false);

        return false;
    }

    public boolean keyUp (int keycode) {

        keyRight = (keycode == Input.Keys.D ? false: true);
        keyLeft = (keycode == Input.Keys.A ? false: true);
        keyUp = (keycode == Input.Keys.W ? false: true);
        keyDown = (keycode == Input.Keys.S ? false: true);
        keyShootN = (keycode == Input.Keys.SPACE ? false: true);
        keyShootS = (keycode == Input.Keys.SHIFT_LEFT ? false: true);

        return false;
    }

    public boolean keyTyped (char character) {
        return false;
    }

    public boolean touchDown (int x, int y, int pointer, int button) {
        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
        return false;
    }

    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (int amount) {
        return false;
    }
}
