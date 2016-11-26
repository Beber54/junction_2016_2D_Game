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


public class MenuState extends State {

    private Texture background;
    private Texture title;
    private Texture playButton;
    private Texture selectCharacterButton;
    private Animation animationSelect;
    private Sprite button;

    public MenuState(GameStateManager gsm) {

        super(gsm);
        background = new Texture("StartStateResources/background.jpg");
        title = new Texture("StartStateResources/title.png");
        playButton = new Texture("StartStateResources/playButton.png");
        selectCharacterButton = new Texture("StartStateResources/selectCharacter.png");

        float widthPlayButton = (float)0.65*playButton.getWidth();
        float heightPlayButton = (float) 0.65*playButton.getHeight();
        float positionPlayButton = (float)0.1*MyGdxGame.HEIGHT;
        button = new Sprite(playButton);
        //button.setFlip(false,true);
        button.setPosition((MyGdxGame.WIDTH / 2) - (widthPlayButton / 2), positionPlayButton);
        button.setSize(widthPlayButton,heightPlayButton);


    }

    @Override
    public void bundleInput() {

        if(Gdx.input.isKeyPressed(Input.Keys.P)) {

            Vector3 m = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(m);
           // Rectangle textureBounds = new Rectangle(button.getX(),button.getY(),button.getWidth(),button.getHeight());
           // double checkPositionY = 0.9*MyGdxGame.HEIGHT - (MyGdxGame.HEIGHT-0.9*MyGdxGame.HEIGHT);
           // if((Gdx.input.getX() > button.getX()) && (Gdx.input.getX() < button.getX() + button.getWidth())) {
           //  if((Gdx.input.getY() > button.getY())) {/*&& (Gdx.input.getY() < 0.1*MyGdxGame.HEIGHT)) {*/
                // if(textureBounds.contains(m.x,m.y)) {
                    gsm.set(new GameState(gsm));
                    dispose();//}
             //  }
            //}

        }

        if(Gdx.input.justTouched()) {

            Vector2 m = new Vector2(Gdx.input.getX(),Gdx.input.getY());

            Gdx.app.log("abscisse clic", String.valueOf(m.x));
            Gdx.app.log("ordonnée clic", String.valueOf(MyGdxGame.HEIGHT  - m.y));
            Rectangle textureBounds = new Rectangle(button.getX(),button.getY(),button.getWidth(),button.getHeight());
            // double checkPositionY = 0.9*MyGdxGame.HEIGHT - (MyGdxGame.HEIGHT-0.9*MyGdxGame.HEIGHT);
            // if((Gdx.input.getX() > button.getX()) && (Gdx.input.getX() < button.getX() + button.getWidth())) {
            //  if((Gdx.input.getY() > button.getY())) {/*&& (Gdx.input.getY() < 0.1*MyGdxGame.HEIGHT)) {*/
            if(textureBounds.contains(m.x,MyGdxGame.HEIGHT  - m.y)) {
                Gdx.app.log("abscisse clic", String.valueOf(m.x));
                Gdx.app.log("ordonnée clic", String.valueOf(m.y));
                gsm.set(new GameState(gsm));
                dispose();
             }
            //  }
            //}

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
        button.draw(sb);
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
