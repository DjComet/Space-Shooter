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
        for (int i = 0; i < numberOfEnemies; i++)
        {
            switch (typeOfShip)
            {
                case simple:
                    WorldController.instance.getCurrentLevel().Instantiate(new SimpleEnemy(0,0));
                    break;
                case advanced:
                    WorldController.instance.getCurrentLevel().Instantiate(new AdvancedEnemy(0,0, AdvancedEnemy.WaypointType.curveL));
                    break;
            }
        }

    }



}
