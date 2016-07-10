package com.stan.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stan.game.Screens.PlayScreen;
import com.stan.game.Screens.TitleScreen;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class BlobGame extends Game {
	public static final int V_WIDTH = 640;
	public static final int V_HEIGHT = 640;
	public static final float PPM = 27;
	public static final float GOOD_BLOB_SPEED = 10;
	public static final float GROUND_RESTITUTION = .5f;
	public static final float BAD_BLOB_RESTITUTION = 1;
	public static final float GOOD_BLOB_LARGE_RESTITUTION = 5;

	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short GOOD_BLOB_BIT = 2;
	public static final short BAD_BLOB_BIT = 4;
	public static final short PICKUP_BIT = 8;

	public static AssetManager manager;



	public SpriteBatch batch;
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("title_screen.mp3", Music.class);
		manager.load("game_music.mp3", Music.class);
		manager.load("Hit_Hurt.wav", Sound.class);
		manager.load("Powerup.wav", Sound.class);
		manager.load("death_sound.mp3", Sound.class);
		manager.load("get_point.mp3", Sound.class);
		manager.load("hit_wall.mp3", Sound.class);
		manager.load("low_power.wav", Sound.class);
		manager.finishLoading();
		setScreen(new TitleScreen(this, batch));
	}



	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		batch.dispose();
	}
}
