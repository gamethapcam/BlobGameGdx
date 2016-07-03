package com.stan.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stan.game.Screens.PlayScreen;

public class BlobGame extends Game {
	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 640;
	public static final int GOOD_BLOB_SPEED = 100;
	public static final int GOOD_BLOB_RESTITUTION = 1;
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
