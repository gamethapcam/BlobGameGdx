package com.stan.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.stan.game.Accessors.GoodBlobAccessor;
import com.stan.game.BlobGame;
import com.stan.game.InputOutput.BlobGameInputProcessor;
import com.stan.game.Scenes.Hud;
import com.stan.game.Sprites.BadBlob;
import com.stan.game.Sprites.Enemy;
import com.stan.game.Sprites.GoodBlob;
import com.stan.game.Tools.B2WorldCreator;
import com.stan.game.Tools.WorldContactListener;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Back;

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
    private TweenManager tweenManager;
    private BadBlob enemy;
    private Music music;
    private Array<BadBlob> enemylist;
    private boolean addedBlob;

    public PlayScreen(BlobGame game){
        music = BlobGame.manager.get("game_music.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();

        addedBlob = false;
        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(BlobGame.V_WIDTH / BlobGame.PPM, BlobGame.V_HEIGHT / BlobGame.PPM, gamecam);
        Gdx.input.setInputProcessor(new BlobGameInputProcessor(this));
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("game_board.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / BlobGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(this);
        player = new GoodBlob(this);
        hud = new Hud(game.batch, player);
        enemylist = new Array<BadBlob>();
        enemylist.add(new BadBlob(this));
        //enemy = new BadBlob(this);
        tweenManager = new TweenManager();
        world.setContactListener(new WorldContactListener());
    }

    public TiledMap getMap(){
        return map;
    }

    public World getWorld(){
        return world;
    }

    public void update(float dt, GoodBlob player){

        //handleInput(dt);
        tweenManager.update(dt);
        Tween.registerAccessor(GoodBlob.class, new GoodBlobAccessor());
        hud.update(dt, player);
        world.step(1/60f, 6, 2);
        player.update(dt);
        for(BadBlob enemy : enemylist) {
            enemy.update(dt);
        }

        renderer.setView(gamecam);
        if (player.currentState == GoodBlob.State.DEAD){
            music.stop();
        }
        if ((hud.getTime() % 10 ) == 0 && addedBlob == false) {
            enemylist.add(new BadBlob(this));
            addedBlob = true;
        } else if((hud.getTime() % 10 ) != 0){
            addedBlob = false;
        }

    }

    private void handleInput(float dt){
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.b2body.applyLinearImpulse(new Vector2(0, BlobGame.GOOD_BLOB_SPEED), player.b2body.getWorldCenter(), true);
            Tween.set(player, GoodBlobAccessor.ALPHA).target(player.getAngle()).start(tweenManager);
            Tween.to(player, GoodBlobAccessor.ALPHA, .4f).target(180).start(tweenManager).ease(Back.IN);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            player.b2body.applyLinearImpulse(new Vector2(0, -BlobGame.GOOD_BLOB_SPEED), player.b2body.getWorldCenter(), true);
            Tween.set(player, GoodBlobAccessor.ALPHA).target(player.getAngle()).start(tweenManager);
            Tween.to(player, GoodBlobAccessor.ALPHA, .4f).target(0).start(tweenManager).ease(Back.IN);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            player.b2body.applyLinearImpulse(new Vector2(-BlobGame.GOOD_BLOB_SPEED, 0), player.b2body.getWorldCenter(),
                    true);
            Tween.set(player, GoodBlobAccessor.ALPHA).target(player.getAngle()).start(tweenManager);
            Tween.to(player, GoodBlobAccessor.ALPHA, .4f).target(270).start(tweenManager).ease(Back.IN);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            player.b2body.applyLinearImpulse(new Vector2(BlobGame.GOOD_BLOB_SPEED, 0), player.b2body.getWorldCenter(), true);
            Tween.set(player, GoodBlobAccessor.ALPHA).target(player.getAngle()).start(tweenManager);
            Tween.to(player, GoodBlobAccessor.ALPHA, .4f).target(90).start(tweenManager).ease(Back.IN);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            Tween.set(player, GoodBlobAccessor.BETA).target(player.getBlobScale()).start(tweenManager);
            Tween.to(player, GoodBlobAccessor.BETA, .4f).target(3).start(tweenManager).ease(Back.IN);
        }
    }

    public GoodBlob getPlayer(){
        return player;
    }

    public TweenManager getTweenManager(){
        return tweenManager;
    }

    public boolean gameOver(){
        if (player.currentState == GoodBlob.State.DEAD && player.getStateTimer() > 3){
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta, player);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        //b2dr.render(world, gamecam.combined);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        for(BadBlob enemy : enemylist) {
            enemy.draw(game.batch);
        }
        //enemy.draw(game.batch);
        game.batch.end();
        hud.stage.draw();
        if (gameOver()){

            game.setScreen(new GameOverScreen(game));
            dispose();
        }
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
