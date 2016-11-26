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
import com.mygdx.game.States.State;

/**
 * Created by Ian on 26/11/2016.
 */

public class CharactersState extends State {
    private Texture background;
    private Texture letters;
    //private Texture c1;
    private TextureRegion[] regions = new TextureRegion[4];
    private TextureRegion[] regionsb = new TextureRegion[4];
    private Texture texture;
    private Sprite buttonToPlay[]=new Sprite[4];
    private Sprite buttonToPlay2[]=new Sprite[4];
    Rectangle textureBounds[]= new Rectangle[4];

    OrthographicCamera cam;
    Vector3 mouse;

    public CharactersState(GameStateManager gsm) {

        super(gsm);
        texture = new Texture(Gdx.files.internal("Characters/last-guardian-sprites1.png"));
        background = new Texture(Gdx.files.internal("StartStateResources/background.jpg"));
        letters = new Texture(Gdx.files.internal("Characters/Select-character.png"));

        regions[0] = new TextureRegion(texture, 5, 77, 74, 72);      // #3
        regions[1] = new TextureRegion(texture, -1, 0, 72, 70);    // #4
        regions[2] = new TextureRegion(texture, 88, 1, 70, 72);     // #5
        regions[3] = new TextureRegion(texture, 170, 0, 81, 72);
        regionsb[0] = new TextureRegion(texture, 176,152, 65, 76);
        regionsb[1] = new TextureRegion(texture, 0, 234, 67, 70);
        regionsb[2] = new TextureRegion(texture, 345, 82, 73, 64);
        regionsb[3] = new TextureRegion(texture, 90, 81, 68, 69);// #6

        for(int j=0;j<4;j++){
            buttonToPlay[j]=new Sprite(regions[j].getTexture());
            buttonToPlay2[j]=new Sprite(regionsb[j].getTexture());
        }

    }

    @Override
    protected void bundleInput() {

        if(Gdx.input.isKeyPressed(Input.Keys.P)) {

            Vector3 m = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            cam.unproject(m);
            gsm.set(new GameState(gsm, "nameCharacter"));
            dispose();

        }
            if (Gdx.input.justTouched()) {
                Vector2 m = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                for (int x = 0; x < 4; x++) {
                    textureBounds[x]= new Rectangle(buttonToPlay[x].getX(), buttonToPlay[x].getY(), buttonToPlay[x].getWidth(), buttonToPlay[x].getHeight());
                    // Rectangle textureBounds2 = new Rectangle(buttonToSelect.getX(),buttonToSelect.getY(),buttonToSelect.getWidth(),buttonToSelect.getHeight());
                    if (textureBounds[x].contains(m.x, MyGdxGame.HEIGHT - m.y)) {
                        Gdx.app.log("MyTag", "joder");
                    }

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
        int heightTitle = (int)(0.85*letters.getHeight());
        int positionTitle = (int)(0.7*MyGdxGame.HEIGHT);

        sb.begin();
        sb.draw(background,0,0, MyGdxGame.WIDTH,MyGdxGame.HEIGHT);
        sb.draw(letters, (MyGdxGame.WIDTH / 2) - (widthTitle / 2), positionTitle, widthTitle, heightTitle);

        double whites=0.05*MyGdxGame.WIDTH ;
        double widthImage=0.1875*MyGdxGame.WIDTH;
        double incrementX=0;

        for(int i=0;i<4;i++) {
            incrementX += whites;
            sb.draw(regions[i], (float) incrementX, (float) (MyGdxGame.HEIGHT / 2.5), (float) widthImage,regions[i].getRegionHeight());// #7
            sb.draw(regionsb[i], (float) incrementX, (float) (MyGdxGame.HEIGHT / 3.5), (float) widthImage,regionsb[i].getRegionHeight());
            incrementX += widthImage;
        }

        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        letters.dispose();
        texture.dispose();
        for(int i=0;i<4;i++){
           regions[i].getTexture().dispose();
        }
    }

}
