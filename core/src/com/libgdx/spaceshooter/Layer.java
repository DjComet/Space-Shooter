package com.libgdx.spaceshooter;

import com.badlogic.gdx.Game;

import java.util.ArrayList;

public class Layer {

    public ArrayList<GameObject> list;
    public enum LayerNames{BACKGROUND, PLAYERSHOT, ENEMY, ENEMYSHOT, PLAYER, DEFAULT};
    LayerNames name;

    public Layer (LayerNames _name)
    {
        name = _name;
        list = new ArrayList<GameObject>();
    }
}
