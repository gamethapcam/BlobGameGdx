package com.stan.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.stan.game.Screens.PlayScreen;

/**
 * Created by stan on 5/07/16.
 */
public abstract class Enemy extends Sprite {

    protected World world;
    protected PlayScreen screen;
    public Body b2body;

    public Enemy(PlayScreen screen){
        this.world = screen.getWorld();
        this.screen = screen;
        defineEnemy();


    }

    public abstract void defineEnemy();

    public void draw(Batch batch){
        if((getX() != 0))
            super.draw(batch);
    }
}
