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

    public static Sound pShotN = Gdx.audio.newSound(Gdx.files.internal("PLAYER_SHOT.wav"));
    public static Sound pShotS = Gdx.audio.newSound(Gdx.files.internal("PLAYER_SHOTSPECIAL.wav"));
    public static Sound pBomb = Gdx.audio.newSound(Gdx.files.internal("PLAYER_BOMB.wav"));
    public static Sound pExp = Gdx.audio.newSound(Gdx.files.internal("PLAYER_BOMB.wav"));
    public static Sound pRespawn = Gdx.audio.newSound(Gdx.files.internal("PLAYER_RESPAWN.wav"));

    public static Sound seShot = Gdx.audio.newSound(Gdx.files.internal("SE_SHOT.wav"));
    public static Sound seExpl = Gdx.audio.newSound(Gdx.files.internal("EXPLOSION_SIMPLE.wav"));
    public static Sound aeShot = Gdx.audio.newSound(Gdx.files.internal("AE_SHOT.wav"));
    public static Sound aeExpl = Gdx.audio.newSound(Gdx.files.internal("EXPLOSION_ADVANCED.wav"));

    public static Sound ovniLaser = Gdx.audio.newSound(Gdx.files.internal("OVNI_LASERLOOP.wav"));
    public static Sound ovniNormal = Gdx.audio.newSound(Gdx.files.internal("OVNI_NORMAL.wav"));
    public static Sound ovniSoftExp = Gdx.audio.newSound(Gdx.files.internal("EXPLOSION_SOFTOVNI.wav"));
    public static Sound ovniHardExp = Gdx.audio.newSound(Gdx.files.internal("EXPLOSION_HARDOVNI.wav"));
    public static Sound ovniFinalExp = Gdx.audio.newSound(Gdx.files.internal("EXPLOSION_FINALOVNI.wav"));

    public static Sound uiHit = Gdx.audio.newSound(Gdx.files.internal("UI_HIT.wav"));
    public static Sound uiLifeLoss = Gdx.audio.newSound(Gdx.files.internal("UI_LIFELOSS.wav"));
    public static Sound uiMove = Gdx.audio.newSound(Gdx.files.internal("UI_MOVE.wav"));
    public static Sound uiSelect = Gdx.audio.newSound(Gdx.files.internal("UI_SELECT.wav"));



    public static void playSounds(int soundID){
        switch (soundID){
            case 1:
                pShotN.setVolume(1,0.5f);
                pShotN.play();
                break;
            case 2:
                pShotS.setVolume(2,4);
                pShotS.play();
                break;
            case 3:
                pBomb.play();
                break;
            case 4:
                pExp.play();
                break;
            case 5:
                pRespawn.play();
                break;
            case 6:
                seShot.setVolume(6,0.6f);
                seShot.play();
                break;
            case 7:
                seExpl.play();
                break;
            case 8:
                aeShot.setVolume(8,0.7f);
                aeShot.play();
                break;
            case 9:

                aeExpl.play();
                break;
            case 10:
                ovniLaser.play();
                ovniLaser.loop();
                break;
            case 11:
                ovniNormal.play();
                break;
            case 12:
                ovniSoftExp.play();
                break;
            case 13:
                ovniHardExp.play();
                break;
            case 14:
                ovniFinalExp.play();
                break;
            case 15:
                uiHit.play();
                break;
            case 16:
                uiLifeLoss.setVolume(16,1.5f);
                uiLifeLoss.play();
                break;
            case 17:
                uiMove.play();
                break;
            case 18:
                uiSelect.play();
                break;
            default:
                break;

        }
    }

    public static void stopLaser()
    {
        ovniLaser.stop();
    }

    public static void playMenuMusic()
    {
        menuTheme.play();
        menuTheme.setVolume(0.9f);
        menuTheme.setLooping(true);
    }

    public static void playVictoryMusic()
    {
        victorySong.play();
        victorySong.setLooping(false);
    }

    public static void playMainMusic(){
       principalTheme.setVolume(0.5f);
       principalTheme.play();
       principalTheme.setLooping(true);
    }
}
