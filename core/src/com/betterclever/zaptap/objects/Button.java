package com.betterclever.zaptap.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
    Color lockedButtonColor;
    BitmapFont font;
    String text;
    SpriteBatch batch;
    GlyphLayout layout;
    Texture lock;
    float fontX,fontY;
    private boolean locked = false;

    public Button(ShapeRenderer renderer, float x, float y, float width, float height, String text, boolean b, BitmapFont font, SpriteBatch batch){
        this.renderer = renderer;
        bounds = new Rectangle(x,y,width,height);
        buttonColor = new Color(1,1,1,0.5f);
        lockedButtonColor = new Color(1,1,1,0.3f);
        this.text = text;
        this.font = font;
        locked = b;
        this.batch = batch;
        layout = new GlyphLayout(font,text);
        lock = new Texture("padlock.png");
        fontX = x + (width - layout.width)/2;
        fontY = y + (height + layout.height)/2;
    }

    public void render(float delta){
        if(locked){

            drawText();
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(lockedButtonColor);
            renderer.rect(bounds.x,bounds.y,bounds.width,bounds.height);
            renderer.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);

            batch.begin();
            batch.draw(lock,bounds.x+bounds.width/2-20,bounds.y+10,40,40);
            batch.end();
        }
        else {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            renderer.begin(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(buttonColor);
            renderer.rect(bounds.x,bounds.y,bounds.width,bounds.height);
            renderer.end();
            drawText();

            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    private void drawText() {
        batch.begin();
        font.draw(batch,text,fontX,fontY);
        batch.end();
    }

    public boolean isTouched(Vector2 position){
        if(!locked) {
            if (bounds.contains(position)) {
                return true;
            }
        }
        return false;
    }

    public void unlock(){
        locked = false;
    }
}
