package com.libgdx.spaceshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;


public class ArcadeHandler implements ControllerListener {

    public WorldController wc;
    public MenuController mc;
    public float dead=0.15f;

    public ArcadeHandler()
    {
        if(MAIN_GAME.instance.getScreen() == MAIN_GAME.instance.menuScreen)
        {
            mc = MenuController.instance;
            wc = null;
        }
        else if(MAIN_GAME.instance.getScreen() == MAIN_GAME.instance.gameScreen)
        {
            wc = WorldController.instance;
            mc = null;
        }
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {

        if(wc!=null)
        {
            //CONTROLLER 1
            if (Controllers.getControllers().indexOf(controller, true) == 0) {
                if (buttonCode == 0)
                    wc.inputMgr.keyShootN = true;
                wc.inputMgr.keyShootNBool = true;

                if (buttonCode == 1)
                    wc.inputMgr.keyShootS = true;


                if (buttonCode == 9)
                    Gdx.app.exit();

                //man.switchColor(buttonCode);
                return true;
            }

            if (Controllers.getControllers().indexOf(controller, true) == 1) {
                if (buttonCode == 0)
                    wc.inputMgr.keyShootNP2 = true;
                wc.inputMgr.keyShootNBoolP2 = true;

                if (buttonCode == 1)
                    wc.inputMgr.keyShootSP2 = true;

                if (buttonCode == 9)
                    Gdx.app.exit();

                //man.switchColor(buttonCode);
                return true;
            }
        }
        if(mc!=null)
        {
            //CONTROLLER 1
            if (Controllers.getControllers().indexOf(controller, true) == 0) {
                if (buttonCode == 0)
                    mc.inputMgr.keyShootN = true;
                mc.inputMgr.keyShootNBool = true;

                if (buttonCode == 1)
                    mc.inputMgr.keyShootS = true;


                if (buttonCode == 9)
                    Gdx.app.exit();

                //man.switchColor(buttonCode);
                return true;
            }

            if (Controllers.getControllers().indexOf(controller, true) == 1) {
                if (buttonCode == 0)
                    mc.inputMgr.keyShootNP2 = true;
                mc.inputMgr.keyShootNBoolP2 = true;

                if (buttonCode == 1)
                    mc.inputMgr.keyShootSP2 = true;

                if (buttonCode == 9)
                    Gdx.app.exit();

                //man.switchColor(buttonCode);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        //Do nothing
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {

        if(wc!=null)
        {
            //Controller 1
            if(Controllers.getControllers().indexOf(controller,true) == 0){

                //y axis
                if(axisCode == 0){

                    //up
                    if(value <= -dead){
                        wc.inputMgr.keyUp = true;
                        return true;
                    }
                    //down
                    else if(value >=dead){
                        wc.inputMgr.keyDown = true;
                        return true;
                    }
                    //center
                    else if(value <dead && value>-dead){

                        return true;
                    }

                }
                //x axis
                else if(axisCode == 1){

                    //left
                    if(value <= -dead){
                        wc.inputMgr.keyLeft = true;
                        return true;
                    }
                    //right
                    else if(value >= dead){
                        wc.inputMgr.keyRight = true;
                        return true;
                    }
                    //center
                    else if(value <dead && value>-dead){
                        return true;
                    }

                }
            }

            //Controller 2
            if(Controllers.getControllers().indexOf(controller,true) == 1){

                //y axis
                if(axisCode == 0){

                    //up
                    if(value <= -dead){
                        wc.inputMgr.keyUpP2 = true;
                        return true;
                    }
                    //down
                    else if(value >= dead){
                        wc.inputMgr.keyDownP2 = true;
                        return true;
                    }
                    //center
                    else if(value <dead && value>-dead){

                        return true;
                    }

                }
                //x axis
                else if(axisCode == 1){

                    //left
                    if(value <= -dead){
                        wc.inputMgr.keyLeftP2 = true;
                        return true;
                    }
                    //right
                    else if(value >= dead){
                        wc.inputMgr.keyRightP2 = true;
                        return true;
                    }
                    //center
                    else if(value <dead && value>-dead){
                        return true;
                    }

                }
            }
        }


        if(mc!=null)
        {
            //Controller 1
            if(Controllers.getControllers().indexOf(controller,true) == 0){

                //y axis
                if(axisCode == 0){

                    //up
                    if(value <= -dead){
                        mc.inputMgr.keyUp = true;
                        return true;
                    }
                    //down
                    else if(value >=dead){
                        mc.inputMgr.keyDown = true;
                        return true;
                    }
                    //center
                    else if(value <dead && value>-dead){

                        return true;
                    }

                }
                //x axis
                else if(axisCode == 1){

                    //left
                    if(value <= -dead){
                        mc.inputMgr.keyLeft = true;
                        return true;
                    }
                    //right
                    else if(value >= dead){
                        mc.inputMgr.keyRight = true;
                        return true;
                    }
                    //center
                    else if(value <dead && value>-dead){
                        return true;
                    }

                }
            }

            //Controller 2
            if(Controllers.getControllers().indexOf(controller,true) == 1){

                //y axis
                if(axisCode == 0){

                    //up
                    if(value <= -dead){
                        mc.inputMgr.keyUpP2 = true;
                        return true;
                    }
                    //down
                    else if(value >= dead){
                        mc.inputMgr.keyDownP2 = true;
                        return true;
                    }
                    //center
                    else if(value <dead && value>-dead){

                        return true;
                    }

                }
                //x axis
                else if(axisCode == 1){

                    //left
                    if(value <= -dead){
                        mc.inputMgr.keyLeftP2 = true;
                        return true;
                    }
                    //right
                    else if(value >= dead){
                        mc.inputMgr.keyRightP2 = true;
                        return true;
                    }
                    //center
                    else if(value <dead && value>-dead){
                        return true;
                    }

                }
            }
        }





        return false;
    }

    @Override
    public void connected(Controller controller) {
        //Do nothing
    }

    @Override
    public void disconnected(Controller controller) {
        //Do nothing
    }
    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        return false;
    }
}
