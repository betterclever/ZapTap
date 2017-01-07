package com.betterclever.zaptap.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by betterclever on 07/01/17.
 */

public class CircleButton implements RenderableObject {

    ShapeRenderer renderer;
    Circle bounds;
    Color color;

    public CircleButton(ShapeRenderer renderer, float scrX, float scrY, int radius){
        this.renderer = renderer;
        bounds = new Circle(scrX,scrY,radius);
        color = new Color(1,1,1,0.3f);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color);
        renderer.circle(bounds.x,bounds.y,bounds.radius,128);
        renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public boolean isTouched(Vector2 position){
        if(bounds.contains(position)){
            return true;
        }
        return false;
    }
}
