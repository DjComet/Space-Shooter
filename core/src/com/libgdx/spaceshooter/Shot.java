package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


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
        rectangle = new Rectangle();

        switch (shotType){
            case SE: layerTag = Layer.LayerNames.ENEMYSHOT;
            break;
            case AE: layerTag = Layer.LayerNames.ENEMYSHOT;
            break;
            case PLNORMAL: layerTag = Layer.LayerNames.PLAYERSHOT;
            break;
            case PLSPECIAL: layerTag = Layer.LayerNames.PLAYERSHOT;
            break;
        }

        if(this.shotType == ShotType.PLSPECIAL)
        {
            scale = new Vector2(3,3);
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
            if(this.shotType == ShotType.PLSPECIAL)
            {
                WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x+(width/2)+1, position.y));
            }

           WorldController.instance.getCurrentLevel().Despawn(this);


        }
    }
}
