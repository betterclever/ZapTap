package com.betterclever.zaptap.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.betterclever.zaptap.Constants;
import com.betterclever.zaptap.ZapTapGame;
import com.betterclever.zaptap.objects.CrossMeRing;
import com.betterclever.zaptap.objects.ExplosionTriangle;
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
    DelayedRemovalArray<ExplosionTriangle> explosionTriangles;

    PlayBall playBall;

    Ring lastAttachedRing;
    ZapTapGame game;

    boolean stopped = false;

    public PlayScreen(ZapTapGame zapTapGame) {
        game = zapTapGame;
    }

    @Override
    public void show() {

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        extendViewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        Gdx.input.setInputProcessor(this);
        rings = new DelayedRemovalArray<Ring>();
        explosionTriangles = new DelayedRemovalArray<ExplosionTriangle>();

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


        if(!stopped) {
            if (timer >= 0.5) {
                timer -= 0.5;

                // dirty hack
                sw = -sw;
                if (sw == 1) {
                    rings.add(new CrossMeRing(shapeRenderer, MathUtils.random(2, 6)));
                } else {
                    NormalRing q = new NormalRing(shapeRenderer);
                    rings.add(q);
                    if (playBall == null) {
                        playBall = new PlayBall(shapeRenderer, q);
                    }
                }
            }
        }

        rings.begin();
        for (int i = 0; i < rings.size; i++) {
            if(rings.get(i).radius < 20){
                rings.removeIndex(i);
            }
        }
        rings.end();

        for (int i = rings.size-1 ; i >= 0 ; i--) {
            rings.get(i).render(delta);
        }

        for (ExplosionTriangle tri: explosionTriangles) {
            tri.render(delta);
        }

        explosionTriangles.begin();
        for (int i = 0; i < explosionTriangles.size ; i++) {
            if(explosionTriangles.get(i).getTime() > 8f){
                explosionTriangles.removeIndex(i);
            }
        }
        explosionTriangles.end();


        handleBall(delta);

    }

    private void handleBall(float delta) {

        if(playBall!=null){
            playBall.render(delta);

            if(playBall.isDetached()){

                for (int i = rings.size -1 ; i >= 0; i--) {

                    Ring ring = rings.get(i);

                    if(ring.equals(lastAttachedRing)){
                        continue;
                    }

                    int r = ring.radius;

                    //Gdx.app.log("radius", String.valueOf(r));
                    float diff = r - playBall.getRotateRadius();

                    //Gdx.app.log("diff", String.valueOf(diff));
                    if(diff<7.5f && diff > 0){
                        if(ring.getClass().equals(NormalRing.class)){
                            playBall.setAttachedRing((NormalRing) ring);
                            break;
                        }
                        else {

                            CrossMeRing cmr = (CrossMeRing) ring;
                            float ballAngle = playBall.getAngle() ;

                            int arcCount = cmr.getArcNum();
                            float rot = cmr.getRot();

                            //Gdx.app.log("arccount", String.valueOf(arcCount));

                            for (int j = 0; j < arcCount; j++) {

                                //Gdx.app.log("check angle", String.valueOf(360/arcCount * j - rot));
                                float q1 = 360/arcCount * j + rot;
                                float q2 = q1 + 180/arcCount  ;

                                float ballAngle1 = ballAngle;

                                //Gdx.app.log("q1 : q2 : ballAngle",""+q1+" "+q2+" "+ballAngle1);


                                if(ballAngle1 >= (q1-5) && ballAngle1 <= (q2+5) ){

                                    //Gdx.app.log(" Collision","detected");

                                    int n = MathUtils.random(10,20);
                                    for(int k=0;k< n;k++){

                                        ExplosionTriangle explosionTriangle = new ExplosionTriangle(shapeRenderer,playBall.getPosition(), k*360/n );
                                        explosionTriangles.add(explosionTriangle);


                                        for(Ring mRing: rings){
                                            mRing.stop();
                                        }
                                        stopped = true;
                                        Gdx.input.vibrate(500);

                                    }
                                }

                                else if(ballAngle1+360 >= (q1-5) && ballAngle1+360 <= (q2+5)) {
                                    // Gdx.app.log(" Collision","detected");

                                    int n = MathUtils.random(10,20);
                                    for(int k=0;k< n;k++){

                                        ExplosionTriangle explosionTriangle = new ExplosionTriangle(shapeRenderer,playBall.getPosition(), k*360/n );
                                        explosionTriangles.add(explosionTriangle);

                                    }

                                    for(Ring mRing: rings){
                                        mRing.stop();
                                    }

                                    stopped = true;
                                    Gdx.input.vibrate(500);
                                }
                            }
                        }
                    }
                }
            }
        }
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

        if(playBall!=null) {
            lastAttachedRing = playBall.detach();
            Gdx.app.log("Hi", "touchDown");
        }
        return true;
    }
}
