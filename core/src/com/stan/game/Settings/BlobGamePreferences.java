package com.stan.game.Settings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by stan on 10/07/16.
 */
public class BlobGamePreferences {
    private static final String PREFS_NAME = "blob_game_prefs";
    private static final String HIGH_SCORE = "high_score";
    private Preferences preferences;

    public BlobGamePreferences(){}

    protected Preferences getPrefs() {

        if(preferences == null){
            preferences = Gdx.app.getPreferences(PREFS_NAME);
        }
        return preferences;
    }

    public void updateHighScore(int score) {
        if (getPrefs().getInteger(HIGH_SCORE, 0) < score){
            getPrefs().putInteger(HIGH_SCORE, score);
            getPrefs().flush();
        }
    }

    public int getHighScore(){
        return getPrefs().getInteger(HIGH_SCORE, 0);
    }

}

