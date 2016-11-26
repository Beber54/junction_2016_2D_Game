package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MyGdxGame;
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
    private Texture texture;
   // private Texture c2;
    //private Texture c3;
    //private Texture c4;

    OrthographicCamera cam;
    Vector3 touchPos;

    public CharactersState(GameStateManager gsm) {
        super(gsm);
        texture = new Texture(Gdx.files.internal("Characters/last-guardian-sprites1.png"));
        background=new Texture(Gdx.files.internal("StartStateResources/background.jpg"));
        letters=new Texture(Gdx.files.internal("Characters/Select-character.png"));
        regions[0] = new TextureRegion(texture, 5, 77, 74, 72);      // #3
        regions[1] = new TextureRegion(texture, -1, 0, 72, 70);    // #4
        regions[2] = new TextureRegion(texture, 88, 1, 70, 72);     // #5
        regions[3] = new TextureRegion(texture, 170, 0, 81, 72);    // #6
    }

    @Override
    protected void bundleInput() {

        if (Gdx.input.isTouched()) {
            //gsm.set(new Ejemplo(gsm,2));
        }

    }

    @Override
    public void update(float dt) {
        bundleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0, MyGdxGame.WIDTH,MyGdxGame.HEIGHT);
        sb.draw(letters,(MyGdxGame.WIDTH/12),MyGdxGame.HEIGHT/2);
        double whites=0.05*MyGdxGame.WIDTH ;
        double widthImage=0.1875*MyGdxGame.WIDTH;
        double incrementX=0;
        for(int i=0;i<4;i++) {
            incrementX+=whites;
            sb.draw(regions[i], (float) incrementX, MyGdxGame.HEIGHT / 4, (float) widthImage,regions[i].getRegionHeight());// #7
            incrementX+=widthImage;

        }

        sb.end();
//

    }

    @Override
    public void dispose() {
        background.dispose();
        letters.dispose();
        texture.dispose();
        for(int i=0;i<4;i++){
           regions[i].getTexture().dispose();
        }

        //selectCharacterButton.dispose();
    }

}
