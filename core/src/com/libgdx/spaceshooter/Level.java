package com.libgdx.spaceshooter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Level {

    //initialize and refresh gameobjects, rendering and updating.
    public GameObject background;
    public GameObject player;
    public ArrayList<GameObject> playerShots;
    public ArrayList<GameObject> enemyGos;
    public ArrayList<GameObject> enemyShots;
    public ArrayList<GameObject> defaultGos;


    public ArrayList<GameObject> toRemove;
    public ArrayList<GameObject> toAdd;
    public float timeToNextLevel = 7f;

    public WaveManager waveM;


    public float currentTime;

    public Level(GameObject bg)
    {
        background = bg;
        playerShots = new ArrayList<GameObject>();
        enemyGos = new ArrayList<GameObject>();
        enemyShots = new ArrayList<GameObject>();
        defaultGos = new ArrayList<GameObject>();

        toRemove = new ArrayList<GameObject>();
        toAdd = new ArrayList<GameObject>();
        currentTime = 0f;
        player = new Player(-4.5f,-4.5f);


        switch(WorldController.instance.currentLevel)
        {
            case 0: waveM = null;
            break;

            case 1: waveM = new WaveManager(1,10);
            break;

            case 2: waveM = new WaveManager(2,15);
            break;

            case 3: waveM = new WaveManager(3,20);
            break;

            case 4: waveM = new WaveManager(4, 35);
            break;

        }
    }

    public void update(float delta)
    {
        currentTime += delta;

        for (int i = 0; i< waveM.waves.size(); i++)
        {
            if(waveM.waves.get(i).spawnable && waveM.waves.get(i).timeToNextWave < currentTime)
            {
                System.out.println("Time For Next Wave: " + waveM.waves.get(i).timeToNextWave + ", CurrentTime: " + currentTime);
                waveM.waves.get(i).SpawnWave();
            }
        }


        updateArrays(delta);
        removeGos();
        addGos();

        if(waveM.waves.get((waveM.waves.size()-1)).timeToNextWave + timeToNextLevel < currentTime)
        {
            WorldController.instance.currentLevel++;
            System.out.println("Going to level " + WorldController.instance.currentLevel);
        }

    }

    void updateArrays(float delta)
    {
        background.update(delta);

        for (int i = 0; i< playerShots.size(); i++)
        {
            playerShots.get(i).update(delta);
        }
        for (int i = 0; i< enemyShots.size(); i++)
        {
            enemyShots.get(i).update(delta);
        }
        for (int i = 0; i< enemyGos.size(); i++)
        {
            enemyGos.get(i).update(delta);
        }

        player.update(delta);

        for (int i = 0; i< defaultGos.size(); i++)
        {
            defaultGos.get(i).update(delta);
        }
    }

    void addGos()
    {
        for(int i = 0; i<toAdd.size(); i++)
        {
            if(toAdd.get(i).tag == "PLAYERSHOT")
            {
                playerShots.add(0,toAdd.get(i));
            }
            else if(toAdd.get(i).tag == "ENEMYSHOT")
            {
                enemyShots.add(toAdd.get(i));
            }
            else if(toAdd.get(i).tag == "ENEMY")
            {
                enemyGos.add(toAdd.get(i));
            }
            else if(toAdd.get(i).tag == "DEFAULT")
            {
                defaultGos.add(toAdd.get(i));
            }
        }
        toAdd.clear();
    }

    void removeGos()
    {
        for(int i = toRemove.size()-1; i>=0; i--)
        {
            if(toRemove.get(i).tag == "PLAYERSHOT")
            {
                playerShots.remove(toRemove.get(i));
            }
            else if(toRemove.get(i).tag == "ENEMYSHOT")
            {
                enemyShots.remove(toRemove.get(i));
            }
            else if(toRemove.get(i).tag == "ENEMY")
            {
                enemyGos.remove(toRemove.get(i));
            }
            else if(toRemove.get(i).tag == "DEFAULT")
            {
                defaultGos.remove(toRemove.get(i));
            }
        }
        toRemove.clear();
    }




    public void Instantiate(GameObject gameObject)
    {
        toAdd.add(gameObject);
        toAdd.get(toAdd.size()-1).position = new Vector2(toAdd.get(toAdd.size()-1).position.x-toAdd.get(toAdd.size()-1).width/2,
                                                         toAdd.get(toAdd.size()-1).position.y-toAdd.get(toAdd.size()-1).width/2);
    }

    public void Despawn(GameObject gameObject)
    {
        toRemove.add(gameObject);
    }

    void centerObject(ArrayList<GameObject> arr, int index)
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
        return player;
    }
    public GameObject getBg()
    {
        return background;
    }

}
