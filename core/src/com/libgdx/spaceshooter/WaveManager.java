package com.libgdx.spaceshooter;

import java.util.ArrayList;

public class WaveManager {

    ArrayList<Wave> waves;
    int difficulty;
    int totalWaves;

    int enemies;
    float timeBetween;
    int shipSwitchInterval;

    int specialWaveCount =0;

    public WaveManager()
    {
        //create the waves here based on difficulty and totalWaves parameter

            generateLevelWaves();

    }


    void generateLevelWaves()
    {
        for (int i = 0; i<totalWaves;i++)
        {
            specialWaveCount ++;

            switch (difficulty)
            {
                case 1:
                    enemies = 1 + (int) Math.random() * 5;//1 to 5 enemies
                    timeBetween = 2 + (float) Math.random() * 5;//2 to 5 seconds between waves
                    shipSwitchInterval = 4 + (int) Math.random() * 7;//4 to seven rounds of simple enemies between one round of advanced enemies
                    break;

            }

            if (shipSwitchInterval >= specialWaveCount)
            {
                waves.add(new Wave(enemies, timeBetween, Wave.TypeOfShip.advanced));
                specialWaveCount = 0;
            }
            else
            {
                waves.add(new Wave(enemies, timeBetween, Wave.TypeOfShip.simple));
            }
        }
    }




}
