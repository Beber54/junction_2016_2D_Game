package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.CharactersState;

/**
 * Created by bertr on 26/11/2016.
 */

public class EndState extends State {

    private Texture background;
    private Texture title, score, timePlayed, tokens;
    private Texture menuButton, replayButton, bestScoresButton;
    private Sprite buttonToMenu, buttonToReplay, buttonToBestScores;
    private Texture scoreImage, timeImage, tokensImage;

    protected EndState(GameStateManager gsm) {

        super(gsm);
        background = new Texture("EndResources/end.jpg");
        title = new Texture("EndResources/title.png");
        menuButton = new Texture("EndResources/menu.png");
        replayButton = new Texture("EndResources/replay.png");
        bestScoresButton = new Texture("EndResources/bestScores.png");

        scoreImage = new Texture("EndResources/score.png");
        timeImage = new Texture("EndResources/time.png");
        tokensImage = new Texture("EndResources/tokens.png");

        double widthSpace = 0.15*MyGdxGame.WIDTH;
        float positionButton1 = (float) 0.18* MyGdxGame.HEIGHT;
        float positionButton2 = (float) 0.1* MyGdxGame.HEIGHT;
        double sumWidthFirstLine = menuButton.getWidth() + widthSpace + replayButton.getWidth();

        buttonToMenu = new Sprite(menuButton);
        buttonToReplay = new Sprite(replayButton);
        buttonToBestScores = new Sprite(bestScoresButton);

        buttonToMenu.setPosition((float) ((MyGdxGame.WIDTH / 2) - (sumWidthFirstLine / 2)), positionButton1);
        buttonToReplay.setPosition((float)(buttonToMenu.getX() + buttonToMenu.getWidth() + widthSpace), positionButton1-10);
        buttonToBestScores.setPosition((float) (MyGdxGame.WIDTH / 2) - (bestScoresButton.getWidth() / 2), positionButton2);

    }

    @Override
    protected void bundleInput() {

        if(Gdx.input.justTouched()) {

            Vector2 m = new Vector2(Gdx.input.getX(),Gdx.input.getY());
            Rectangle textureBounds1 = new Rectangle(buttonToMenu.getX(),buttonToMenu.getY(),buttonToMenu.getWidth(),buttonToMenu.getHeight());
            Rectangle textureBounds2 = new Rectangle(buttonToReplay.getX(),buttonToReplay.getY(),buttonToReplay.getWidth(),buttonToReplay.getHeight());
            Rectangle textureBounds3 = new Rectangle(buttonToBestScores.getX(),buttonToBestScores.getY(),buttonToBestScores.getWidth(),buttonToBestScores.getHeight());

            if(textureBounds1.contains(m.x,MyGdxGame.HEIGHT  - m.y)) {
                gsm.set(new MenuState(gsm));
                dispose();
            }

            if(textureBounds2.contains(m.x,MyGdxGame.HEIGHT  - m.y)) {
                gsm.set(new GameState(gsm,"sprite1"));
                dispose();
            }

            if(textureBounds3.contains(m.x,MyGdxGame.HEIGHT  - m.y)) {
                gsm.set(new GameState(gsm,"sprite2")); // A CHANGER !!!
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
        BitmapFont font = new BitmapFont();
        font.setColor(1.0f, 0.0f, 0.0f, 1.0f);
        font.getData().setScale(2);

        sb.begin();
        sb.draw(background, 0, 0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
        sb.draw(title, (MyGdxGame.WIDTH / 2) - (widthTitle / 2), positionTitle, widthTitle, heightTitle);
        sb.draw(scoreImage, (float) ((MyGdxGame.WIDTH / 2) - 0.5*scoreImage.getWidth()), (float) (0.50*MyGdxGame.HEIGHT), (float) (0.5*scoreImage.getWidth()), scoreImage.getHeight());
        sb.draw(timeImage, (float) ((MyGdxGame.WIDTH / 2) - 0.5*timeImage.getWidth()), (float) (0.42*MyGdxGame.HEIGHT), (float) (0.5*timeImage.getWidth()), timeImage.getHeight());
        sb.draw(tokensImage, (float) ((MyGdxGame.WIDTH / 2) - 0.5*tokensImage.getWidth()), (float) (0.34*MyGdxGame.HEIGHT), (float) (0.5*tokensImage.getWidth()), tokensImage.getHeight());
        buttonToMenu.draw(sb);
        buttonToBestScores.draw(sb);
        buttonToReplay.draw(sb);
        font.draw(sb, "11200", (MyGdxGame.WIDTH / 2)+30, (float) (0.50*MyGdxGame.HEIGHT+scoreImage.getHeight()-13));
        font.draw(sb, "120s", (MyGdxGame.WIDTH / 2)+30, (float) (0.42*MyGdxGame.HEIGHT+timeImage.getHeight()-13));
        font.draw(sb, "5898", (MyGdxGame.WIDTH / 2)+30, (float) (0.34*MyGdxGame.HEIGHT+tokensImage.getHeight()-13));
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        title.dispose();
        menuButton.dispose();
        replayButton.dispose();
        bestScoresButton.dispose();
    }
}
