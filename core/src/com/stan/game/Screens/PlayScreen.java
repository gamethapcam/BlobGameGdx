package com.stan.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.stan.game.BlobGame;
import com.stan.game.Scenes.Hud;
import com.stan.game.Sprites.GoodBlob;

import java.sql.Blob;

/**
 * Created by stan on 3/07/16.
 */
public class PlayScreen implements Screen {
    private BlobGame game;
    private OrthographicCamera gamecam;
    private FitViewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private GoodBlob player;


    public PlayScreen(BlobGame game){
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(BlobGame.V_WIDTH, BlobGame.V_HEIGHT, gamecam);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("game_board.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gamecam.position.set(BlobGame.V_WIDTH / 2, BlobGame.V_HEIGHT / 2, 0);
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        player = new GoodBlob(world);
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
            fdef.shape = shape;
            fdef.restitution = BlobGame.GOOD_BLOB_RESTITUTION;
            body.createFixture(fdef);
        }
    }

    public void update(float dt){
        handleInput(dt);
        world.step(1/60f, 6, 2);
        renderer.setView(gamecam);
    }

    private void handleInput(float dt){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.b2body.applyLinearImpulse(new Vector2(0, BlobGame.GOOD_BLOB_SPEED), player.b2body.getWorldCenter(), true);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            player.b2body.applyLinearImpulse(new Vector2(0, -BlobGame.GOOD_BLOB_SPEED), player.b2body.getWorldCenter(), true);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            player.b2body.applyLinearImpulse(new Vector2(-BlobGame.GOOD_BLOB_SPEED, 0), player.b2body.getWorldCenter(), true);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            player.b2body.applyLinearImpulse(new Vector2(BlobGame.GOOD_BLOB_SPEED, 0), player.b2body.getWorldCenter(), true);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        b2dr.render(world, gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.batch.end();
        hud.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
