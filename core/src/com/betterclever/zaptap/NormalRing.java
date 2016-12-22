package com.betterclever.zaptap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by betterclever on 22/12/16.
 */

public class NormalRing extends Renderable {

    ShapeRenderer renderer;

    public NormalRing(ShapeRenderer renderer){
        this.renderer = renderer;
        radius = 200;
    }

    @Override
    public void render(float delta) {

        radius -= delta;

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(Color.FIREBRICK);
        renderer.circle(Constants.WORLD_WIDTH/2,Constants.WORLD_HEIGHT/2,radius,256);
        renderer.setColor(Color.BLACK);
        renderer.circle(Constants.WORLD_WIDTH/2,Constants.WORLD_HEIGHT/2,radius-5,256);

        renderer.end();

    }
}
