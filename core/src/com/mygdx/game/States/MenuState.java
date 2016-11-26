package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

public class MenuState extends State {

    private Texture background;
    private Texture title;
    private Texture playButton;
    private Texture selectCharacterButton;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("StartStateResources/background.jpg");
        title = new Texture("StartStateResources/title.png");
        playButton = new Texture("StartStateResources/playButton.png");
        selectCharacterButton = new Texture("StartStateResources/selectCharacter.png");
    }

    @Override
    public void bundleInput() {

        if(Gdx.input.justTouched()) {
            gsm.set(new GameState(gsm));
            dispose();
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

        double widthPlayButton = 0.65*playButton.getWidth();
        double heightPlayButton = 0.65*playButton.getHeight();
        double positionPlayButton = 0.1*MyGdxGame.HEIGHT;

        double widthSelectCharacterButton = 0.9*selectCharacterButton.getWidth();
        double heightSelectCharacterButton = 0.9*selectCharacterButton.getHeight();
        double positionSelectCharacterButton = 0.45*MyGdxGame.HEIGHT;

        sb.begin();
        sb.draw(background, 0, 0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        sb.draw(title, (MyGdxGame.WIDTH / 2) - (widthTitle / 2), positionTitle, widthTitle, heightTitle);
        sb.draw(selectCharacterButton, (int)((MyGdxGame.WIDTH / 2) - (widthSelectCharacterButton / 2)), (int) positionSelectCharacterButton, (int) widthSelectCharacterButton, (int) heightSelectCharacterButton);
        sb.draw(playButton, (int)((MyGdxGame.WIDTH / 2) - (widthPlayButton / 2)), (int) positionPlayButton, (int) widthPlayButton, (int) heightPlayButton);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        title.dispose();
        playButton.dispose();
        selectCharacterButton.dispose();
    }
}
