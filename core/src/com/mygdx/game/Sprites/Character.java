package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Corentin on 26/11/2016.
 */

public class Character {

    public int speed = 10;

    private Vector3 position;
    private Vector3 velocity;
    private Texture character;

    public Character(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0,0,0);
        character = new Texture("GameStateResources/testCharacter.png");

    }

    public void update(float dt){
        velocity.add(speed, 0, 0);
        velocity.scl(dt);
        position.add(velocity.x, 0,0);

        velocity.scl(1/dt);
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getCharacter() {
        return character;
    }
}
