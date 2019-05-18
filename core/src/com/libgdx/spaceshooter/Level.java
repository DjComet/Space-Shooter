package com.libgdx.spaceshooter;

import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;
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

    public float currentTime;

    public Level(GameObject bg)
    {
        background = bg;
        playerGos = new CopyOnWriteArrayList<GameObject>();
        enemyGos = new CopyOnWriteArrayList<GameObject>();
        defaultGos = new CopyOnWriteArrayList<GameObject>();
        gameObjects = new CopyOnWriteArrayList<GameObject>();
        currentTime = 0f;
        switch(WorldController.instance.currentLevel)
        {
            case 0: waveM = new WaveManager(1,5);
            break;

            case 1: waveM = new WaveManager(3,15);

        }
    }

    public void update(float deltaTime)
    {
        currentTime += deltaTime;

        for (Wave wave: waveM.waves)
        {
            if(wave.timeToNextWave > currentTime)
            {
                wave.SpawnWave();
            }
        }


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
        gameObjects = combine(gameObjects, enemyGos);
        gameObjects = combine(gameObjects, playerGos);
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
            playerGos.add(0,gameObject);
            centerObject(playerGos, 0);
        }
        else if(gameObject.tag == "ENEMY")
        {
            enemyGos.add(gameObject);
            centerObject(enemyGos, enemyGos.size()-1);
        }
        else
        {
            defaultGos.add(gameObject);
            centerObject(defaultGos, enemyGos.size()-1);
        }

        refresh();




    }

    void centerObject(CopyOnWriteArrayList<GameObject> arr, int index)
    {

        if(index < arr.size())
        {
            arr.get(index).position = new Vector2(arr.get(index).position.x-arr.get(index).width/2,arr.get(index).position.y-arr.get(index).width/2);

        }
        else
            System.out.println("The index is out of bounds");
    }

    public GameObject getPlayer()
    {
        return playerGos.get(playerGos.size()-1);
    }

}
