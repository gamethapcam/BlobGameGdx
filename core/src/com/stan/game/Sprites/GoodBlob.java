package com.stan.game.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.stan.game.BlobGame;
import com.stan.game.Screens.PlayScreen;


/**
 * Created by stan on 3/07/16.
 */
public class GoodBlob extends Sprite {
    public enum State { ALIVE, DEAD };
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    public PlayScreen screen;
    private Texture goodBlobSmall;
    private Texture goodBlobDead;
    private float stateTimer;
    private float rotation;
    private float scale;
    private boolean goodBlobIsDead;
    private int lives;
    private int score;
    public float power;

    public GoodBlob(PlayScreen screen) {
        super();
        this.score =0;
        currentState = State.ALIVE;
        previousState = State.DEAD;
        this.world = screen.getWorld();
        this.lives = 3;
        this.rotation = 0;
        this.scale = 1;
        stateTimer = 0;
        this.power = 1f;
        defineGoodBlob();
        setPosition(200 / BlobGame.PPM, 200 / BlobGame.PPM);
        goodBlobSmall = new Texture("blob_1.png");
        goodBlobDead = new Texture("blob_1_dead.png");
        setBounds(0, 0, 27 / BlobGame.PPM, 27 / BlobGame.PPM);
        setRegion(goodBlobSmall);
    }

    public void update(float dt) {
        setOrigin(getWidth() / 2, getHeight() / 2);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRotation(getAngle());
        setScale(this.scale);
        setRegion(getFrame(dt));
        reDefineGoodBlob(this.scale);

    }

    public float getBlobScale() {
        return this.scale;
    }

    public void setBlobScale(float scale) {
        this.scale = scale;
    }


    public float getAngle() {
        return this.rotation;
    }

    public void setAngle(float rotation){
        this.rotation = rotation;
    }

    public void defineGoodBlob() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(100 / BlobGame.PPM, 100 / BlobGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(13 / BlobGame.PPM);
        fdef.filter.categoryBits = BlobGame.GOOD_BLOB_BIT;
        fdef.filter.maskBits = BlobGame.GROUND_BIT | BlobGame.BAD_BLOB_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void reDefineGoodBlob(float scale) {
        Vector2 velocity = b2body.getLinearVelocity();
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.linearVelocity.set(velocity);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(13 * scale / BlobGame.PPM);
        fdef.filter.categoryBits = BlobGame.GOOD_BLOB_BIT;
        fdef.filter.maskBits = BlobGame.GROUND_BIT | BlobGame.BAD_BLOB_BIT;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);
    }

    public int getLives(){
        return this.lives;
    }

    public int getScore(){
        return this.score;
    }

    public void onCollision(){

        if (this.scale == 1){
            if (this.lives > 0) {
                BlobGame.manager.get("Hit_Hurt.wav", Sound.class).play();
                this.lives--;
            }
        } else {
            if (this.power < 1){
                this.power += .1f;
            }
            this.score += 10;
            BlobGame.manager.get("get_point.mp3", Sound.class).play();
        }

        if (this.lives == 0){
            BlobGame.manager.get("death_sound.mp3", Sound.class).play();
            goodBlobIsDead = true;
        }
    }

    public void hitWall(){
        BlobGame.manager.get("hit_wall.mp3", Sound.class).play();
    }

    public Texture getFrame(float dt){
        //get marios current state. ie. jumping, running, standing...
        currentState = getState();

        Texture region;

        //depending on the state, get corresponding animation keyFrame.
        switch(currentState){
            case DEAD:
                region = goodBlobDead;
                break;
            case ALIVE:
            default:
                region = goodBlobSmall;
                break;
        }



        //if the current state is the same as the previous state increase the state timer.
        //otherwise the state has changed and we need to reset timer.
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        //update previous state
        previousState = currentState;
        //return our final adjusted frame
        return region;

    }

    public State getState(){
        //Test to Box2D for velocity on the X and Y-Axis
        //if mario is going positive in Y-Axis he is jumping... or if he just jumped and is falling remain in jump state
        if(goodBlobIsDead)
            return State.DEAD;
        else
            return State.ALIVE;
    }

    public float getStateTimer(){
        return stateTimer;
    }

    public float getPower(){
        return this.power;
    }

    public void changePower(float amount){
        this.power += amount;
    }
}
