package com.betterclever.zaptap.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.betterclever.zaptap.Constants;

/**
 * Created by betterclever on 22/12/16.
 */

public class PlayBall {

    int radius;
    ShapeRenderer renderer;
    NormalRing attachedRing;
    public Circle bound;
    int rotateRadius = 0;
    float angle;
    float time;
    Vector2 position;
    Color color;
    Color destColor;
    ColorAction colorAction;

    public PlayBall(ShapeRenderer renderer, NormalRing attachedRing) {
        radius = 15;
        this.renderer = renderer;
        this.attachedRing = attachedRing;
        bound = new Circle();
        angle = 0;
        position = new Vector2();
        color = Color.TEAL;
        destColor = Color.RED;
        colorAction = new ColorAction();
        colorAction.setColor(color);
        colorAction.setDuration(2f);
        colorAction.setEndColor(destColor);
    }

    public void render(float delta) {

        if (attachedRing == null) {
            renderNotAttached(delta);
            return;
        }

        colorAction.act(delta);

        time += delta;

        if(time > 2){
            time -= 2;
            destColor = destColor.set(MathUtils.random(0.3f,0.8f),MathUtils.random(0.3f,0.8f),MathUtils.random(0.3f,0.8f),1);
            colorAction.reset();
            colorAction.setColor(color);
            colorAction.setEndColor(destColor);
            Gdx.app.log("destcolor", String.valueOf(destColor));
        }

        angle -= 50*delta;
        angle %= 360;
        if(angle<0){
            angle += 360;
        }
        rotateRadius = attachedRing.getRadius();

        position.x = Constants.WORLD_WIDTH / 2 + rotateRadius * MathUtils.cosDeg(angle) - 2.5f;
        position.y = Constants.WORLD_HEIGHT / 2 + rotateRadius * MathUtils.sinDeg(angle) - 2.5f;

       // Gdx.app.log("arr", String.valueOf(rotateRadius));

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(color);
        renderer.circle( position.x, position.y, radius, 256);

        bound.set(position.x, position.y, radius);


        //Gdx.app.log("angle of ball", String.valueOf(angle));
        renderer.end();

    }

    private void renderNotAttached(float delta) {

        //Gdx.app.log("rr", String.valueOf(rotateRadius));

        position.x = Constants.WORLD_WIDTH / 2 + rotateRadius * MathUtils.cosDeg(angle) - 2.5f;
        position.y = Constants.WORLD_HEIGHT / 2 + rotateRadius * MathUtils.sinDeg(angle) - 2.5f;

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.CORAL);
        renderer.circle(position.x, position.y, radius, 256);

        bound.set(position.x, position.y, radius);

        rotateRadius += 300 * delta;
        renderer.end();

    }

    public Ring detach() {
        Gdx.app.log("ball","detached");
        Ring r = attachedRing;
        attachedRing = null;
        return r;
    }

    public boolean isDetached() {
        return attachedRing == null;
    }

    public int getRotateRadius() {
        return rotateRadius;
    }

    public void setAttachedRing(NormalRing attachedRing) {
        Gdx.app.log("ball","attached");
        this.attachedRing = attachedRing;
    }

    public float getAngle() {
        return angle;
    }

    public Vector2 getPosition() {
        return position;
    }
}
