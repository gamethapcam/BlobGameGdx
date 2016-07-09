package com.stan.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.stan.game.BlobGame;
import com.stan.game.Screens.PlayScreen;

import java.util.Random;

/**
 * Created by stan on 5/07/16.
 */
public class BadBlob extends Enemy {

    private float stateTimer;
    private Texture texture;
    public BadBlob(PlayScreen screen) {
        super(screen, 100 / BlobGame.PPM, 50 / BlobGame.PPM);
        stateTimer = 0;
        texture = new Texture("badblob_1.png");
        setBounds(getX(), getY(), 27 / BlobGame.PPM, 27 / BlobGame.PPM);
        setRegion(texture);

    }

    public void update(float dt){
        stateTimer += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
    }

    @Override
    public void defineEnemy() {
        BodyDef bdef = new BodyDef();
        Random rand = new Random();

        int  n = rand.nextInt(BlobGame.V_WIDTH) + 1;
        bdef.position.set((rand.nextInt(BlobGame.V_WIDTH - 100) + 50) / BlobGame.PPM, (rand.nextInt(BlobGame.V_HEIGHT - 100) + 50) / BlobGame.PPM);
        bdef.linearVelocity.set((rand.nextInt(50) - 25), (rand.nextInt(50) - 25));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(13 / BlobGame.PPM);
        fdef.filter.categoryBits = BlobGame.BAD_BLOB_BIT;
        fdef.filter.maskBits = BlobGame.GOOD_BLOB_BIT | BlobGame.GROUND_BIT;
        fdef.shape = shape;
        fdef.restitution = BlobGame.BAD_BLOB_RESTITUTION;
        b2body.createFixture(fdef);

    }
}
