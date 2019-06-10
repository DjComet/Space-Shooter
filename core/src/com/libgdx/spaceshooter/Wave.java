package com.libgdx.spaceshooter;

import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

public class Wave {

    public boolean spawnable = true;
    int numberOfEnemies;
    float timeToNextWave;
    enum TypeOfShip {simple, advanced, ovni}
    TypeOfShip typeOfShip;
    Random random = new Random();
    float padding = 100f;


    float posX = 0;
    float posY = 405;

    public Wave(int number, float time, TypeOfShip type )
    {
        numberOfEnemies = number;
        timeToNextWave = time;
        typeOfShip = type;
        spawnable = true;

    }

    public void SpawnWave()
    {


        for (int i = 0; i < numberOfEnemies; i++)
        {

            switch (typeOfShip)
            {
                case simple:
                    posX = randomPos(true);
                    while (checkForCollisionOnSpawn(posX))
                    {
                        posX = randomPos(true);
                        posY += 8f;
                    }
                    WorldController.instance.getCurrentLevel().Instantiate(new SimpleEnemy(posX,posY));
                    break;
                case advanced:
                    posY = randomPos(false);
                    while (checkForCollisionOnSpawn(posX))
                    {
                        posY = randomPos(false);
                    }
                    WorldController.instance.getCurrentLevel().Instantiate(new AdvancedEnemy(posX,posY));
                    break;
            }
        }
        spawnable = false;

    }


    boolean checkForCollisionOnSpawn(float pos)
    {
        boolean temp = false;
        for(GameObject enemy: WorldController.instance.getCurrentLevel().getLayerList(Layer.LayerNames.ENEMY))
        {
            temp = CollisionHelper.CheckCollision(new SimpleEnemy(pos,50), enemy);
            if(temp) continue;
        }
        return temp;
    }

    public float randomPos(boolean horizontal)
    {
        float max =  (horizontal ? WorldController.instance.getCurrentLevel().getBg().width/2 - padding : posY+10);
        float min =  (horizontal ? -WorldController.instance.getCurrentLevel().getBg().width/2 +padding : posY);
        float position = (random.nextFloat() * ((max - min) + 1)) + min;

        return position;
    }



}
