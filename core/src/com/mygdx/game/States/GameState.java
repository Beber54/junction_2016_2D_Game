package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Character;
import com.mygdx.game.Sprites.Obstacle;
import com.mygdx.game.Sprites.Type;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Corentin on 26/11/2016.
 */

public class GameState extends State{

    private SpriteBatch currentSB;

    private Character character;
    private Obstacle block;

    private Texture background;

    private Vector3 touch;

    private int previousPosY = 0;

    private static final int OBSTACLE_COUNT = 20;

    public int score = 0;
    public boolean isGameOver = false;

    public Type currentTypeMode = Type.Fire;

    private String scoreDisplay;
    BitmapFont scoreFont;

    private float life = 10;
    BitmapFont lifeFont;
    private int maxLife;


    private Array<Obstacle> obstacles;

    public GameState(GameStateManager gsm) {
        super(gsm);
        character = new Character(MyGdxGame.WIDTH/2,0);
        block = new Obstacle(0, MyGdxGame.HEIGHT, randomType(currentTypeMode));
        background = new Texture("StartStateResources/background.jpg");

        obstacles = new Array<Obstacle>();

        for(int i= 1; i < OBSTACLE_COUNT; i++ ){
            obstacles.add(new Obstacle(randomPosX(), randomPosY(), randomType(currentTypeMode)));
        }



        //Init the score
        scoreDisplay = "0";
        scoreFont = new BitmapFont();

        //Init lifes
        lifeFont = new BitmapFont();
    }

    private int randomPosX(){
        double randomNumber = Math.floor(Math.random() * 4);
        int tempNumber = (int) randomNumber;
        int finalPos = tempNumber * MyGdxGame.WIDTH/4;
        return finalPos;
    }

    private int randomPosY(){
        //double randomNumber = Math.floor((Math.random() * 4) * (MyGdxGame.HEIGHT - block.getTestTexture().getHeight()));
        int finalPos = (int) (MyGdxGame.HEIGHT - block.getTestTexture().getHeight()) + previousPosY;
        previousPosY += 1.5 * block.getTestTexture().getHeight();
        return finalPos;
    }

    @Override
    protected void bundleInput() {
        if(Gdx.input.justTouched()){
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();

            if(x > MyGdxGame.WIDTH /2){
                character.moveRight();
            }else if(x < MyGdxGame.WIDTH/2){
                character.moveLeft();
            }
        }
    }


    @Override
    public void update(float dt) {
        bundleInput();
        character.update(dt);

        for(int i = 0; i < obstacles.size; i++) {

            obstacles.get(i).update(dt);

            if (obstacles.get(i).getPosition().y < 0) {
                if(obstacles.size < OBSTACLE_COUNT) {
                    obstacles.get(i).getPosition().x = randomPosX();
                    obstacles.get(i).getPosition().y = randomPosY();
                    //obstacles.set(i, obstacles.get(i));
                }
            }
            boolean isInCollision = false;
            if((obstacles.get(i).getPosition().y < character.characterSprite.getHeight())
                    && (obstacles.get(i).getPosition().x == character.getPosition().x)) {
                Gdx.app.log("collides", "true");
                isInCollision = true;

                //Update the life of the player
                do {
                    updateLife(obstacles.get(i).getElementType(), currentTypeMode);
                }while(!isInCollision);
            }
        }
    }

    public Type randomType(Type currentTypeMode){
        Random rand = new Random();
        Type finalType = currentTypeMode;
        int randomTypeNumber = rand.nextInt((3 - 1) + 1) + 1;
        switch (currentTypeMode){
            case Fire:
                switch(randomTypeNumber){
                    case 1:
                        finalType = Type.Air;
                        break;
                    case 2:
                        finalType = Type.Earth;
                        break;
                    case 3:
                        finalType = Type.Water;
                        break;
                }
                break;
            case Earth:
                switch(randomTypeNumber){
                    case 1:
                        finalType = Type.Air;
                        break;
                    case 2:
                        finalType = Type.Fire;
                        break;
                    case 3:
                        finalType = Type.Water;
                        break;
                }
                break;
            case Water:
                switch(randomTypeNumber){
                    case 1:
                        finalType = Type.Air;
                        break;
                    case 2:
                        finalType = Type.Fire;
                        break;
                    case 3:
                        finalType = Type.Earth;
                        break;
                }
                break;
            case Air:
                switch(randomTypeNumber){
                    case 1:
                        finalType = Type.Water;
                        break;
                    case 2:
                        finalType = Type.Fire;
                        break;
                    case 3:
                        finalType = Type.Earth;
                        break;
                }
                break;
        }
        return finalType;
    }

    public float updateLife(Type elementTypeCatched, Type currentTypeMode){
        int changingLifes = 0;
        switch(currentTypeMode){
            case Fire:
                switch(elementTypeCatched){
                    case Water:
                        changingLifes = -2;
                        break;
                    case Earth:
                        changingLifes = -1;
                        break;
                    case Air:
                        changingLifes = 1;
                        break;
                }
                break;
            case Water:
                switch(elementTypeCatched){
                    case Fire:
                        changingLifes = +1;
                        break;
                    case Earth:
                        changingLifes = -2;
                        break;
                    case Air:
                        changingLifes = -1;
                        break;
                }
                break;
            case Earth:
                switch(elementTypeCatched){
                    case Water:
                        changingLifes = -2;
                        break;
                    case Fire:
                        changingLifes = -1;
                        break;
                    case Air:
                        changingLifes = +1;
                        break;
                }
                break;
            case Air:
                switch(elementTypeCatched){
                    case Water:
                        changingLifes = -1;
                        break;
                    case Earth:
                        changingLifes = +1;
                        break;
                    case Fire:
                        changingLifes = +1;
                        break;
                }
                break;
        }

        life = life + changingLifes;
        currentSB.begin();
        lifeFont.draw(currentSB, String.valueOf(life), 10, MyGdxGame.HEIGHT - lifeFont.getLineHeight());
        currentSB.end();
        return life;
    }

    @Override
    public void render(final SpriteBatch sb) {
        currentSB = sb;
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(character.getCharacter(), character.getPosition().x, character.getPosition().y, MyGdxGame.WIDTH/4, character.characterSprite.getHeight());
        sb.draw(block.getTestTexture(), block.getPosition().x, block.getPosition().y, MyGdxGame.WIDTH/4, MyGdxGame.WIDTH/4);

        scoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        scoreFont.draw(sb, scoreDisplay, MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT - scoreFont.getLineHeight());

        lifeFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        lifeFont.draw(sb, String.valueOf(life), 10, MyGdxGame.HEIGHT - lifeFont.getLineHeight());

        for(final Obstacle obs: obstacles){
            sb.draw(obs.getTestTexture(), obs.getPosition().x, obs.getPosition().y, MyGdxGame.WIDTH/4, MyGdxGame.WIDTH/4);
        }

        sb.end();
    }



    public void dispose(){

    }

    public void triggerScore(){
        score = 0;

        new Timer().schedule(new TimerTask()
        {

            @Override
            public void run()
            {

                    score += 10;
                    scoreDisplay = String.valueOf(score);
                    Gdx.app.log("score", String.valueOf(score));

            }
        }, 1000 );

    }
}
