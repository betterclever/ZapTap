package com.betterclever.zaptap.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import javax.swing.plaf.synth.ColorType;

/**
 * Created by betterclever on 24/12/16.
 */

public class ExplosionTriangle {

    ShapeRenderer renderer;
    Vector2 position;
    float angle;
    float size;
    float f = 0.0f;
    Color color;
    float time;
    float speedFactor;

    public ExplosionTriangle(ShapeRenderer renderer, Vector2 position, float angle){
        this.renderer = renderer;
        this.position = new Vector2(position);
        this.angle = angle;
        size = MathUtils.random();
        f = MathUtils.random(0.5f,1.2f);
        color = new Color(MathUtils.random(),MathUtils.random(),MathUtils.random(),1);
        speedFactor = MathUtils.random(1f,2f);
    }

    public void render(float delta){

        time += delta;

        position.x += speedFactor * 400 * delta * MathUtils.sinDeg(angle);
        position.y += speedFactor * 400 * delta * MathUtils.cosDeg(angle);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(color);
        renderer.identity();
        renderer.translate(position.x,position.y,0);
        renderer.rotate(0f,0f,1f,angle);
        renderer.triangle(0f-f*15f, 0f-f*13f, 0+f*15f, 0f-f*13f, 0f,0f+f*13f);
        renderer.rotate(0f,0f,1f,-angle);
        renderer.translate(-position.x,-position.y,0);
        renderer.end();
    }

    public float getTime() {
        return time;
    }
}
