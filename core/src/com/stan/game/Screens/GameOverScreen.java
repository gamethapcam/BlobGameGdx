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
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.stan.game.BlobGame;
import com.stan.game.InputOutput.GameOverScreenInputListener;
import com.stan.game.InputOutput.TitleScreenInputListener;

/**
 * Created by stan on 9/07/16.
 */
public class GameOverScreen implements Screen {
    private FitViewport viewport;
    private Stage stage;

    private Game game;
    private Music music;

    public GameOverScreen(Game game){
        Gdx.input.setInputProcessor(new GameOverScreenInputListener(this));

        music = BlobGame.manager.get("title_screen.mp3", Music.class);
        music.setLooping(true);
        music.setVolume(0.3f);
        music.play();


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
        table.setFillParent(true);
        Label gameOverlabel = new Label("GAME OVER", font);
        Label playAgainLabel = new Label("TOUCH TO PLAY AGAIN", font);
        table.add(gameOverlabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);
        stage.addActor(table);
    }
    @Override
    public void show() {

    }
    public Music getMusic(){
        return music;
    }


    public Game getGame(){
        return this.game;
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()){
            music.stop();
            game.setScreen(new PlayScreen((BlobGame) game));
            dispose();
        }

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
