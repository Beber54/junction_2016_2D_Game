package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Sprites.CharactersState;


public class MenuState extends State {

    private Texture background;
    private Texture title;
    private Texture playButton, selectButton;
    private Texture selectCharacterButton;
    private Sprite buttonToPlay, buttonToSelect;

    public MenuState(GameStateManager gsm) {

        super(gsm);
        background = new Texture("StartStateResources/background.jpg");
        title = new Texture("StartStateResources/title.png");
        playButton = new Texture("StartStateResources/playButton.png");
        selectButton = new Texture("StartStateResources/selectCharacter.png");

        float widthPlayButton = (float)0.65*playButton.getWidth();
        float heightPlayButton = (float) 0.65*playButton.getHeight();
        float positionPlayButton = (float)0.1*MyGdxGame.HEIGHT;
        buttonToPlay = new Sprite(playButton);
        buttonToPlay.setPosition((MyGdxGame.WIDTH / 2) - (widthPlayButton / 2), positionPlayButton);
        buttonToPlay.setSize(widthPlayButton,heightPlayButton);

        float widthSelectButton = (float)0.9*selectButton.getWidth();
        float heightSelectButton = (float) 0.9*selectButton.getHeight();
        float positionSelectButton = (float)0.45*MyGdxGame.HEIGHT;
        buttonToSelect = new Sprite(selectButton);
        buttonToSelect.setPosition((MyGdxGame.WIDTH / 2) - (widthSelectButton / 2), positionSelectButton);
        buttonToSelect.setSize(widthSelectButton,heightSelectButton);

    }

    @Override
    public void bundleInput() {

        if(Gdx.input.isKeyPressed(Input.Keys.P)) {
            gsm.set(new GameState(gsm, "sprite1"));
            dispose();
        }

        if(Gdx.input.justTouched()) {

            Vector2 m = new Vector2(Gdx.input.getX(),Gdx.input.getY());
            Rectangle textureBounds1 = new Rectangle(buttonToPlay.getX(),buttonToPlay.getY(),buttonToPlay.getWidth(),buttonToPlay.getHeight());
            Rectangle textureBounds2 = new Rectangle(buttonToSelect.getX(),buttonToSelect.getY(),buttonToSelect.getWidth(),buttonToSelect.getHeight());

            if(textureBounds1.contains(m.x,MyGdxGame.HEIGHT  - m.y)) {
                gsm.set(new GameState(gsm, "sprite1"));
                dispose();
            }

            if(textureBounds2.contains(m.x,MyGdxGame.HEIGHT  - m.y)) {
                gsm.set(new CharactersState(gsm));
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
        buttonToPlay.draw(sb);
        buttonToSelect.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        title.dispose();
        playButton.dispose();
        buttonToPlay.getTexture().dispose();
        buttonToSelect.getTexture().dispose();
    }
}
