package com.betterclever.zaptap.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.particles.ParallelArray;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by betterclever on 05/01/17.
 */

public class Ripple implements RenderableObject{

    ShapeRenderer renderer;
    Vector2 position;
    float minRadius;
    float maxRadius;

    float curRadius;

    float timeCounter;
    float speedFactor;

    Color alphaWhite;
    Circle circle;

    boolean isIncreasing = true;

    public Ripple(ShapeRenderer shapeRenderer,Vector2 position, float minRadius, float maxRadius){
        renderer = shapeRenderer;
        this.position = position;
        this.maxRadius = maxRadius;
        this.minRadius = minRadius;
        circle = new Circle(position,maxRadius);
        curRadius = minRadius;
        speedFactor =  ( maxRadius - minRadius ) / 40;
        alphaWhite = new Color(1,1,1,0.5f);
    }

    public void render(float delta){

        curRadius += speedFactor*60*delta;
        if(curRadius > maxRadius){
            curRadius = minRadius;
        }

        alphaWhite.a = 1 - (curRadius * (0.4f)/  (maxRadius - minRadius))/speedFactor;

        Gdx.app.log("alpha", String.valueOf(alphaWhite.a));

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(alphaWhite);
        renderer.circle(position.x,position.y,curRadius,128);
        renderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

    }

    public boolean isTouched(Vector2 position){
        if(circle.contains(position)){
            return true;
        }
        return false;
    }

}
