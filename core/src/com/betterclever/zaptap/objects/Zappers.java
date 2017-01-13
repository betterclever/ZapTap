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
import com.betterclever.zaptap.FontsUtilty;

/**
 * Created by betterclever on 10/01/17.
 */

public class Zappers implements RenderableObject {

    private SpriteBatch spriteBatch;
    private ShapeRenderer renderer;
    private Texture zapperImage;
    private Vector2 position;
    private int zapperCount;
    private Preferences preferences;
    private int pendingZappers;

    float timePassed;

    public Zappers(SpriteBatch spriteBatch, ShapeRenderer renderer, Preferences preferences, Vector2 position) {
        this.spriteBatch = spriteBatch;
        this.renderer = renderer;
        this.preferences = preferences;
        this.position = position;
        pendingZappers = 0;
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
        FontsUtilty.ZAPPER_FONT.draw(spriteBatch,String.valueOf(zapperCount),position.x+50,position.y+50);
        if(pendingZappers > 0) {
            FontsUtilty.PENDING_ZAPPER_FONT.draw(spriteBatch, "+ " + pendingZappers, position.x + 50, position.y + 5);
        }
        spriteBatch.end();

    }

    private void updateZapperCount(){
        zapperCount = Encrypt.decrypt(preferences.getString(Constants.ZAPPER_COUNT));
    }

    public void increasePendingZappers(){
        pendingZappers += 15;
    }

    public void giveInitZappers(){
        pendingZappers += 5;
    }

    public void resetPendingZappers(){
        pendingZappers = 0;
    }

    public int getPendingZappers() {
        return pendingZappers;
    }
}
