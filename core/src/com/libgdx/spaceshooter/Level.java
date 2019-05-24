package com.libgdx.spaceshooter;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Level {

    //initialize and refresh gameobjects, rendering and updating.

    public ArrayList<Layer>Layers;



    public ArrayList<GameObject> toRemove;
    public ArrayList<GameObject> toAdd;
    public float timeToNextLevel = 7f;

    public WaveManager waveM;


    public float currentTime;

    public Level(GameObject bg)
    {
        Layers = new ArrayList<Layer>();
        Layers.add(new Layer(Layer.LayerNames.BACKGROUND)); //0 BG
        Layers.add(new Layer(Layer.LayerNames.PLAYERSHOT)); //1 PLSHOTS
        Layers.add(new Layer(Layer.LayerNames.ENEMY));      //2 ENEMIES
        Layers.add(new Layer(Layer.LayerNames.ENEMYSHOT));  //3 ENEMYSHOTS
        Layers.add(new Layer(Layer.LayerNames.PLAYER));     //4 PLAYER
        Layers.add(new Layer(Layer.LayerNames.DEFAULT));    //5 DEFAULT

        Layers.get(0).list.add(bg);
        Layers.get(4).list.add(new Player(-4.5f,-4.5f));

        toRemove = new ArrayList<GameObject>();
        toAdd = new ArrayList<GameObject>();
        currentTime = 0f;



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


        updateLists(delta);
        removeGos();
        addGos();

        if(waveM.waves.get((waveM.waves.size()-1)).timeToNextWave + timeToNextLevel < currentTime)
        {
            WorldController.instance.currentLevel++;
            System.out.println("Going to level " + WorldController.instance.currentLevel);
        }

    }

    void updateLists(float delta)
    {

        for(int i = 0; i<Layers.size(); i++)
        {
            for(int j = 0; j<Layers.get(i).list.size(); j++)
            {
                Layers.get(i).list.get(j).update(delta);
            }
        }
    }

    void addGos()
    {
        for(int i = 0; i<toAdd.size(); i++)
        {
            for(Layer L:Layers)
            {
                if(L.name == toAdd.get(i).layerTag)
                {
                    L.list.add((toAdd.get(i)));
                }
            }
        }

        toAdd.clear();
    }

    void removeGos()
    {
        for(int i = toRemove.size()-1; i>=0; i--)
        {
            for(Layer L:Layers)
            {
                if(L.name == toRemove.get(i).layerTag)
                {
                    L.list.remove((toRemove.get(i)));
                }
            }
        }
        toRemove.clear();
    }


    ArrayList<GameObject> getLayerList(Layer.LayerNames _name)
    {
        ArrayList<GameObject> goList = null;
        for(Layer L:Layers)
        {
            if(L.name == _name)
            {
                goList = L.list;
            }
        }
        if(goList == null) System.out.println("Oh no the list you want is null! Check your tags or if that list exists.");
        return goList;
    }


    public void Instantiate(GameObject gameObject)
    {
        toAdd.add(gameObject);
        //Center new object:
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
        GameObject go = null;
        for (Layer L: Layers)
        {
            if(L.name == Layer.LayerNames.PLAYER)
            {
                go = L.list.get(0);
            }
        }
        if(go==null) System.out.println("There is no player object");
        return go;
    }
    public GameObject getBg()
    {
        GameObject go = null;
        for (Layer L: Layers)
        {
            if(L.name == Layer.LayerNames.BACKGROUND)
            {
                go = L.list.get(0);
            }
        }
        if(go==null) System.out.println("There is no background object");
        return go;
    }

}
