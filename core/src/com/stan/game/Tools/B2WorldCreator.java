package com.stan.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.stan.game.BlobGame;
import com.stan.game.Screens.PlayScreen;

/**
 * Created by stan on 4/07/16.
 */
public class B2WorldCreator {
    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / BlobGame.PPM, (rect.getY() + rect.getHeight() / 2) / BlobGame.PPM);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / BlobGame.PPM, rect.getHeight() / 2 / BlobGame.PPM);
            fdef.shape = shape;
            fdef.restitution = BlobGame.GROUND_RESTITUTION;
            body.createFixture(fdef);
        }
    }
}
