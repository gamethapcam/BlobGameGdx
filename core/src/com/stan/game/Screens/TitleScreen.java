package com.stan.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.stan.game.BlobGame;
import com.stan.game.InputOutput.TitleScreenInputListener;
import com.stan.game.Settings.BlobGamePreferences;

/**
 * Created by stan on 9/07/16.
 */
public class TitleScreen implements Screen {
    private FitViewport viewport;
    private Stage stage;
    private Music music;
    private Game game;

    public TitleScreen(Game game, SpriteBatch sb){

        Gdx.input.setInputProcessor(new TitleScreenInputListener(this));
        BlobGamePreferences prefs = new BlobGamePreferences();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Montserrat-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        BitmapFont font50 = generator.generateFont(parameter); // font size 12 pixels
        font50.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        this.game = game;
        viewport = new FitViewport(BlobGame.V_WIDTH, BlobGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((BlobGame) game).batch);

        Label.LabelStyle font = new Label.LabelStyle(font50, Color.WHITE);
        Table table = new Table();
        table.center();
        table.setWidth(BlobGame.V_WIDTH);
        table.setHeight(BlobGame.V_HEIGHT);
        //table.setFillParent(true);
        Label playLabel = new Label("TOUCH TO PLAY", font);
        Label highScoreLabel = new Label("HIGH SCORE: " + Integer.toString(prefs.getHighScore()), font);
        table.add(playLabel);
        table.row();
        table.add(highScoreLabel).expandX().padTop(10f);
        stage.addActor(table);

        music = BlobGame.manager.get("title_screen.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();
    }

    public Music getMusic(){
        return music;
    }

    @Override
    public void show() {

    }

    public Game getGame(){
        return this.game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glViewport((int) viewport.getScreenX(), (int) viewport.getScreenY(),
                (int) viewport.getScreenWidth(), (int) viewport.getScreenHeight());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {


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
        stage.dispose();
    }
}
