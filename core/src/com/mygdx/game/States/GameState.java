package com.mygdx.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Sprites.Character;
import com.mygdx.game.Sprites.Obstacle;

/**
 * Created by Corentin on 26/11/2016.
 */

public class GameState extends State{

    private Character character;
    private Obstacle block;

    private Texture background;

    private Vector3 touch;

    private int previousPosY = 0;

    private static final int OBSTACLE_COUNT = 25;


    private Array<Obstacle> obstacles;

    public GameState(GameStateManager gsm) {
        super(gsm);
        character = new Character(MyGdxGame.WIDTH/2,0);
        block = new Obstacle(0, MyGdxGame.HEIGHT);
        background = new Texture("StartStateResources/background.jpg");

        obstacles = new Array<Obstacle>();

        for(int i= 1; i < OBSTACLE_COUNT; i++ ){
            obstacles.add(new Obstacle(randomPosX(), randomPosY()));
        }
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
        Gdx.app.log("randomPosY",String.valueOf(finalPos));
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
        block.update(dt);

        for(Obstacle obs : obstacles){
            obs.update(dt);
            if(obs.getPosition().y < 0){
                obs = new Obstacle(0,MyGdxGame.HEIGHT);
            }

            if(obs.collides(character.getBounds())){
                Gdx.app.log("collides","true");
            }else{
            }
        }
    }

    @Override
    public void render(final SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(character.getCharacter(), character.getPosition().x, character.getPosition().y, MyGdxGame.WIDTH/4, character.characterSprite.getHeight());
        sb.draw(block.getTestTexture(), block.getPosition().x, block.getPosition().y, MyGdxGame.WIDTH/4, MyGdxGame.WIDTH/4);

        for(final Obstacle obs: obstacles){
            sb.draw(obs.getTestTexture(), obs.getPosition().x, obs.getPosition().y, MyGdxGame.WIDTH/4, MyGdxGame.WIDTH/4);
        }

        sb.end();
    }



    public void dispose(){

    }
}
