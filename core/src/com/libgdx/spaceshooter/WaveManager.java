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


    public WaveManager(int diff, int totalW)
    {
        waves = new ArrayList<Wave>();
        difficulty = diff;
        totalWaves = totalW;

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
                    shipSwitchInterval = 5 + (int) Math.random() * 7;//4 to seven rounds of simple enemies between one round of advanced enemies
                    break;

                case 2:
                    enemies = 3 + (int) Math.random() * 7;
                    timeBetween = 1.8f + (float) Math.random() * 4.2f;
                    shipSwitchInterval = 4 + (int) Math.random() * 5;
                    break;

                case 3:
                    enemies = 4 + (int) Math.random() * 9;
                    timeBetween = 1 + (float) Math.random() * 3.5f;
                    shipSwitchInterval = 3 + (int) Math.random() * 4;
                    break;

                case 4:
                    enemies = 6 + (int) Math.random() * 12;
                    timeBetween = 0.8f + (float) Math.random() * 2.5f;
                    shipSwitchInterval = 2 + (int) Math.random() * 3;
                    break;

            }

                //adds a wave to the arrayList at a certain second.
                if (shipSwitchInterval >= specialWaveCount)
                {
                    waves.add(new Wave(enemies, getTimeToSpawn(timeBetween), Wave.TypeOfShip.advanced));
                    specialWaveCount = 0;
                }
                else
                {
                    waves.add(new Wave(enemies, getTimeToSpawn(timeBetween), Wave.TypeOfShip.simple));
                }



        }
    }


    float getTimeToSpawn(float tb)
    {
        float totalTime = 0;
        for (int i = 0; i<waves.size();i++)
        {
            totalTime += waves.get(i).timeToNextWave;//gets the total time elapsed between all waves
        }
        totalTime += tb;//and adds the time for the next wave to spawn
        return totalTime;
    }


}
