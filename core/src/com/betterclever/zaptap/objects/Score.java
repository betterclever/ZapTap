package com.betterclever.zaptap.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.betterclever.zaptap.Constants;
import com.betterclever.zaptap.FontsUtilty;

/**
 * Created by betterclever on 01/01/17.
 */

public class Score implements RenderableObject {

    private int score = 0;
    SpriteBatch batch;

    public Score(SpriteBatch spriteBatch){
        batch = spriteBatch;
    }

    public void render(float delta){
        batch.begin();
        FontsUtilty.SCORE_FONT.draw(batch,""+score,10,Constants.WORLD_HEIGHT-20);
        batch.end();
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
