package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.States.GameState;
import com.mygdx.game.States.GameStateManager;
import com.mygdx.game.States.MenuState;
import com.mygdx.game.States.State;

/**
 * Created by Ian on 26/11/2016.
 */

public class CharactersState extends State {

    private Texture background, title;
    private Texture sprite1, sprite2, sprite3, sprite4;
    private Sprite buttonSprite1, buttonSprite2, buttonSprite3, buttonSprite4;
    private Texture menuButton;
    private Sprite buttonToMenu;

    public CharactersState(GameStateManager gsm) {

        super(gsm);
        background = new Texture("StartStateResources/background.jpg");
        title = new Texture("Characters/Select-character.png");
        sprite1 = new Texture("Characters/sprite1.png");
        sprite2 = new Texture("Characters/sprite2.png");
        sprite3 = new Texture("Characters/sprite3.png");
        sprite4 = new Texture("Characters/sprite4.png");

        float widthSprite = (float) 0.1875*MyGdxGame.WIDTH;
        float widthSpace = (float) 0.05*MyGdxGame.WIDTH;
        float incrementX = widthSpace;

        buttonSprite1 = new Sprite(sprite1);
        buttonSprite1.setPosition(incrementX, (float) (MyGdxGame.HEIGHT/2.5));
        buttonSprite1.setSize(widthSprite,sprite1.getHeight());
        incrementX += widthSprite;
        incrementX += widthSpace;

        buttonSprite2 = new Sprite(sprite2);
        buttonSprite2.setPosition(incrementX, (float) (MyGdxGame.HEIGHT/2.5));
        buttonSprite2.setSize(widthSprite,sprite2.getHeight());
        incrementX += widthSprite;
        incrementX += widthSpace;

        buttonSprite3 = new Sprite(sprite3);
        buttonSprite3.setPosition(incrementX, (float) (MyGdxGame.HEIGHT/2.5));
        buttonSprite3.setSize(widthSprite,sprite3.getHeight());
        incrementX += widthSprite;
        incrementX += widthSpace;

        buttonSprite4 = new Sprite(sprite4);
        buttonSprite4.setPosition(incrementX, (float) (MyGdxGame.HEIGHT/2.5));
        buttonSprite4.setSize(widthSprite,sprite4.getHeight());

        menuButton = new Texture("StartStateResources/arrow.png");
        buttonToMenu = new Sprite(menuButton);
        float widthMenuButton = (float)menuButton.getWidth();
        float heightMenuButton = (float) 0.65*menuButton.getHeight();
        float positionPlayButton = (float)0.1*MyGdxGame.HEIGHT;
        buttonToMenu.setPosition((MyGdxGame.WIDTH / 2) - (widthMenuButton / 2), positionPlayButton);
        buttonToMenu.setSize(widthMenuButton,heightMenuButton);

    }

    @Override
    protected void bundleInput() {

        if(Gdx.input.justTouched()) {

            Vector2 m = new Vector2(Gdx.input.getX(),Gdx.input.getY());
            Rectangle textureBounds1 = new Rectangle(buttonSprite1.getX(),buttonSprite1.getY(),buttonSprite1.getWidth(),buttonSprite1.getHeight());
            Rectangle textureBounds2 = new Rectangle(buttonSprite2.getX(),buttonSprite2.getY(),buttonSprite2.getWidth(),buttonSprite2.getHeight());
            Rectangle textureBounds3 = new Rectangle(buttonSprite3.getX(),buttonSprite3.getY(),buttonSprite3.getWidth(),buttonSprite3.getHeight());
            Rectangle textureBounds4 = new Rectangle(buttonSprite4.getX(),buttonSprite4.getY(),buttonSprite4.getWidth(),buttonSprite4.getHeight());
            Rectangle textureBounds5 = new Rectangle(buttonToMenu.getX(),buttonToMenu.getY(),buttonToMenu.getWidth(),buttonToMenu.getHeight());

            if(textureBounds1.contains(m.x,MyGdxGame.HEIGHT  - m.y)) {
                gsm.set(new GameState(gsm,"sprite1"));
                dispose();
            }

            if(textureBounds2.contains(m.x,MyGdxGame.HEIGHT  - m.y)) {
                gsm.set(new GameState(gsm,"sprite2"));
                dispose();
            }

            if(textureBounds3.contains(m.x,MyGdxGame.HEIGHT  - m.y)) {
                gsm.set(new GameState(gsm,"sprite3"));
                dispose();
            }

            if(textureBounds4.contains(m.x,MyGdxGame.HEIGHT  - m.y)) {
                gsm.set(new GameState(gsm,"sprite4"));
                dispose();
            }

            if(textureBounds5.contains(m.x,MyGdxGame.HEIGHT  - m.y)) {
                gsm.set(new MenuState(gsm));
                dispose();
            }

        }

    }

    @Override
    public void update(float dt) {
        bundleInput();
    }

    @Override
    public void render(SpriteBatch sb) {

        int widthTitle = (int)(0.85*MyGdxGame.WIDTH);
        int heightTitle = (int)(0.85*title.getHeight());
        int positionTitle = (int)(0.7*MyGdxGame.HEIGHT);

        sb.begin();
        sb.draw(background, 0, 0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        sb.draw(title, (MyGdxGame.WIDTH / 2) - (widthTitle / 2), positionTitle, widthTitle, heightTitle);
        buttonSprite1.draw(sb);
        buttonSprite2.draw(sb);
        buttonSprite3.draw(sb);
        buttonSprite4.draw(sb);
        buttonToMenu.draw(sb);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        sprite1.dispose();
        sprite2.dispose();
        sprite3.dispose();
        sprite4.dispose();
        menuButton.dispose();
    }

}
