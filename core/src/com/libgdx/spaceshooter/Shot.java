package com.libgdx.spaceshooter;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Shot extends GameObject{
    public int damage;
    public ShotType shotType;
    float lifeTimer = 0f;
    float lifeTime = 4f;
    private Vector2 speed = new Vector2(0f,0f);



    public Shot (ShotType st, float posX, float posY, float spd, int dmg, float rot)
    {
        position = new Vector2(posX, posY);
        rotation = rot;

        width = 16;
        height = 16;

        scale = new Vector2(0.5f,0.5f);
        damage = dmg;
        speed.y = spd;
        shotType = st;
        rectangle = new Rectangle();

        switch (shotType){
            case SE: layerTag = Layer.LayerNames.ENEMYSHOT;
            break;
            case AE: layerTag = Layer.LayerNames.ENEMYSHOT;
            break;
            case OVNINORMAL: layerTag = Layer.LayerNames.ENEMYSHOT;
            break;
            case OVNILASER: layerTag = Layer.LayerNames.ENEMYSHOT;
            break;
            case PLNORMAL: layerTag = Layer.LayerNames.PLAYERSHOT;
            break;
            case PLSPECIAL: layerTag = Layer.LayerNames.PLAYERSHOT;
            break;
        }

        if(this.shotType == ShotType.PLSPECIAL || this.shotType == ShotType.OVNINORMAL)
        {
            scale = new Vector2(1.5f,1.5f);
            damage = 5;
            lifeTime = 10f;
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
        if(rotation==0)
        rectangle.set(position.x, position.y, width*scale.x, height*scale.y);
        else rectangle.set(position.x-width*scale.x, position.y-height*scale.y, width,height);

        lifeTimer += delta;

        if(this.shotType == ShotType.PLSPECIAL)
        {
            for (GameObject enemy: WorldController.instance.getCurrentLevel().getLayerList(Layer.LayerNames.ENEMY))
            {
                if(CollisionHelper.CheckCollision(this, enemy))
                {
                    WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x+(width/2)-32, position.y + (height/2)-32, true));
                    WorldController.instance.getCurrentLevel().Despawn(this);
                }
            }
        }

        if(lifeTimer>lifeTime)
        {
            if(this.shotType == ShotType.PLSPECIAL)
            {

                WorldController.instance.getCurrentLevel().Instantiate(new Explosion(position.x+(width/2)-32, position.y + (height/2)-32, true));
            }

           WorldController.instance.getCurrentLevel().Despawn(this);


        }
    }
}
