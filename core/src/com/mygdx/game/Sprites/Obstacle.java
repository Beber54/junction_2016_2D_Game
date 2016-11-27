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


    public static int speed = -400;

    private Vector2 position;
    private Vector2 velocity;
    private Rectangle bounds;
    private Type elementType;

    private Texture testTexture;

    public Obstacle(int x, int y, Type elementType){
        this.elementType = elementType;
        position = new Vector2(x, y);
        velocity = new Vector2(0,0);
        testTexture = new Texture("GameStateResources/block.png");
        switch(this.elementType){
            case Fire:
                testTexture = new Texture("GameStateResources/spriteFire.png");
                break;
            case Earth:
                testTexture = new Texture("GameStateResources/spriteEarth.png");
                break;
            case Air:
                testTexture = new Texture("GameStateResources/spriteAir.png");
                break;
            case Water:
                testTexture = new Texture("GameStateResources/spriteWater.png");
                break;
            case ChangingMode:
                testTexture = new Texture("GameStateResources/blockChanging.jpg");
                break;

        }

        bounds = new Rectangle(position.x, MyGdxGame.HEIGHT - (MyGdxGame.HEIGHT - testTexture.getHeight()), testTexture.getWidth(), testTexture.getHeight());
        velocity.add(0, speed);
    }



    public void update(float dt){
        velocity.scl(dt);
        position.add(0, velocity.y);
        velocity.scl(1/dt);
        bounds.setPosition(this.position.x,MyGdxGame.HEIGHT - this.position.y);
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

    public Type getElementType() {
        return elementType;
    }
}
