package com.betterclever.zaptap.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.betterclever.zaptap.utility.Constants;
import com.betterclever.zaptap.utility.FontsUtilty;

/**
 * Created by betterclever on 10/01/17.
 */

public class DevelopersOverlay implements RenderableObject {

    private SpriteBatch spriteBatch;
    private ShapeRenderer renderer;
    private Color alphaBlack;
    private float fontX, fontY;
    private static final String DEVELOPERS = "Developed By";
    private static final String DEV_NAME = "Pranjal Paliwal";
    public Rectangle githubBounds, facebookBounds,googleplusBounds;

    public DevelopersOverlay(ShapeRenderer renderer, SpriteBatch batch){
        spriteBatch = batch;
        this.renderer = renderer;
        alphaBlack = new Color(0,0,0,0.8f);
        GlyphLayout layout = new GlyphLayout(FontsUtilty.DEVELOPERS_SCREEN_FONT,DEVELOPERS);
        fontX = 0 + (Constants.WORLD_WIDTH - layout.width)/2;
        fontY = Constants.WORLD_HEIGHT/4f + (Constants.WORLD_HEIGHT + layout.height)/2;

        facebookBounds = new Rectangle(Constants.WORLD_WIDTH/2 + 90,Constants.WORLD_HEIGHT/2 - 65,50,50);
        githubBounds = new Rectangle(Constants.WORLD_WIDTH/2 + 150,Constants.WORLD_HEIGHT/2 - 65,50,50);
        googleplusBounds = new Rectangle(Constants.WORLD_WIDTH/2 + 210,Constants.WORLD_HEIGHT/2 - 65,50,50);

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
        FontsUtilty.DEVELOPERS_SCREEN_FONT.draw(spriteBatch,DEVELOPERS,fontX,fontY);
        FontsUtilty.GO_TO_HOME_FONT.draw(spriteBatch,DEV_NAME,140,fontY-140);
        FontsUtilty.MODE_BUTTON_FONT.draw(spriteBatch,"(@betterclever)",170,fontY-190);
        spriteBatch.draw(Constants.COPYRIGHT_IMAGE,Constants.WORLD_WIDTH/2-130,20,20,20);
        FontsUtilty.COPYRIGHT_FONT.draw(spriteBatch," CLEVERCORE LABS - 2017",Constants.WORLD_WIDTH/2-110,38);
        spriteBatch.draw(Constants.FACEBOOK_IMAGE,Constants.WORLD_WIDTH/2 + 90,Constants.WORLD_HEIGHT/2 - 65,50,50);
        spriteBatch.draw(Constants.GITHUB_IMAGE,Constants.WORLD_WIDTH/2 + 150,Constants.WORLD_HEIGHT/2 - 65,50,50);
        spriteBatch.draw(Constants.GOOGLEPLUS_IMAGE,Constants.WORLD_WIDTH/2 + 210,Constants.WORLD_HEIGHT/2 - 65,50,50);
        spriteBatch.draw(Constants.CLOSE_IMAGE,Constants.WORLD_WIDTH-60,Constants.WORLD_HEIGHT-60,40,40);
        spriteBatch.end();
    }
}
