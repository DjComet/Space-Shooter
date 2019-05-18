package com.libgdx.spaceshooter;

public class Wave {

    int numberOfEnemies;
    float timeToNextWave;
    enum TypeOfShip {simple, advanced}
    TypeOfShip typeOfShip;

    public Wave(int number, float time, TypeOfShip type )
    {
        numberOfEnemies = number;
        timeToNextWave = time;
        typeOfShip = type;
    }

    public void SpawnWave()
    {
        //somehow the time at which each wave spawns must be available
        for (int i = 0; i < numberOfEnemies; i++)
        {
            switch (typeOfShip)
            {
                case simple:
                    WorldController.instance.getCurrentLevel().Instantiate(new SimpleEnemy(randomPos(),50));
                    break;
                case advanced:
                    WorldController.instance.getCurrentLevel().Instantiate(new AdvancedEnemy(randomPos(),50, AdvancedEnemy.WaypointType.curveL));
                    break;
            }
        }

    }

    public float randomPos()
    {
        return -WorldController.instance.getCurrentLevel().background.width/2 + (float)Math.random() * WorldController.instance.getCurrentLevel().background.width;
    }



}
