package com.stan.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.stan.game.BlobGame;
import com.stan.game.Sprites.GoodBlob;
import com.sun.prism.image.ViewPort;

/**
 * Created by stan on 3/07/16.
 */
public class Hud implements Disposable {
    public Stage stage;
    private FitViewport viewPort;
    private Integer worldTimer;
    private float timeCount;
    private Integer livesCount;
    private boolean timeUp;
    private int score;
    private PowerBar powerBar;

    Label countdownLabel;
    Label livesLabel;
    Label livesCountLabel;
    Label timeLabel;
    Label scoreCountLabel;
    Label scoreLabel;

    public Hud(SpriteBatch sb, GoodBlob player){

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Montserrat-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;

        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        parameter.shadowColor= Color.BLACK;
        BitmapFont font30 = generator.generateFont(parameter); // font size 12 pixels
        font30.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        worldTimer = 300;
        livesCount = player.getLives();
        timeCount = 0;
        score = player.getScore();

        viewPort = new FitViewport(BlobGame.V_WIDTH, BlobGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewPort, sb);

        Table table = new Table();

        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(font30, Color.WHITE));
        livesLabel = new Label(String.format("%02d", livesCount), new Label.LabelStyle(font30, Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(font30, Color.WHITE));
        livesCountLabel = new Label("LIVES", new Label.LabelStyle(font30, Color.WHITE));
        scoreCountLabel = new Label("SCORE", new Label.LabelStyle(font30, Color.WHITE));
        scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(font30, Color.WHITE));

        table.add(timeLabel).expandX().padTop(10);
        table.add(livesCountLabel).expandX().padTop(10);
        table.add(scoreCountLabel).expandX().padTop(10);
        table.row();
        table.add(countdownLabel).expandX().padTop(10);
        table.add(livesLabel).expandX().padTop(10);
        table.add(scoreLabel).expandX().padTop(10);

        powerBar = new PowerBar();
        powerBar.setPosition(BlobGame.V_WIDTH / 2 - 200, 15);
        powerBar.setWidth(400);
        powerBar.setHeight(12);

        stage.addActor(powerBar);
        stage.addActor(table);
    }

    public void addScore(int amount){
        score += amount;
    }

    public void update(float dt, GoodBlob player){
        powerBar.setProgress(player.getPower());
        timeCount += dt;
        if (timeCount >= 1){
            if (worldTimer > 0){
                worldTimer--;
            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
        livesCount = player.getLives();
        score = player.getScore();
        livesLabel.setText(String.format("%02d", livesCount));
        scoreLabel.setText(String.format("%03d", score));

    }



    @Override
    public void dispose(){
        stage.dispose();
    }

    public boolean isTimeUp(){
        return timeUp;
    }

    public int getTime(){
        return this.worldTimer;
    }
}
