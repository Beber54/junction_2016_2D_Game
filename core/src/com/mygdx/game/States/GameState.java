package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    private Obstacle newObs;

    private Texture background;

    private Vector3 touch;

    private int previousPosY = 0;

    private int OBSTACLE_COUNT = 20;

    public int score = 0;
    public boolean isGameOver = false;

    private int timePlayed = 0;

    public Type currentTypeMode = Type.Earth;

    //Score
    private String scoreDisplay;
    BitmapFont scoreFont;

    //Life
    private int life = 1;
    BitmapFont lifeFont;
    private int maxLife;
    private Texture heart = new Texture("GameStateResources/heart.png");

    BitmapFont tokensFont;
    private Texture token;


    //Obstacles
    private int totalObstaclesSpawned = 0;

    //Changing the world
    private String nextWorld;
    BitmapFont nextWorldFont;
    private String countdown;
    BitmapFont countdownFont;

    private Array<Obstacle> obstacles;

    private Music music;

    public GameState(GameStateManager gsm, String nameSelectedCharacter) {
        super(gsm);
        character = new Character(MyGdxGame.WIDTH/2,0, nameSelectedCharacter);
        block = new Obstacle(0, MyGdxGame.HEIGHT, randomType(currentTypeMode));
        background = new Texture("StartStateResources/background.jpg");

        obstacles = new Array<Obstacle>();

        for(int i= 1; i < OBSTACLE_COUNT; i++ ){
            obstacles.add(new Obstacle(randomPosX(), randomPosY(), randomType(currentTypeMode)));
        }

        triggerScore();
        triggerTime();

        randomType(currentTypeMode);

        //Init the score
        scoreDisplay = "0";
        scoreFont = new BitmapFont();

        //Init lifes
        lifeFont = new BitmapFont();

        //Tokens
        tokensFont = new BitmapFont();

        //New world
        nextWorldFont = new BitmapFont();
        countdownFont = new BitmapFont();

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
                totalObstaclesSpawned ++;
                if(obstacles.size < OBSTACLE_COUNT) {
                    obstacles.get(i).getPosition().x = randomPosX();
                    obstacles.get(i).getPosition().y = randomPosY();
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
                            background = new Texture("GameStateResources/backgroundFire.png");
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
                    //Clearing the game level
                    obstacles.clear();
                    //ADD WARNING AND COUNTDOWN
                    nextWorldFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                    nextWorldFont.draw(currentSB, nextWorld, MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT - 200 - nextWorldFont.getLineHeight());
                    countdownFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                    countdownFont.draw(currentSB, "1", MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT - 400 - countdownFont.getLineHeight());
                    /*try {
                        Thread.sleep(1000);
                        countdownFont.draw(currentSB, "2", MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT - 400 - countdownFont.getLineHeight());
                        Thread.sleep(1000);
                        countdownFont.draw(currentSB, "3", MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT - 400 - countdownFont.getLineHeight());
                        Thread.sleep(1000);
                        //countdownFont.draw(currentSB, "", MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT - 400 - countdownFont.getLineHeight());
                        //nextWorldFont.draw(currentSB, "", MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT - 200 - nextWorldFont.getLineHeight());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                    currentSB.draw(background,0,0, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
                    //Reset of the height of y axis
                    previousPosY = 0;
                    //Relaunching the game
                    for(int j= 1; j < OBSTACLE_COUNT; j++ ){
                        Obstacle newObs = new Obstacle(randomPosX(), randomPosY(), randomType(currentTypeMode));
                        obstacles.add(newObs);
                        currentSB.draw(newObs.getTestTexture(), newObs.getPosition().x, newObs.getPosition().y, MyGdxGame.WIDTH/4, MyGdxGame.WIDTH/4);
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
        Gdx.app.log("randomTypeNumber", String.valueOf(randomTypeNumber));
        Gdx.app.log("randomTypeNumber", String.valueOf(currentTypeMode));
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
                        finalType = Type.Fire;
                        break;
                    case 2:
                        finalType = Type.Air;
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
                        finalType = Type.Earth;
                        break;
                    case 3:
                        finalType = Type.Fire;
                        break;
                }
                break;
        }
        Gdx.app.log("current type", String.valueOf(finalType));
        this.currentTypeMode = finalType;
        return finalType;
    }

    public float updateLife(Type elementTypeCatched, Type currentTypeMode){
        life = 0; //DEBUG
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
        if(life == 0 || life < 0){
            life = 0;
            //GAME OVER TO DO
            gsm.set(new EndState(gsm));
            saveHighScore(score);
            saveTokens(score);
            dispose();
        }
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
        sb.draw(character.getCharacter(), character.getPosition().x, character.getPosition().y, MyGdxGame.WIDTH/4, MyGdxGame.WIDTH/4f);
        sb.draw(block.getTestTexture(), block.getPosition().x, block.getPosition().y, MyGdxGame.WIDTH/4, MyGdxGame.WIDTH/4);
        scoreFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        scoreFont.getData().setScale(3,3);
        scoreFont.draw(sb, scoreDisplay, MyGdxGame.WIDTH / 2 - scoreFont.getSpaceWidth(), MyGdxGame.HEIGHT - 10);
        sb.draw(heart,40,MyGdxGame.HEIGHT - (lifeFont.getLineHeight()*2),lifeFont.getLineHeight()*2,lifeFont.getLineHeight()*2);
        lifeFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        lifeFont.getData().setScale((float)1.3, (float)1.3);
        lifeFont.draw(sb, String.valueOf(life), 10, MyGdxGame.HEIGHT - 10);

        tokensFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        tokensFont.draw(sb, String.valueOf(MenuState.getTokens()), MyGdxGame.WIDTH - 100, MyGdxGame.HEIGHT - tokensFont.getLineHeight());
        for(final Obstacle obs: obstacles){
            sb.draw(obs.getTestTexture(), obs.getPosition().x, obs.getPosition().y, MyGdxGame.WIDTH/4, MyGdxGame.WIDTH/4);
        }

        sb.end();
    }



    public void dispose(){

    }

    public void appearChangingModeObstacle(int totalObstaclesSpawned){
        Gdx.app.log("totalObstaclesSpawned", String.valueOf(totalObstaclesSpawned));
        if( totalObstaclesSpawned % 50 == 0){
            Gdx.app.log("appearChangingModeObstacle", "with 50 ratio");
            ChangingModeObstacle randomChange = new ChangingModeObstacle(randomPosX(), randomPosY());
            obstacles.add(randomChange);
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

    public void addObstacles(){
        newObs = new Obstacle(randomPosX(), randomPosY(), randomType(currentTypeMode));
        obstacles.add(newObs);
        currentSB.begin();
        currentSB.draw(newObs.getTestTexture(), newObs.getPosition().x, newObs.getPosition().y, MyGdxGame.WIDTH/4, MyGdxGame.WIDTH/4);
        currentSB.end();
    }

    public void triggerTime(){
        score = 0;
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                timePlayed += 1;
                if(timePlayed % 20 == 0){
                    Obstacle.speed = Obstacle.speed - 50;

                    int newSpeed = Obstacle.speed;
                    for(int i = 0; i < obstacles.size; i++) {
                        obstacles.get(i).speed = newSpeed;
                    }
                    for(int j = 0; j < 2; j++){
                        //addObstacles();
                    }
                    Gdx.app.log("Obstacle.speed",String.valueOf(Obstacle.speed));
                    Gdx.app.log("Obstacle.speed",String.valueOf(OBSTACLE_COUNT));
                }
            }
        }, 1000, 1000 );
    }

    public void saveTokens(int score){
        try{
            int tokensToAdd = 0;
            if(score < 200){
                tokensToAdd = 10;
            }else{
                tokensToAdd = (int)Math.ceil(score/100);
            }
            Gdx.app.log("save tokens SCORE",String.valueOf(score));
            FileHandle file = Gdx.files.local("Data/tokens.txt");


            int currentTokens = MenuState.getTokens();
            String finalTokens = String.valueOf(currentTokens + tokensToAdd);
            Gdx.app.log("tokens total ",String.valueOf(currentTokens + tokensToAdd));
            Gdx.app.log("save tokens total ",finalTokens);
            file.writeString(finalTokens, false);


        }catch(Exception ex){
            Gdx.app.log("save tokens","failure");
            ex.printStackTrace();
        }
    }

    public void saveHighScore(int scorePassed){
        // determine the high score
        String[] highscores = getAllHighScore();
        for(int i =0; i < highscores.length; i++){
            if(scorePassed >= Integer.parseInt(highscores[i].replace("\\n",""))){
                highscores[i] = String.valueOf(scorePassed) + "\\n" + highscores[i];
                //shighscores[5] = "";
            }
        }

        FileHandle file = Gdx.files.local("Data/highscores.txt");
        for(int i =0; i < highscores.length; i++) {
            file.writeString(highscores[i] + "\\n", false);
        }
    }

    public int getHighScore(){
        int highScore;
        FileHandle handle = Gdx.files.local("Data/highscores.txt");
        String text = handle.readString();
        String wordsArray[] = text.split("\\n");
        highScore = Integer.parseInt(wordsArray[0].trim());
        return highScore;
    }

    public String[] getAllHighScore(){
        FileHandle handle = Gdx.files.local("Data/highscores.txt");
        String text = handle.readString();
        String wordsArray[] = text.split("\\n");
        return wordsArray;
    }
}
