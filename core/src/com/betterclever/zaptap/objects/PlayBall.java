package com.betterclever.zaptap.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
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
    int time;

    public PlayBall(ShapeRenderer renderer, NormalRing attachedRing) {
        radius = 15;
        this.renderer = renderer;
        this.attachedRing = attachedRing;
        bound = new Circle();
        angle = 0;
    }

    public void render(float delta) {

        if (attachedRing == null) {
            renderNotAttached(delta);
            return;
        }

        angle += 100*delta;
        angle %= 360;
        rotateRadius = attachedRing.getRadius();

       // Gdx.app.log("arr", String.valueOf(rotateRadius));

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(Color.CYAN);
        renderer.circle(Constants.WORLD_WIDTH / 2 + rotateRadius * MathUtils.sinDeg(angle) - 2.5f, Constants.WORLD_HEIGHT / 2 + rotateRadius * MathUtils.cosDeg(angle) - 2.5f, radius, 256);

        bound.set(Constants.WORLD_WIDTH / 2 + rotateRadius * MathUtils.sinDeg(angle) - 2.5f, Constants.WORLD_HEIGHT / 2 + rotateRadius * MathUtils.cosDeg(angle) - 2.5f, radius);

        renderer.end();

    }

    private void renderNotAttached(float delta) {

        //Gdx.app.log("rr", String.valueOf(rotateRadius));

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.CORAL);
        renderer.circle(Constants.WORLD_WIDTH / 2 + rotateRadius * MathUtils.sinDeg(angle) - 2.5f, Constants.WORLD_HEIGHT / 2 + rotateRadius * MathUtils.cosDeg(angle) - 2.5f, radius, 256);

        bound.set(Constants.WORLD_WIDTH / 2 + rotateRadius * MathUtils.sinDeg(angle) - 2.5f, Constants.WORLD_HEIGHT / 2 + rotateRadius * MathUtils.cosDeg(angle) - 2.5f, radius);

        rotateRadius += 300 * delta;
        renderer.end();

    }

    public Ring detach() {
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
        this.attachedRing = attachedRing;
    }

    public float getAngle() {
        return angle;
    }
}
