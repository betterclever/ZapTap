package com.betterclever.zaptap.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by betterclever on 07/01/17.
 */

public class Button implements RenderableObject {

    ShapeRenderer renderer;
    Rectangle bounds;
    Color buttonColor;
    BitmapFont font;
    String text;
    SpriteBatch batch;
    GlyphLayout layout;
    float fontX,fontY;

    public Button(ShapeRenderer renderer, float x, float y, float width, float height , String text, BitmapFont font, SpriteBatch batch){
        this.renderer = renderer;
        bounds = new Rectangle(x,y,width,height);
        buttonColor = new Color(1,1,1,0.5f);
        this.text = text;
        this.font = font;
        this.batch = batch;
        layout = new GlyphLayout(font,text);
        fontX = x + (width - layout.width)/2;
        fontY = y + (height + layout.height)/2;
    }

    public void render(float delta){
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(buttonColor);
        renderer.rect(bounds.x,bounds.y,bounds.width,bounds.height);
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        drawText();
    }

    private void drawText() {
        batch.begin();
        font.draw(batch,text,fontX,fontY);
        batch.end();
    }

    public boolean isTouched(Vector2 position){
        if(bounds.contains(position)){
            return true;
        }
        return false;
    }
}
