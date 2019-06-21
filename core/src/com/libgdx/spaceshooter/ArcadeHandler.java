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


    public float dead=0.3f;
    InputManager inputMgr;

    public ArcadeHandler(InputManager imgr)
    {
        inputMgr = imgr;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {


        //CONTROLLER 1
        if (Controllers.getControllers().indexOf(controller, true) == 0) {
            if (buttonCode == 0)
            {
                inputMgr.keyShootN = true;
                inputMgr.keyShootNBool = true;
            }


            if (buttonCode == 1)
                inputMgr.keyShootS = true;


            if (buttonCode == 9)
                Gdx.app.exit();


            //man.switchColor(buttonCode);
            return true;
        }

        if (Controllers.getControllers().indexOf(controller, true) == 1) {
            if (buttonCode == 0)
            {
                inputMgr.keyShootNP2 = true;
                inputMgr.keyShootNBoolP2 = true;
            }

            if (buttonCode == 1)
                inputMgr.keyShootSP2 = true;

            if (buttonCode == 9)
                Gdx.app.exit();



            //man.switchColor(buttonCode);
            return true;
        }

        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {

        //CONTROLLER 1
        if (Controllers.getControllers().indexOf(controller, true) == 0) {
            if (buttonCode == 0)
            {
                inputMgr.keyShootN = false;
                inputMgr.keyShootNBool = false;
            }

            if (buttonCode == 1)
                inputMgr.keyShootS = false;
            //man.switchColor(buttonCode);
            return true;
        }

        if (Controllers.getControllers().indexOf(controller, true) == 1) {
            if (buttonCode == 0)
            {
                inputMgr.keyShootNP2 = false;
                inputMgr.keyShootNBoolP2 = false;
            }

            if (buttonCode == 1)
                inputMgr.keyShootSP2 = false;
            //man.switchColor(buttonCode);
            return false;
        }

        return true;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {


        //Controller 1
        if(Controllers.getControllers().indexOf(controller,true) == 0){

            //y axis
            if(axisCode == 0){

                //up
                if(value <= -dead){
                    inputMgr.keyUp = true;
                    return true;
                }
                //down
                else if(value >=dead){
                    inputMgr.keyDown = true;
                    return true;
                }
                //center
                else if(value <dead && value>-dead){

                    inputMgr.keyUp = false;
                    inputMgr.keyDown = false;
                    return true;
                }

            }
            //x axis
            else if(axisCode == 1){

                //left
                if(value <= -dead){
                    inputMgr.keyLeft = true;
                    return true;
                }
                //right
                else if(value >= dead){
                    inputMgr.keyRight = true;
                    return true;
                }
                //center
                else if(value <dead && value>-dead){
                    inputMgr.keyLeft = false;
                    inputMgr.keyRight = false;

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
                    inputMgr.keyUpP2 = true;
                    return true;
                }
                //down
                else if(value >= dead){
                    inputMgr.keyDownP2 = true;
                    return true;
                }
                //center
                else if(value <dead && value>-dead){

                    inputMgr.keyUpP2 = false;
                    inputMgr.keyDownP2 = false;
                    return true;
                }

            }
            //x axis
            else if(axisCode == 1){

                //left
                if(value <= -dead){
                    inputMgr.keyLeftP2 = true;
                    return true;
                }
                //right
                else if(value >= dead){
                    inputMgr.keyRightP2 = true;
                    return true;
                }
                //center
                else if(value <dead && value>-dead){
                    inputMgr.keyLeftP2 = false;
                    inputMgr.keyRightP2 = false;
                    return true;
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
