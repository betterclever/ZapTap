package com.betterclever.zaptap.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
    boolean paused = false;
    boolean gameOver = false;

    Color bannerColor;

    BitmapFont font;
    BitmapFont restartButtonFont;
    BitmapFont goToHomeFont;

    Texture pauseButton;

    Rectangle bounds;
    Rectangle pauseButtonBounds;
    Rectangle goToHomeBounds;

    int playMode;

    public PlayScreen(int mode, ZapTapGame zapTapGame) {
        game = zapTapGame;
        playMode = mode;
    }

    @Override
    public void show() {

        shapeRenderer = new ShapeRenderer();
        spriteBatch= new SpriteBatch();
        shapeRenderer.setAutoShapeType(true);
        extendViewport = new ExtendViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        rings = new DelayedRemovalArray<Ring>();
        explosionTriangles = new DelayedRemovalArray<ExplosionTriangle>();

        bounds = new Rectangle();
        time = 0;
        timer = 0;

        Preferences preferences = Gdx.app.getPreferences(Constants.PREF_KEY);
        int playNum = preferences.getInteger(Constants.PLAY_COUNT);
        playNum++;
        preferences.putInteger(Constants.PLAY_COUNT,playNum);

        if(playNum >= 50){
            game.getPlayGameServices().unlockAchievement();
        }

        bannerColor = new Color(0,0,0,0.8f);
        score = new Score(spriteBatch);
        playBall = null;

        pauseButton = new Texture("pause.png");

        pauseButtonBounds = new Rectangle(Constants.WORLD_WIDTH-100,Constants.WORLD_HEIGHT-100,100,100);
        goToHomeBounds = new Rectangle(Constants.WORLD_WIDTH/2-150,Constants.WORLD_HEIGHT/6-100,300,100);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("VikingHell.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.color= Color.WHITE;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        font = generator.generateFont(parameter);
        parameter.size = 50;
        parameter.color = Color.BLACK;
        restartButtonFont = generator.generateFont(parameter);
        generator.dispose();

        FreeTypeFontGenerator generator1 = new FreeTypeFontGenerator(Gdx.files.internal("Teacher_a.ttf"));
        parameter.color = Color.WHITE;
        goToHomeFont = generator1.generateFont(parameter);
        generator1.dispose();

        reset(playMode);
    }

    @Override
    public void render(float delta) {

        if(!stopped) {
            time += 2 * delta;
            timer += delta;
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
                    rings.add(new CrossMeRing(shapeRenderer,playMode));
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
            if(rings.get(i).radius < 10){
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
            if (playBall.getRotateRadius() > 300) {
                stopped = true;
                gameOver = true;
                for (Ring r : rings) {
                    r.stop();
                }
                createExplosion();
                playBall = null;
            }

            else if (playBall.getRotateRadius() < 12) {
                stopped = true;
                gameOver = true;
                for(Ring r: rings){
                    r.stop();
                }
                createExplosion();
                playBall = null;

            }
        }

        if(!stopped){
            drawPauseButton();
        }

        if(stopped){

            drawBanner();
            writeText("Go to Home",Constants.WORLD_WIDTH/2-150,Constants.WORLD_HEIGHT/6,goToHomeFont);
            if(gameOver) {
                writeText("Game Over",Constants.WORLD_WIDTH/4.5f,Constants.WORLD_HEIGHT/2 + 50,font);
                drawButtonWithText("RESTART");
            }
            else if(paused) {
                writeText("   Paused",Constants.WORLD_WIDTH/4.5f,Constants.WORLD_HEIGHT/2 + 50,font);
                drawButtonWithText("RESUME");
            }
        }

    }

    private void drawPauseButton(){
        spriteBatch.begin();
        spriteBatch.draw(pauseButton,Constants.WORLD_WIDTH-60,Constants.WORLD_HEIGHT-60,40,40);
        spriteBatch.end();
    }

    private void drawButtonWithText(String text) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(Constants.WORLD_WIDTH/2-100,Constants.WORLD_HEIGHT/3-25,200,50);
        shapeRenderer.end();

        bounds.set(Constants.WORLD_WIDTH/2-100,Constants.WORLD_HEIGHT/3-25,200,50);

        spriteBatch.begin();
        restartButtonFont.draw(spriteBatch,text,Constants.WORLD_WIDTH/2-80,Constants.WORLD_HEIGHT/3+20);
        spriteBatch.end();
    }

    private void writeText(String text, float scrX, float scrY, BitmapFont font) {
        spriteBatch.begin();
        font.draw(spriteBatch,text,scrX,scrY);
        spriteBatch.end();
    }

    private void drawBanner() {

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(bannerColor);
        shapeRenderer.rect(0,0,Constants.WORLD_WIDTH,Constants.WORLD_HEIGHT/1.5f);
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

                float r = ring.radius;

                if(playBall == null){
                    Gdx.app.log("WTF","playball null");
                    return;
                }
                float diff = r - playBall.getRotateRadius();

               if(diff<7.5f && diff > -10){
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


                        for (int j = 0; j < arcCount; j++) {

                            float q1 = 360/arcCount * j + rot;
                            float q2 = q1 + 180/arcCount  ;

                            float ballAngle1 = ballAngle;


                            if(ballAngle1 >= (q1-5) && ballAngle1 <= (q2+5) ){

                                createExplosion();

                                for(Ring mRing: rings){
                                    mRing.stop();
                                }
                                stopped = true;
                                gameOver = true;
                                playBall = null;
                            }

                            else if(ballAngle1+360 >= (q1-5) && ballAngle1+360 <= (q2+5)) {

                                createExplosion();

                                for(Ring mRing: rings){
                                    mRing.stop();
                                }

                                stopped = true;
                                gameOver = true;
                                playBall = null;
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


    public void createExplosion(){

        int n = MathUtils.random(20,30);
        for(int k=0;k< n;k++){
            ExplosionTriangle explosionTriangle = new ExplosionTriangle(shapeRenderer,playBall.getPosition(), k*360/n );
            explosionTriangles.add(explosionTriangle);
        }
        game.getPlayGameServices().submitScore(score.getScore(),playMode);
        Gdx.input.vibrate(500);

    }

    @Override
    public void pause() {
        paused =true;
        stopped = true;
        for (Ring r:rings){
            r.stop();
        }
    }

    @Override
    public void resume() {
        paused = false;
        if(gameOver!=true){
            stopped = false;
        }
        for (Ring r:rings){
            r.start();
        }
    }

    @Override
    public void hide() {

    }

    public void reset(int playMode){

        explosionTriangles.clear();
        rings.clear();
        time = 0;
        timer = 0;
        sw = 1;
        this.playMode = playMode;
        playBall = null;
        stopped = false;
        gameOver = false;
        score.reset();

    }

    public void reset(){

        explosionTriangles.clear();
        rings.clear();
        time = 0;
        timer = 0;
        sw = 1;
        playBall = null;
        stopped = false;
        gameOver = false;
        score.reset();

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        font.dispose();
        restartButtonFont.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);

        Vector2 touchPos = extendViewport.unproject(new Vector2(screenX,screenY));
        if(!paused){
            if(pauseButtonBounds.contains(touchPos)){
                pause();
                return true;
            }
        }


        if(stopped){
            if(bounds.contains(touchPos)){
                if(paused){
                    resume();
                }
                else if(gameOver) {
                    reset();
                }
            }
            else if(goToHomeBounds.contains(touchPos)){
                game.setHomeScreen();
            }
            return true;
        }


        if(playBall!=null) {
            lastAttachedRing = playBall.detach();
            Gdx.app.log("Hi", "touchDown");
        }

        return true;
    }

    public boolean keyDown (int keycode) {

        if(keycode == Input.Keys.SPACE){
            if(playBall!=null) {
                lastAttachedRing = playBall.detach();
            }
        }

        if(keycode == Input.Keys.ESCAPE){
            if(!stopped){
                pause();
            }
        }

        if(keycode == Input.Keys.BACK){
            if(!stopped) {
                pause();
            }
        }

        return false;
    }

    public void setPlayMode(int mode) {
        playMode = mode;
    }
}
