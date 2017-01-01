package com.betterclever.zaptap.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
import com.betterclever.zaptap.objects.Score;

/**
 * Created by betterclever on 22/12/16.
 */

public class PlayScreen extends InputAdapter implements Screen {

    ExtendViewport extendViewport;
    ShapeRenderer shapeRenderer;
    SpriteBatch spriteBatch;

    int sw = 1;
    float time;

    float timer;

    int radius;

    DelayedRemovalArray<Ring> rings;
    DelayedRemovalArray<ExplosionTriangle> explosionTriangles;

    PlayBall playBall;

    Ring lastAttachedRing;
    ZapTapGame game;
    Score score;

    boolean stopped = false;
    Color bannerColor;

    BitmapFont font;
    BitmapFont restartButtonFont;

    Rectangle bounds;

    public PlayScreen(ZapTapGame zapTapGame) {
        game = zapTapGame;
    }

    @Override
    public void show() {

        shapeRenderer = new ShapeRenderer();
        spriteBatch= new SpriteBatch();
        shapeRenderer.setAutoShapeType(true);
        extendViewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        Gdx.input.setInputProcessor(this);
        rings = new DelayedRemovalArray<Ring>();
        explosionTriangles = new DelayedRemovalArray<ExplosionTriangle>();

        bounds = new Rectangle();
        time = 0;
        timer = 0;

        bannerColor = new Color(0,0,0,0.8f);
        score = new Score(spriteBatch);
        playBall = null;
        radius = 200;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("VikingHell.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.color= Color.WHITE;
        font = generator.generateFont(parameter);
        parameter.size = 50;
        parameter.color = Color.BLACK;
        restartButtonFont = generator.generateFont(parameter);
        generator.dispose();


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
        spriteBatch.setProjectionMatrix(extendViewport.getCamera().combined);

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

        if(!stopped) {
            handleBall(delta);
        }

        score.render(delta);

        if (playBall!=null) {
            if (playBall.getRotateRadius() > 400) {
                stopped = true;
                for (Ring r : rings) {
                    r.stop();
                }
                playBall = null;
            }
        }

        if(stopped){
            drawBanner();
            writeGameOver();
            drawRestartButton();
        }

    }

    private void drawRestartButton() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(Constants.WORLD_WIDTH/2-100,Constants.WORLD_HEIGHT/3-25,200,50);
        shapeRenderer.end();

        bounds.set(Constants.WORLD_WIDTH/2-100,Constants.WORLD_HEIGHT/3-25,200,50);

        spriteBatch.begin();
        restartButtonFont.draw(spriteBatch,"RESTART",Constants.WORLD_WIDTH/2-80,Constants.WORLD_HEIGHT/3+20);
        spriteBatch.end();
    }

    private void writeGameOver() {
        spriteBatch.begin();
        font.draw(spriteBatch,"Game Over",Constants.WORLD_WIDTH/4.5f,Constants.WORLD_HEIGHT/2 + 50);
        spriteBatch.end();
    }

    private void drawBanner() {

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(bannerColor);
        shapeRenderer.rect(0,Constants.WORLD_HEIGHT/4,Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT/2.3f);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

    }

    private void handleBall(float delta) {

        if(playBall == null){
            Gdx.app.log("ball","null");
            return;
        }

        playBall.render(delta);

        if(playBall.isDetached()){

            for (int i = rings.size -1 ; i >= 0; i--) {

                Ring ring = rings.get(i);

                if(ring.equals(lastAttachedRing)){
                    continue;
                }

                int r = ring.radius;

                //Gdx.app.log("radius", String.valueOf(r));
                if(playBall == null){
                    Gdx.app.log("WTF","playball null");
                    return;
                }
                float diff = r - playBall.getRotateRadius();

                //Gdx.app.log("diff", String.valueOf(diff));
                if(diff<7.5f && diff > 0){
                    if(ring.getClass().equals(NormalRing.class)){
                        playBall.setAttachedRing((NormalRing) ring);
                        score.increase();
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

                                }

                                for(Ring mRing: rings){
                                    mRing.stop();
                                }
                                stopped = true;
                                playBall = null;
                                Gdx.input.vibrate(500);
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
                                playBall = null;
                                Gdx.input.vibrate(500);
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

        if(stopped){
            Vector2 point = new Vector2(screenX,screenY);
            point =  extendViewport.unproject(point);
            if(bounds.contains(point)){
                game.resetPlay();
            }
        }

        return true;
    }
}
