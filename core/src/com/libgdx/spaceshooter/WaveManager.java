package com.libgdx.spaceshooter;

import java.util.ArrayList;

public class WaveManager {

    ArrayList<Wave> waves;
    int difficulty;
    int totalWaves;

    int enemies;
    int advEnemies;
    float timeBetween;
    int shipSwitchInterval;

    int specialWaveCount =0;


    public WaveManager(int diff, int totalW)
    {
        waves = new ArrayList<Wave>();
        difficulty = diff;


        totalWaves = totalW;
        if(difficulty == 5)
            totalWaves = 1;

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
                    enemies = (int) Math.random() * 8 + 1;//1 to 5 enemies
                    advEnemies = 2;
                    timeBetween = 2 + (float) Math.random() * 5;//2 to 5 seconds between waves
                    shipSwitchInterval = 5 + (int) Math.random() * 7;//4 to seven rounds of simple enemies between one round of advanced enemies
                    break;

                case 2:
                    enemies = (int) Math.random() * 7 + 3;
                    advEnemies = (int) Math.random() * 2 + 1;
                    timeBetween =  (float) Math.random() * 4.2f + 1.82f;
                    shipSwitchInterval =(int) Math.random() * 5 + 4;
                    break;

                case 3:
                    enemies = (int) Math.random() * 9 + 4;
                    advEnemies = (int) Math.random() * 3 + 1;
                    timeBetween = (float) Math.random() * 3.5f + 1;
                    shipSwitchInterval =(int) Math.random() * 4 + 3;
                    break;

                case 4:
                    enemies =(int) Math.random() * 12 + 6;
                    advEnemies = (int) Math.random() * 5 + 1;
                    timeBetween = (float) Math.random() * 2.5f + 0.8f;
                    shipSwitchInterval = (int) Math.random() * 3 + 2;
                    break;

                case 5:
                    enemies = 0;
                    advEnemies = 0;
                    timeBetween = 180;
                    shipSwitchInterval = 0;
                    break;

            }
                System.out.println("TimeBetween: " + timeBetween);
                //adds a wave to the arrayList at a certain second.
                if(difficulty == 5)
                {
                    waves.add(new Wave(1, 0, Wave.TypeOfShip.ovni));
                    waves.add(new Wave(0,180, Wave.TypeOfShip.simple));
                }
                else if (shipSwitchInterval <= specialWaveCount)
                {
                    System.out.println("Wave of Advanced added" );
                    waves.add(new Wave(advEnemies, getTimeToSpawn(timeBetween), Wave.TypeOfShip.advanced));
                    specialWaveCount = 0;
                }
                else
                {
                    System.out.println("wave of simple added");
                    waves.add(new Wave(enemies, getTimeToSpawn(timeBetween), Wave.TypeOfShip.simple));
                }





        }
    }


    float getTimeToSpawn(float tb)
    {
        float totalTime = 0;

        if(waves.size()>0)
        {
            totalTime += waves.get(waves.size()-1).timeToNextWave;
        }


        totalTime += tb;//and adds the time for the next wave to spawn
        return totalTime;
    }


}
