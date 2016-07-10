package com.stan.game.InputOutput;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.stan.game.Accessors.GoodBlobAccessor;
import com.stan.game.BlobGame;
import com.stan.game.Screens.PlayScreen;
import com.stan.game.Sprites.GoodBlob;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Back;

/**
 * Created by stan on 9/07/16.
 */
public class BlobGameInputProcessor implements InputProcessor {

    private static final int KEY_UP = 19;
    private static final int KEY_DOWN = 20;
    private static final int KEY_LEFT = 21;
    private static final int KEY_RIGHT = 22;
    private static final int KEY_SPACE = 62;
    private float prevDragX;
    private float prevDragY;
    private PlayScreen screen;

    public BlobGameInputProcessor(PlayScreen screen){
        this.screen = screen;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (screen.getPlayer().getState() != GoodBlob.State.DEAD){
            if (keycode == KEY_UP){
                screen.getPlayer().b2body.applyLinearImpulse(new Vector2(0, BlobGame.GOOD_BLOB_SPEED), screen.getPlayer().b2body.getWorldCenter(), true);
                Tween.set(screen.getPlayer(), GoodBlobAccessor.ALPHA).target(screen.getPlayer().getAngle()).start(screen.getTweenManager());
                Tween.to(screen.getPlayer(), GoodBlobAccessor.ALPHA, .4f).target(180).start(screen.getTweenManager()).ease(Back.IN);
            } else if (keycode == KEY_DOWN){
                screen.getPlayer().b2body.applyLinearImpulse(new Vector2(0, -BlobGame.GOOD_BLOB_SPEED), screen.getPlayer().b2body.getWorldCenter(), true);
                Tween.set(screen.getPlayer(), GoodBlobAccessor.ALPHA).target(screen.getPlayer().getAngle()).start(screen.getTweenManager());
                Tween.to(screen.getPlayer(), GoodBlobAccessor.ALPHA, .4f).target(0).start(screen.getTweenManager()).ease(Back.IN);
            } else if (keycode == KEY_LEFT){
                screen.getPlayer().b2body.applyLinearImpulse(new Vector2(-BlobGame.GOOD_BLOB_SPEED, 0), screen.getPlayer().b2body.getWorldCenter(),
                        true);
                Tween.set(screen.getPlayer(), GoodBlobAccessor.ALPHA).target(screen.getPlayer().getAngle()).start(screen.getTweenManager());
                Tween.to(screen.getPlayer(), GoodBlobAccessor.ALPHA, .4f).target(270).start(screen.getTweenManager()).ease(Back.IN);
            } else if (keycode == KEY_RIGHT){
                screen.getPlayer().b2body.applyLinearImpulse(new Vector2(BlobGame.GOOD_BLOB_SPEED, 0), screen.getPlayer().b2body.getWorldCenter(), true);
                Tween.set(screen.getPlayer(), GoodBlobAccessor.ALPHA).target(screen.getPlayer().getAngle()).start(screen.getTweenManager());
                Tween.to(screen.getPlayer(), GoodBlobAccessor.ALPHA, .4f).target(90).start(screen.getTweenManager()).ease(Back.IN);
            } else if (keycode == KEY_SPACE){
                if(screen.getPlayer().getPower() > .1){
                    BlobGame.manager.get("Powerup.wav", Sound.class).play(.1f);
                    screen.getPlayer().changePower(-.1f);
                    Tween.set(screen.getPlayer(), GoodBlobAccessor.BETA).target(screen.getPlayer().getBlobScale()).start(screen.getTweenManager());
                    Tween.to(screen.getPlayer(), GoodBlobAccessor.BETA, .2f).target(4f).start(screen.getTweenManager()).ease(Back.IN);
                    Tween.set(screen.getPlayer(), GoodBlobAccessor.BETA).target(screen.getPlayer().getBlobScale()).start(screen.getTweenManager());
                    Tween.to(screen.getPlayer(), GoodBlobAccessor.BETA, .2f).target(1).start(screen.getTweenManager()).ease(Back.IN).delay(.7f);
                } else {
                    BlobGame.manager.get("low_power.wav", Sound.class).play(.2f);
                }
            }

        }
        return false;

    }

