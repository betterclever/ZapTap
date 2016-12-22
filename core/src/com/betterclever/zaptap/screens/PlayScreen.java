package com.betterclever.zaptap.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.betterclever.zaptap.Constants;
import com.betterclever.zaptap.objects.CrossMeRing;
import com.betterclever.zaptap.objects.NormalRing;
import com.betterclever.zaptap.objects.PlayBall;
import com.betterclever.zaptap.objects.Ring;

/**
 * Created by betterclever on 22/12/16.
 */

public class PlayScreen extends InputAdapter implements Screen {

    ExtendViewport extendViewport;
    ShapeRenderer shapeRenderer;

    int sw = 1;
    float time;

    float timer;

    int radius;
    DelayedRemovalArray<Ring> rings;
    PlayBall playBall;

    @Override
    public void show() {

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        extendViewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        Gdx.input.setInputProcessor(this);
        rings = new DelayedRemovalArray<Ring>();
        time = 0;
        timer = 0;

        playBall = null;
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
                NormalRing q = new NormalRing(shapeRenderer);
                rings.add(q);
                if(playBall == null){
                    playBall = new PlayBall(shapeRenderer,q);
                }
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

        if(playBall!=null){
            playBall.render(delta);
        }

        if(playBall.isDetached()){
            for (int i = 0; i < rings.size; i++) {



            }
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
        //Gdx.app.log("width", String.valueOf(width));
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);

        playBall.detach();
        Gdx.app.log("Hi","touchDown");

        return true;
    }
}
