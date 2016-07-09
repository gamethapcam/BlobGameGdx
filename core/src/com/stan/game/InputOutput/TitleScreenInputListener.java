package com.stan.game.InputOutput;

import com.badlogic.gdx.InputProcessor;
import com.stan.game.BlobGame;
import com.stan.game.Screens.PlayScreen;
import com.stan.game.Screens.TitleScreen;

/**
 * Created by stan on 9/07/16.
 */
public class TitleScreenInputListener implements InputProcessor {
    public TitleScreen screen;

    public TitleScreenInputListener(TitleScreen screen){
        this.screen = screen;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screen.getGame().setScreen(new PlayScreen((BlobGame) screen.getGame()));
        screen.getMusic().stop();
        screen.dispose();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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
