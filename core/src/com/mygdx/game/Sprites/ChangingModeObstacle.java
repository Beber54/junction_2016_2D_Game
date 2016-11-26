package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;

/**
 * Created by Corentin on 26/11/2016.
 */

public class ChangingModeObstacle extends Obstacle {

    @Override
    public boolean collides(Rectangle characterRect) {
        return super.collides(characterRect);
    }

    public ChangingModeObstacle(int x, int y) {
        super(x, MyGdxGame.HEIGHT * 2, Type.ChangingMode);
        Gdx.app.log("changing block constructor", String.valueOf(x));
    }

    @Override
    public void update(float dt) {
        super.update(dt);
    }

    @Override
    public Vector2 getPosition() {
        return super.getPosition();
    }

    @Override
    public Texture getTestTexture() {
        return new Texture("GameStateResources/blockChanging.jpg");
    }
}
