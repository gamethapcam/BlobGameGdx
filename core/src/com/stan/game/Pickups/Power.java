package com.stan.game.Pickups;

import com.badlogic.gdx.graphics.Texture;
import com.stan.game.Screens.PlayScreen;

/**
 * Created by stan on 9/07/16.
 */
public class Power extends Pickup {
    private Texture powerPack;


    public Power(PlayScreen screen){
        super(screen);
        powerPack = new Texture("power_pack.png");
        setRegion(powerPack);
    }


}
