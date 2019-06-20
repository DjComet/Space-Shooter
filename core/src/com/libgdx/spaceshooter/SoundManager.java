package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    //FOR MUSIC THEME
    public static Music principalTheme = Gdx.audio.newMusic(Gdx.files.internal("Hideki Naganuma - AINT NOTHIN LIKE A FUNKY BEAT.mp3"));
    public static Music menuTheme = Gdx.audio.newMusic(Gdx.files.internal("NPC - Pulsar Miracle.mp3"));
    public static Music victorySong = Gdx.audio.newMusic(Gdx.files.internal("victoryMusic.mp3"));


    //FOR SIMPLE SOUNDS
    //public static Sound Shoot = Gdx.audio.newSound(Gdx.files.internal(""));



    public static void reproduceSounds(int soundID){
        /*switch (soundID){
            case 1:
                actionDone.play();
                break;
            case 2:
                accelProcess.play();
                break;
            case 3:
                endTurn.play();
                break;
            case 4:
                resourceObtained.setVolume(4,0.5f);
                resourceObtained.play();
                break;
            case 5:
                buildDone.play();
                break;
            case 6:
                walk.play();
                break;
            default:
                break;

        }*/
    }

    public static void playMenuMusic()
    {
        menuTheme.play();
        menuTheme.setVolume(1.5f);
        menuTheme.setLooping(true);
    }

    public static void playVictoryMusic()
    {
        victorySong.play();
        victorySong.setLooping(false);
    }

    public static void playMainMusic(){
       principalTheme.play();
       principalTheme.setVolume(1.5f);
       principalTheme.setLooping(true);
    }
}
