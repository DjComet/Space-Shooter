package com.libgdx.spaceshooter.HUD;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;


public class HUD extends HUDElement {

    ArrayList<TextButton> textButtons;
    ArrayList<HUDElement> elements;

    public HUD(){
        textButtons = new ArrayList<TextButton>();
        elements = new ArrayList<HUDElement>();
    }

    public void add(TextButton go){
        textButtons.add(go);
    }
    public void addHe(HUDElement he){elements.add(he);}

    @Override
    public void render(SpriteBatch batch) {
        for(HUDElement he : textButtons){
            he.render(batch);
        }
        for(HUDElement he: elements)
        {
            he.render(batch);
        }
    }

    public boolean click(float x, float y) {

        for(TextButton b : textButtons){
            if(b.contains(x,y)){
                b.click();
                return true;
            }
        }
        return false;
    }
}
