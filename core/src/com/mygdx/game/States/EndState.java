package com.mygdx.game.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGdxGame;

/**
 * Created by bertr on 26/11/2016.
 */

public class EndState extends State {

    private Texture background;
    private Texture title, score, timePlayed, tokens;
    private Texture menuButton, replayButton, bestScoresButton;
    private Sprite buttonToMenu, buttonToReplay, buttonToBestScores;

    protected EndState(GameStateManager gsm) {

        super(gsm);
        background = new Texture("EndResources/end.jpg");
        title = new Texture("EndResources/title.png");
        menuButton = new Texture("EndResources/menu.png");
        replayButton = new Texture("EndResources/replay.png");
        bestScoresButton = new Texture("EndResources/bestScores.png");

        double widthSpace = 0.15*MyGdxGame.WIDTH;
        float positionButton1 = (float) 0.18* MyGdxGame.HEIGHT;
        float positionButton2 = (float) 0.1* MyGdxGame.HEIGHT;

        double sumWidthFirstLine = menuButton.getWidth() + widthSpace + replayButton.getWidth();
        buttonToMenu = new Sprite(menuButton);
        buttonToMenu.setPosition((float) ((MyGdxGame.WIDTH / 2) - (sumWidthFirstLine / 2)), positionButton1);
        //buttonToMenu.setSize(widthButton,heightButton);

        buttonToReplay = new Sprite(replayButton);
        buttonToReplay.setPosition((float)(buttonToMenu.getX() + buttonToMenu.getWidth() + widthSpace), positionButton1-10);
        // buttonToReplay.setSize(widthButton,heightButton);

        buttonToBestScores = new Sprite(bestScoresButton);
        buttonToBestScores.setPosition((float) (MyGdxGame.WIDTH / 2) - (bestScoresButton.getWidth() / 2), positionButton2);
      //  buttonToBestScores.setSize(widthButton,heightButton);

    }

    @Override
    protected void bundleInput() {

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
        buttonToMenu.draw(sb);
        buttonToBestScores.draw(sb);
        buttonToReplay.draw(sb);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        title.dispose();
        menuButton.dispose();

    }
}
