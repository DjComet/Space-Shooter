package com.libgdx.spaceshooter;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Level {

    //initialize and refresh gameobjects, rendering and updating.
    public GameObject background;
    public CopyOnWriteArrayList<GameObject> playerGos;
    public CopyOnWriteArrayList<GameObject> enemyGos;
    public CopyOnWriteArrayList<GameObject> defaultGos;
    public CopyOnWriteArrayList<GameObject> gameObjects;

    public WaveManager waveM;

    public float seInterval = 2f;
    public float aeInterval = 5f;


    public Level(GameObject bg)
    {
        background = bg;
        playerGos = new CopyOnWriteArrayList<GameObject>();
        enemyGos = new CopyOnWriteArrayList<GameObject>();
        defaultGos = new CopyOnWriteArrayList<GameObject>();
        gameObjects = new CopyOnWriteArrayList<GameObject>();

        switch(WorldController.instance.currentLevel)
        {
            case 0: waveM = new WaveManager();
            break;

            case 1: waveM = new WaveManager();

        }
    }

    public void update(float deltaTime)
    {
        Iterator<GameObject> iter = gameObjects.iterator();
        while (iter.hasNext()){

            GameObject element = iter.next();

            element.update(deltaTime);


        }
    }

    public void refresh()
    {
        gameObjects.clear();
        gameObjects.add(background);
        gameObjects = combine(gameObjects, playerGos);
        gameObjects = combine(gameObjects, enemyGos);
        gameObjects = combine(gameObjects, defaultGos);
    }

    public CopyOnWriteArrayList<GameObject> combine(CopyOnWriteArrayList<GameObject> a, CopyOnWriteArrayList<GameObject> b){
        //int length = a.size() + b.size();
        CopyOnWriteArrayList<GameObject> result = new CopyOnWriteArrayList<GameObject>();
        for(int i = 0; i < a.size(); i++)
        {
            result.add(a.get(i));
        }

        for(int i = 0; i < b.size(); i++)
        {
            result.add(b.get(i));
        }
        return result;
    }

    public void Instantiate(GameObject gameObject)
    {
        if(gameObject.tag == "PLAYER")
        {
            playerGos.add(gameObject);

        }
        else if(gameObject.tag == "ENEMY")
        {
            enemyGos.add(gameObject);
        }
        else
        {
            defaultGos.add(gameObject);
        }

        refresh();

        centerObject(gameObjects.size()-1);


    }

    void centerObject(int index)
    {
        System.out.println("Index of object being centered: "+index);
        if(index < gameObjects.size())
        {
            gameObjects.get(index).position = new Vector2(gameObjects.get(index).position.x-gameObjects.get(index).width/2,gameObjects.get(index).position.y-gameObjects.get(index).width/2);
            System.out.println("position of centered object: " + gameObjects.get(index).position);
        }
        else
            System.out.println("The index is out of bounds");
    }

    public GameObject getPlayer()
    {
        return gameObjects.get(1);
    }

}
