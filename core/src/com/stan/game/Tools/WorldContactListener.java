package com.stan.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.stan.game.BlobGame;
import com.stan.game.Sprites.GoodBlob;

/**
 * Created by stan on 5/07/16.
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case BlobGame.GOOD_BLOB_BIT | BlobGame.BAD_BLOB_BIT:

                if (fixA.getFilterData().categoryBits == BlobGame.GOOD_BLOB_BIT) {
                    ((GoodBlob) fixA.getUserData()).onCollision();
                }
                else
                    ((GoodBlob) fixB.getUserData()).onCollision();
                break;
            case BlobGame.GOOD_BLOB_BIT | BlobGame.GROUND_BIT:

                if (fixA.getFilterData().categoryBits == BlobGame.GOOD_BLOB_BIT) {
                    ((GoodBlob) fixA.getUserData()).hitWall();
                }
                else
                    ((GoodBlob) fixB.getUserData()).hitWall();
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
