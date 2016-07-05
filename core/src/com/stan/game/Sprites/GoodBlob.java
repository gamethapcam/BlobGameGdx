package com.stan.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.stan.game.Screens.PlayScreen;

import aurelienribon.tweenengine.TweenManager;

/**
 * Created by stan on 3/07/16.
 */
public class GoodBlob extends Sprite {
    public World world;
    public Body b2body;
    public PlayScreen screen;
    private Texture goodBlobSmall;
    private float stateTimer;
    private float rotation;

    public GoodBlob(World world, PlayScreen screen) {
        super();

        this.world = world;
        this.rotation = 0;
        stateTimer = 0;
        defineGoodBlob();
        goodBlobSmall = new Texture("blob_1.png");
        setBounds(0, 0, 27, 27);
        setRegion(goodBlobSmall);
    }

    public void update(float dt) {
        setOrigin(getWidth() / 2, getHeight() / 2);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRotation(getAngle());

        Gdx.app.debug("this", "shown");
    }

    public float getAngle() {
        return this.rotation;
    }

    public void setAngle(float rotation){
        this.rotation = rotation;
    }

    public void defineGoodBlob() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(100, 100);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(13);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