    @Override
    public boolean keyUp(int keycode) {
        if (screen.getPlayer().getState() != GoodBlob.State.DEAD) {
            if (keycode == KEY_SPACE) {
                Tween.set(screen.getPlayer(), GoodBlobAccessor.BETA).target(screen.getPlayer().getBlobScale()).start(screen.getTweenManager());
                Tween.to(screen.getPlayer(), GoodBlobAccessor.BETA, .2f).target(1).start(screen.getTweenManager()).ease(Back.IN);
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screen.getPlayer().getState() != GoodBlob.State.DEAD && screen.getPlayer().getPower() > .1 ) {
            screen.getPlayer().changePower(-.1f);
            BlobGame.manager.get("Powerup.wav", Sound.class).play(.1f);
            Tween.set(screen.getPlayer(), GoodBlobAccessor.BETA).target(screen.getPlayer().getBlobScale()).start(screen.getTweenManager());
            Tween.to(screen.getPlayer(), GoodBlobAccessor.BETA, .2f).target(4f).start(screen.getTweenManager()).ease(Back.IN);
            Tween.set(screen.getPlayer(), GoodBlobAccessor.BETA).target(screen.getPlayer().getBlobScale()).start(screen.getTweenManager());
            Tween.to(screen.getPlayer(), GoodBlobAccessor.BETA, .2f).target(1).start(screen.getTweenManager()).ease(Back.IN).delay(.7f);
        } else {
            BlobGame.manager.get("low_power.wav", Sound.class).play(.2f);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (screen.getPlayer().getState() == GoodBlob.State.ALIVE) {
            Tween.set(screen.getPlayer(), GoodBlobAccessor.BETA).target(screen.getPlayer().getBlobScale()).start(screen.getTweenManager());
            Tween.to(screen.getPlayer(), GoodBlobAccessor.BETA, .2f).target(1).start(screen.getTweenManager()).ease(Back.IN);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (screen.getPlayer().getState() != GoodBlob.State.DEAD) {
            if (Math.abs(screenX - prevDragX) > Math.abs(screenY - prevDragY)) {
                if (screenX - prevDragX > 30) {
                    screen.getPlayer().b2body.applyLinearImpulse(new Vector2(BlobGame.GOOD_BLOB_SPEED, 0), screen.getPlayer().b2body.getWorldCenter(), true);
                    Tween.set(screen.getPlayer(), GoodBlobAccessor.ALPHA).target(screen.getPlayer().getAngle()).start(screen.getTweenManager());
                    Tween.to(screen.getPlayer(), GoodBlobAccessor.ALPHA, .4f).target(90).start(screen.getTweenManager()).ease(Back.IN);
                } else if (screenX - prevDragX < -30){
                    screen.getPlayer().b2body.applyLinearImpulse(new Vector2(-BlobGame.GOOD_BLOB_SPEED, 0), screen.getPlayer().b2body.getWorldCenter(),
                            true);
                    Tween.set(screen.getPlayer(), GoodBlobAccessor.ALPHA).target(screen.getPlayer().getAngle()).start(screen.getTweenManager());
                    Tween.to(screen.getPlayer(), GoodBlobAccessor.ALPHA, .4f).target(270).start(screen.getTweenManager()).ease(Back.IN);
                }
            } else if (Math.abs(screenX - prevDragX) < Math.abs(screenY - prevDragY)) {
                if (screenY - prevDragY < -30) {
                    screen.getPlayer().b2body.applyLinearImpulse(new Vector2(0, BlobGame.GOOD_BLOB_SPEED), screen.getPlayer().b2body.getWorldCenter(), true);
                    Tween.set(screen.getPlayer(), GoodBlobAccessor.ALPHA).target(screen.getPlayer().getAngle()).start(screen.getTweenManager());
                    Tween.to(screen.getPlayer(), GoodBlobAccessor.ALPHA, .4f).target(180).start(screen.getTweenManager()).ease(Back.IN);
                } else if (screenY - prevDragY > 30){
                    screen.getPlayer().b2body.applyLinearImpulse(new Vector2(0, -BlobGame.GOOD_BLOB_SPEED), screen.getPlayer().b2body.getWorldCenter(), true);
                    Tween.set(screen.getPlayer(), GoodBlobAccessor.ALPHA).target(screen.getPlayer().getAngle()).start(screen.getTweenManager());
                    Tween.to(screen.getPlayer(), GoodBlobAccessor.ALPHA, .4f).target(0).start(screen.getTweenManager()).ease(Back.IN);
                }
            }
            prevDragX = screenX;
            prevDragY = screenY;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
