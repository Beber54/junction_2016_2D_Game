package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;

import com.badlogic.gdx.math.Rectangle;


/**
 * Created by Corentin on 26/11/2016.
 */

public class Character {

    public int speed = MyGdxGame.WIDTH/4;

    private Vector3 position;
    private Vector3 velocity;
    public Texture characterSprite;
    private Rectangle bounds;

    private Vector3 newPosition;

    public Character(int x, int y, String nameSelectedCharacter){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0,0,0);
        characterSprite = new Texture("GameStateResources/testCharacter.png");
        //characterSprite = new Texture("Characters/"+nameSelectedCharacter+".png");
        newPosition = new Vector3(x, y, 0);
        bounds = new Rectangle(x, this.characterSprite.getHeight(), characterSprite.getWidth(), characterSprite.getHeight());
    }

    public void update(float dt){
        velocity.add(speed, 0, 0);
        velocity.scl(dt);

        if(this.newPosition.x >= 0 && (this.newPosition.x) <= (MyGdxGame.WIDTH - speed)) {
            this.getPosition().x = this.newPosition.x;
        }else if(this.newPosition.x < 0){
            this.getPosition().x = 0;
        }else if((this.newPosition.x + characterSprite.getWidth()) > MyGdxGame.WIDTH){
            this.getPosition().x = MyGdxGame.WIDTH - speed;
        }

        velocity.scl(1/dt);
        bounds.setPosition(this.position.x, MyGdxGame.HEIGHT - this.characterSprite.getHeight());
    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getCharacter() {
        return characterSprite;
    }

    public void moveRight(){
        velocity.x =+ speed;
        newPosition = new Vector3(this.getPosition().x + speed, 0,0);
    }

    public void moveLeft(){
        newPosition = new Vector3(this.getPosition().x - speed, 0,0);
    }

    public Rectangle getBounds(){
        return bounds;
    }
}
