package com.mygdx.game.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Sprites.Character;

/**
 * Created by Corentin on 26/11/2016.
 */

public class GameState extends State{

    private Character character;

    private Texture background;

    public GameState(GameStateManager gsm) {
        super(gsm);
        character = new Character(50,50);
        background = new Texture("StartStateResources/background.jpg");
    }

    @Override
    protected void bundleInput() {

    }

    @Override
    public void update(float dt) {
        bundleInput();
        character.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(character.getCharacter(), character.getPosition().x, character.getPosition().y);
        sb.end();
    }

    public void dispose(){

    }
}
