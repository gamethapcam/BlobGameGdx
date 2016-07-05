package com.stan.game.Accessors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.stan.game.Sprites.GoodBlob;

import aurelienribon.tweenengine.TweenAccessor;

/**
 * Created by stan on 4/07/16.
 */
public class GoodBlobAccessor implements TweenAccessor<GoodBlob> {
    public static final int ALPHA = 0;
    @Override
    public int getValues(GoodBlob target, int tweenType, float[] returnValues) {
        switch(tweenType){
            case ALPHA:
                returnValues[0] = target.getAngle();
                return 1;
            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(GoodBlob target, int tweenType, float[] newValues) {
        switch(tweenType){
            case ALPHA:
                target.setAngle(newValues[0]);
                break;
            default:
                assert false;
        }
    }
}
