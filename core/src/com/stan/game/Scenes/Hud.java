package com.stan.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.stan.game.BlobGame;
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

    Label countdownLabel;
    Label livesLabel;
    Label livesCountLabel;
    Label timeLabel;

    public Hud(SpriteBatch sb){
        worldTimer = 300;
        livesCount = 3;
        timeCount = 0;

        viewPort = new FitViewport(BlobGame.V_WIDTH, BlobGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewPort, sb);

        Table table = new Table();

        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        livesLabel = new Label(String.format("%02d", livesCount), new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        livesCountLabel = new Label("LIVES", new Label.LabelStyle(new BitmapFont(), Color.BLACK));

        table.add(timeLabel).expandX().padTop(10);
        table.add(livesCountLabel).expandX().padTop(10);
        table.row();
        table.add(countdownLabel).expandX().padTop(10);
        table.add(livesLabel).expandX().padTop(10);

        stage.addActor(table);
    }

    public void update(float dt){
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
    }

    @Override
    public void dispose(){
        stage.dispose();
    }

    public boolean isTimeUp(){
        return timeUp;
    }
}
