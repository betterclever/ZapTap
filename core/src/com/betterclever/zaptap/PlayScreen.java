package com.betterclever.zaptap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.ConeShapeBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by betterclever on 22/12/16.
 */

public class PlayScreen implements Screen {

    ExtendViewport extendViewport;
    ShapeRenderer shapeRenderer;

    int sw = 1;
    float time;

    float timer;

    int radius;
    DelayedRemovalArray<Renderable> rings;

    @Override
    public void show() {

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        extendViewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        rings = new DelayedRemovalArray<Renderable>();
        time = 0;
        timer = 0;

        radius = 200;
    }

    @Override
    public void render(float delta) {

        time += 2*delta;
        timer += delta;

        radius-=delta;

        if(radius < 10){
            radius =200;
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        extendViewport.apply();
        shapeRenderer.setProjectionMatrix(extendViewport.getCamera().combined);


        if(timer >= 0.5){
            timer-=0.5;

            // dirty hack
            sw = -sw;
            if(sw == 1) {
                rings.add(new CrossMeRing(shapeRenderer, 5));
            }
            else {
                rings.add(new NormalRing(shapeRenderer));
            }
        }

        rings.begin();
        for (int i = 0; i < rings.size; i++) {
            if(rings.get(i).radius < 10){
                rings.removeIndex(i);
            }
        }
        rings.end();

        for (int i = rings.size-1 ; i >= 0 ; i--) {
            rings.get(i).render(delta);
        }
        /*

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.CHARTREUSE);
        shapeRenderer.circle(Constants.WORLD_WIDTH/2,Constants.WORLD_HEIGHT/2,radius,256);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(Constants.WORLD_WIDTH/2, Constants.WORLD_HEIGHT/2, radius-5,256);
        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(Constants.WORLD_WIDTH/2+(radius-2.5f)*MathUtils.sin(time),Constants.WORLD_HEIGHT/2+(radius-2.5f)*MathUtils.cos(time),20,256);
        shapeRenderer.end();*/

    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.log("width", String.valueOf(width));
        extendViewport.update(width,height,true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
