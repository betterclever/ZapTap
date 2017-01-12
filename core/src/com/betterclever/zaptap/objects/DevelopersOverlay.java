package com.betterclever.zaptap.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.betterclever.zaptap.Constants;

/**
 * Created by betterclever on 10/01/17.
 */

public class DevelopersOverlay implements RenderableObject {

    private SpriteBatch spriteBatch;
    private ShapeRenderer renderer;
    private BitmapFont font1, font2;
    private Color alphaBlack;
    private float fontX, fontY;
    private static final String DEVELOPERS = "Developers";
    private static final String DEV_NAME = "betterclever";

    public DevelopersOverlay(ShapeRenderer renderer, SpriteBatch batch, BitmapFont font1, BitmapFont font2){
        spriteBatch = batch;
        this.renderer = renderer;
        alphaBlack = new Color(0,0,0,0.8f);
        this.font1 = font1;
        this.font2 = font2;
        GlyphLayout layout = new GlyphLayout(font1,DEVELOPERS);
        fontX = 0 + (Constants.WORLD_WIDTH - layout.width)/2;
        fontY = Constants.WORLD_HEIGHT/4 + (Constants.WORLD_HEIGHT + layout.height)/2;

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
        font1.draw(spriteBatch,DEVELOPERS,fontX,fontY);
        font2.draw(spriteBatch,DEV_NAME,fontX + 250,fontY-150);
        spriteBatch.end();
    }
}
