package com.betterclever.zaptap.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.betterclever.zaptap.Constants;

/**
 * Created by betterclever on 01/01/17.
 */

public class Score implements RenderableObject {

    private int score = 0;
    BitmapFont font;
    SpriteBatch batch;

    public Score(SpriteBatch spriteBatch){
        batch = spriteBatch;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Teacher_a.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.color= Color.WHITE;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose();// don't forget to dispose to avoid memory leaks!
    }

    public void render(float delta){
        batch.begin();
        font.draw(batch,""+score,10,Constants.WORLD_HEIGHT-50);
        batch.end();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void increase(){
        score++;
    }

    public int getScore() {
        return score;
    }

    public void reset() {
        score = 0;
    }
}
