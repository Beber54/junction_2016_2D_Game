package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.ChangingModeObstacle;
import com.mygdx.game.Sprites.Character;
import com.mygdx.game.Sprites.Obstacle;
import com.mygdx.game.Sprites.Type;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.mygdx.game.Sprites.Type.ChangingMode;

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

    //Score
    private String scoreDisplay;
    BitmapFont scoreFont;

    //Life
    private float life = 10;
    BitmapFont lifeFont;
    private int maxLife;

    //Obstacles
    private int totalObstaclesSpawned = 0;

    //Changing the world
    private String nextWorld;
    BitmapFont nextWorldFont;
    private String countdown;
    BitmapFont countdownFont;

    private Array<Obstacle> obstacles;

    private Music music;

    public GameState(GameStateManager gsm) {
        super(gsm);
        character = new Character(MyGdxGame.WIDTH/2,0);
        block = new Obstacle(0, MyGdxGame.HEIGHT, randomType(currentTypeMode));
        background = new Texture("StartStateResources/background.jpg");

        obstacles = new Array<Obstacle>();

        for(int i= 1; i < OBSTACLE_COUNT; i++ ){
            obstacles.add(new Obstacle(randomPosX(), randomPosY(), randomType(currentTypeMode)));
        }

        triggerScore();

        //Init the score
        scoreDisplay = "0";
        scoreFont = new BitmapFont();

        //Init lifes
        lifeFont = new BitmapFont();

        music = Gdx.audio.newMusic(Gdx.files.internal("Music/retro.mp3"));
        music.setLooping(true);
        music.play();

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
                    totalObstaclesSpawned++;
                    appearChangingModeObstacle(totalObstaclesSpawned);
                    //obstacles.set(i, obstacles.get(i));
                }
            }
            boolean isInCollision = false;
            if((obstacles.get(i).getPosition().y < character.characterSprite.getHeight())
                    && (obstacles.get(i).getPosition().x == character.getPosition().x)) {


                //Check if this is a trap obstacle or not
                if(obstacles.get(i).getElementType() == ChangingMode){
                    //Changing the mode
                    switch(randomType(currentTypeMode)){
                        case Fire:
                            background = new Texture("GameStateResources/backgroundFire.gif");
                            nextWorld = "Fire World";
                            break;
                        case Water:
                            background = new Texture("GameStateResources/backgroundWater.jpg");
                            nextWorld = "Water World";
                            break;
                        case Earth:
                            background = new Texture("GameStateResources/backgroundEarth.jpg");
                            nextWorld = "Earth World";
                            break;
                        case Air:
                            background = new Texture("GameStateResources/backgroundAir.jpg");
                            nextWorld = "Air World";
                            break;
                    }
                    currentSB.begin();
                    currentSB.draw(background,0,0);
                    //Clearing the game level
                    obstacles.clear();
                    //ADD WARNING AND COUNTDOWN
                    nextWorldFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                    nextWorldFont.getData().setScale(20, 30);
                    nextWorldFont.draw(currentSB, nextWorld, MyGdxGame.WIDTH, MyGdxGame.HEIGHT - 200 - lifeFont.getLineHeight());
                    countdownFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                    countdownFont.getData().setScale(40, 70);
                    countdownFont.draw(currentSB, "1", MyGdxGame.WIDTH, MyGdxGame.HEIGHT - 400 - lifeFont.getLineHeight());
                    try {
                        Thread.sleep(1000);
                        countdownFont.draw(currentSB, "2", MyGdxGame.WIDTH, MyGdxGame.HEIGHT - 400 - lifeFont.getLineHeight());
                        Thread.sleep(1000);
                        countdownFont.draw(currentSB, "3", MyGdxGame.WIDTH, MyGdxGame.HEIGHT - 400 - lifeFont.getLineHeight());
                        Thread.sleep(1000);
                        countdownFont.draw(currentSB, "", MyGdxGame.WIDTH, MyGdxGame.HEIGHT - 400 - lifeFont.getLineHeight());
                        nextWorldFont.draw(currentSB, "", MyGdxGame.WIDTH, MyGdxGame.HEIGHT - 200 - lifeFont.getLineHeight());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Relaunching the game
                    for(int j= 1; j < OBSTACLE_COUNT; j++ ){
                        obstacles.add(new Obstacle(randomPosX(), randomPosY(), randomType(currentTypeMode)));
                    }
                    currentSB.end();

                }else {
                    //Update the life of the player
                    updateLife(obstacles.get(i).getElementType(), currentTypeMode);
                    obstacles.get(i).update(dt);
                    float y = obstacles.get(i).getPosition().y;

                    while(y > -obstacles.get(i).getTestTexture().getHeight()) {
                        obstacles.get(i).update(dt);
                        y = obstacles.get(i).getPosition().y;
                    }
                }
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
        if(life < 0) life = 0;
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
        totalObstaclesSpawned ++;
        scoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        scoreFont.draw(sb, scoreDisplay, MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT - scoreFont.getLineHeight());

        lifeFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        lifeFont.draw(sb, String.valueOf(life), 10, MyGdxGame.HEIGHT - lifeFont.getLineHeight());

        for(final Obstacle obs: obstacles){
            sb.draw(obs.getTestTexture(), obs.getPosition().x, obs.getPosition().y, MyGdxGame.WIDTH/4, MyGdxGame.WIDTH/4);
            totalObstaclesSpawned ++;
        }

        sb.end();
    }



    public void dispose(){

    }

    public void appearChangingModeObstacle(int totalObstaclesSpawned){
        Gdx.app.log("appearChangingModeObstacle", "true");
        if(totalObstaclesSpawned == 1 || totalObstaclesSpawned == 50 || totalObstaclesSpawned % 50 == 0){
            Gdx.app.log("appearChangingModeObstacle", "with 50 ratio");
            ChangingModeObstacle randomChange = new ChangingModeObstacle(randomPosX(), randomPosY());
             currentSB.begin();
             currentSB.draw(randomChange.getTestTexture(), randomChange.getPosition().x, randomChange.getPosition().y, MyGdxGame.WIDTH/4, MyGdxGame.WIDTH/4);
             Gdx.app.log("appearChangingModeObstacle position", String.valueOf(randomChange.getPosition().x));
             Gdx.app.log("appearChangingModeObstacle position", String.valueOf(randomChange.getPosition().y));
             currentSB.end();
         }
    }


    public void triggerScore(){
        score = 0;
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                score += 1;
                scoreDisplay = String.valueOf(score);
            }
        }, 100, 100 );
    }
}
