package com.libgdx.spaceshooter;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Level {

    //initialize and refresh gameobjects, rendering and updating.

    public ArrayList<Layer>Layers;


    public ArrayList<GameObject> toRemove;
    public ArrayList<GameObject> toAdd;
    public float timeToNextLevel = 8f;

    public WaveManager waveM;


    public float currentTime;

    public Level(int difficulty) //If difficulty is 0, this is a menu.
    {
        Layers = new ArrayList<Layer>();
        Layers.add(new Layer(Layer.LayerNames.BACKGROUND)); //0 BG
        Layers.add(new Layer(Layer.LayerNames.ENEMY));      //1 ENEMIES
        Layers.add(new Layer(Layer.LayerNames.ENEMYSHOT));  //2 ENEMYSHOTS
        Layers.add(new Layer(Layer.LayerNames.PLAYERSHOT)); //3 PLSHOTS
        Layers.add(new Layer(Layer.LayerNames.PLAYER));     //4 PLAYER
        Layers.add(new Layer(Layer.LayerNames.EXPLOSION));    //5 EXPLOSION

        Layers.get(0).list.add(new Background());
        Layers.get(0).list.add(new Canyon(-getBg().width/2, -200,false));
        Layers.get(0).list.add(new Canyon(getBg().width/2-32, -200,true));


        if(MAIN_GAME.instance.getScreen() == MAIN_GAME.instance.gameScreen && MAIN_GAME.instance.gameScreen.twoPlayers)
        {
            Layers.get(4).list.add(new Player(-80f,-16f));
            Layers.get(4).list.add(new Player(48f,-16f));
            Player temp = (Player)Layers.get(4).list.get(1);
            temp.secondPlayer = true;
        }
        else if(MAIN_GAME.instance.getScreen() == MAIN_GAME.instance.gameScreen)Layers.get(4).list.add(new Player(-16f,-16f));



        toRemove = new ArrayList<GameObject>();
        toAdd = new ArrayList<GameObject>();
        currentTime = 0f;


        if(MAIN_GAME.instance.getScreen() == MAIN_GAME.instance.gameScreen)
        {
            switch (WorldController.instance.currentLevel)
            {
                case 0:
                    waveM = new WaveManager(difficulty, 7);
                    break;

                case 1:
                    waveM = new WaveManager(difficulty, 15);
                    break;

                case 2:
                    waveM = new WaveManager(difficulty, 20);
                    break;

                case 3:
                    waveM = new WaveManager(difficulty, 30);
                    break;

                case 4:
                    waveM = new WaveManager(5, 1);//ovni
                    break;

            }
        }
        else { /*waveM = new WaveManager(4, 50); */}

    }

    public void update(float delta)
    {
        currentTime += delta;

        if(waveM != null)
        {
            for (int i = 0; i < waveM.waves.size(); i++) {
                if (waveM.waves.get(i).spawnable && waveM.waves.get(i).timeToNextWave < currentTime) {
                    System.out.println("Time For Next Wave: " + waveM.waves.get(i).timeToNextWave + ", CurrentTime: " + currentTime);
                    waveM.waves.get(i).SpawnWave();
                }
            }
        }

        updateLists(delta);
        removeGos();
        addGos();

        if(waveM!=null && waveM.waves.get((waveM.waves.size()-1)).timeToNextWave + timeToNextLevel < currentTime)
        {
            WorldController.instance.currentLevel++;
            System.out.println("Going to level " + WorldController.instance.currentLevel);
        }
        if(MAIN_GAME.instance.getScreen() == MAIN_GAME.instance.menuScreen)
        {
            MenuController.instance.inputMgr.resetInputs();
        }
        else
        {
            WorldController.instance.inputMgr.resetInputs();
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
        //toAdd.get(toAdd.size()-1).position = new Vector2(toAdd.get(toAdd.size()-1).position.x-toAdd.get(toAdd.size()-1).width/2,
                                                         //toAdd.get(toAdd.size()-1).position.y-toAdd.get(toAdd.size()-1).width/2);
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

    public Player getPlayer()
    {
        Player go = null;
        for (Layer L: Layers)
        {
            if(L.name == Layer.LayerNames.PLAYER)
            {
                if(L.list.size()!=0)
                go = (Player)L.list.get(0);
            }
        }
        if(go==null) System.out.println("There is no player object");
        return go;
    }

    public Player getPlayer2()
    {
        Player go = null;
        for (Layer L: Layers)
        {
            if(L.name == Layer.LayerNames.PLAYER)
            {
                if(L.list.size()!=0)
                go = (Player)L.list.get(1);
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
                if(L.list.size()!=0)
                go = L.list.get(0);
            }
        }
        if(go==null) System.out.println("There is no background object");
        return go;
    }

}
