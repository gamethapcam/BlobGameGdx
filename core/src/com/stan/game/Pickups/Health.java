package com.stan.game.Pickups;

import com.badlogic.gdx.graphics.Texture;
import com.stan.game.Screens.PlayScreen;

/**
 * Created by stan on 9/07/16.
 */
public class Health extends Pickup {
    private Texture healthPack;


    public Health(PlayScreen screen){
        super(screen);
        healthPack = new Texture("health_pack.png");
        setRegion(healthPack);
    }





}
