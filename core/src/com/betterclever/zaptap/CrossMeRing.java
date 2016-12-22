package com.betterclever.zaptap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by betterclever on 22/12/16.
 */

public class CrossMeRing extends Renderable {

    ShapeRenderer renderer;
    int arcNum;
    int rot = 0;

    public CrossMeRing(ShapeRenderer renderer, int arcNum){

        this.renderer = renderer;
        this.arcNum = arcNum;

        radius = 200;
    }

    public void render(float delta){

        radius -= delta;
        rot += 100*delta;

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(Color.WHITE);
        for (int i = 0; i < arcNum ; i++) {
            renderer.arc(Constants.WORLD_WIDTH/2,Constants.WORLD_HEIGHT/2,radius, ( (180/arcNum * 2* i) + rot ),180/arcNum,256);
        }

        renderer.setColor(Color.BLACK);
        renderer.circle(Constants.WORLD_WIDTH/2,Constants.WORLD_HEIGHT/2,radius-5,256);

        renderer.end();

    }

}
