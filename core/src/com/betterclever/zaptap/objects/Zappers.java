package com.betterclever.zaptap.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.betterclever.zaptap.Constants;
import com.betterclever.zaptap.Encrypt;

/**
 * Created by betterclever on 10/01/17.
 */

public class Zappers implements RenderableObject {

    private SpriteBatch spriteBatch;
    private ShapeRenderer renderer;
    private BitmapFont bitmapFont;
    private Texture zapperImage;
    private Vector2 position;
    private int zapperCount;
    private Preferences preferences;

    float timePassed;

    public Zappers(SpriteBatch spriteBatch, ShapeRenderer renderer, Preferences preferences) {
        this.spriteBatch = spriteBatch;
        this.renderer = renderer;
        this.preferences = preferences;

        position = new Vector2(10,Constants.WORLD_HEIGHT-70);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Teacher_a.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.color= Color.WHITE;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        bitmapFont = generator.generateFont(parameter);
        zapperImage = new Texture("zapper.png");
        zapperCount = Encrypt.decrypt(preferences.getString(Constants.ZAPPER_COUNT));
    }

    @Override
    public void render(float delta) {

        timePassed += delta;

        if(timePassed>2){
            timePassed-=2;
            updateZapperCount();
        }

        spriteBatch.begin();
        spriteBatch.draw(zapperImage,position.x,position.y,50,50);
        bitmapFont.draw(spriteBatch,String.valueOf(zapperCount),position.x+50,position.y+40);
        spriteBatch.end();


    }

    public void updateZapperCount(){
        zapperCount = Encrypt.decrypt(preferences.getString(Constants.ZAPPER_COUNT));
    }



}
