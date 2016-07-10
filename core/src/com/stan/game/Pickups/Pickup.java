package com.stan.game.Pickups;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.stan.game.BlobGame;
import com.stan.game.Screens.PlayScreen;

import java.util.Random;

/**
 * Created by stan on 10/07/16.
 */
public abstract class Pickup extends Sprite {

    protected World world;
    protected Screen screen;
    protected Body b2body;
    protected float stateTimer;
    protected boolean setToDestroy;
    protected boolean destroyed;


    public Pickup(PlayScreen screen){
        super();
        this.destroyed = false;
        this.setToDestroy = false;
        this.stateTimer = 0;
        this.world = screen.getWorld();
        this.screen = screen;
        definePickup();
        setBounds(0, 0, 27 / BlobGame.PPM, 27 / BlobGame.PPM);
    }

    public void definePickup(){
        BodyDef bdef = new BodyDef();
        Random rand = new Random();
        bdef.position.set((rand.nextInt(BlobGame.V_WIDTH - 100) + 50) / BlobGame.PPM, (rand.nextInt(BlobGame.V_HEIGHT - 100) + 50) / BlobGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(13 / BlobGame.PPM);
        fdef.filter.categoryBits = BlobGame.PICKUP_BIT;
        fdef.filter.maskBits = BlobGame.GOOD_BLOB_BIT | BlobGame.GROUND_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void update(float dt){
        this.stateTimer += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        if(this.setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            stateTimer = 0;
        }
    }

    public void onPickedUp(){
        BlobGame.manager.get("pickup.wav", Sound.class).play(.2f);
        this.setToDestroy = true;
    }

    public void draw(Batch batch){
        if(!destroyed && (getX() != 0))
            super.draw(batch);
    }


}
