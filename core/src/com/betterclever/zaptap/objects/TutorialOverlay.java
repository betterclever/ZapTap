package com.betterclever.zaptap.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.betterclever.zaptap.utility.Constants;
import com.betterclever.zaptap.utility.FontsUtilty;

/**
 * Created by betterclever on 14/01/17.
 */

public class TutorialOverlay implements RenderableObject {

    SpriteBatch spriteBatch;
    ShapeRenderer renderer;
    Color alphaBlack;

    public TutorialOverlay (SpriteBatch spriteBatch, ShapeRenderer renderer){
        this.spriteBatch = spriteBatch;
        this.renderer = renderer;

        alphaBlack = new Color(0,0,0,0.8f);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(alphaBlack);
        renderer.rect(0,0, Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT);
        renderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        spriteBatch.begin();
        FontsUtilty.DEVELOPERS_SCREEN_FONT.draw(spriteBatch,"How To Play?",Constants.WORLD_WIDTH/2-150,Constants.WORLD_HEIGHT/2+130);
        
        spriteBatch.end();


    }
}
