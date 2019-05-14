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



}
