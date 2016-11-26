package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Corentin on 26/11/2016.
 */

public class ChangingModeObstacle extends Obstacle {

    @Override
    public boolean collides(Rectangle characterRect) {
        return super.collides(characterRect);
    }

    public ChangingModeObstacle(int x, int y) {
        super(x, y, Type.ChangingMode);
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
        return super.getTestTexture();
    }
}
