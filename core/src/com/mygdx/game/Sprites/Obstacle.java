package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;

import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by Corentin on 26/11/2016.
 */

public class Obstacle {


    public int speed = -15;



    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;

    private Texture testTexture;

    public Obstacle(int x, int y){
        position = new Vector2(x, y);
        velocity = new Vector2(0,0);
        testTexture = new Texture("GameStateResources/block.png");
        bounds = new Rectangle(position.x, MyGdxGame.HEIGHT - (MyGdxGame.HEIGHT - testTexture.getHeight()), testTexture.getWidth(), testTexture.getHeight());
    }



    public void update(float dt){
        velocity.add(0, speed);
        velocity.scl(dt);
        position.add(0, velocity.y);
        velocity.scl(1/dt);
        bounds.setPosition(this.position.x, this.position.y);
        Gdx.app.log("pos x block",String.valueOf(this.position.x));
        Gdx.app.log("pos y block",String.valueOf(this.position.y));
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getTestTexture() {
        return testTexture;
    }

    public boolean collides(Rectangle characterRect){
        return characterRect.overlaps(bounds);
    }
}
