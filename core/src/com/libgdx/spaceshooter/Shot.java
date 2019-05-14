package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.Iterator;


public class Shot extends GameObject{
    public int damage;
    public ShotType shotType;
    float lifeTimer = 0f;
    float lifeTime = 2f;
    private Vector2 speed = new Vector2(0f,0f);



    public Shot (ShotType st, float posX, float posY, float spd, int dmg, float rot)
    {
        position = new Vector2(posX, posY);
        rotation = rot;

        width = 1.0f;
        height = 1.0f;

        scale = new Vector2(1,1);
        damage = dmg;
        speed.y = spd;
        shotType = st;

        switch (shotType){
            case SE: tag = "ENEMY";
            break;
            case AE: tag = "ENEMY";
            break;
            case PLNORMAL: tag = "PLAYER";
            break;
            case PLSPECIAL:tag = "PLAYER";
        }

    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(Assets.getInstance().shotTexRegions[shotType.getValue()],position.x,position.y,0,0,width,height,scale.x,scale.y,rotation);
    }

    @Override
    public void update(float delta) {

        position.x += speed.x * delta;
        position.y += speed.y * delta;

        lifeTimer += delta;

        if(lifeTimer>lifeTime)
        {

           if(this.tag =="PLAYER")
           {
                Iterator<GameObject> iter = WorldController.instance.getCurrentLevel().playerGos.iterator();
                while (iter.hasNext())
                {
                    GameObject element = iter.next();
                    if(element == this)
                    {
                       WorldController.instance.getCurrentLevel().playerGos.remove(element);
                       WorldController.instance.getCurrentLevel().refresh();
                    }
                }
            }

            if(this.tag =="ENEMY")
            {
                Iterator<GameObject> iter = WorldController.instance.getCurrentLevel().enemyGos.iterator();
                while (iter.hasNext())
                {
                    GameObject element = iter.next();
                    if(element == this)
                    {
                        WorldController.instance.getCurrentLevel().enemyGos.remove(element);
                        WorldController.instance.getCurrentLevel().refresh();
                    }
                }
            }










        }
    }
}
