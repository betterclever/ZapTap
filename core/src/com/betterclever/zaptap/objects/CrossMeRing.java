package com.betterclever.zaptap.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.betterclever.zaptap.Constants;
import com.betterclever.zaptap.objects.Ring;

/**
 * Created by betterclever on 22/12/16.
 */

public class CrossMeRing extends Ring {

    ShapeRenderer renderer;
    int arcNum;
    float rot = 0;

    public CrossMeRing(ShapeRenderer renderer, int arcNum){

        this.renderer = renderer;
        this.arcNum = arcNum;

        radius = 200;
    }

    public void render(float delta){

        radius -= delta;
        rot += (10*delta);
        rot %= 360;

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(Color.WHITE);
        for (int i = 0; i < arcNum ; i++) {
            renderer.arc(Constants.WORLD_WIDTH/2,Constants.WORLD_HEIGHT/2,radius, ( (180/arcNum * 2* i) + rot ),180/arcNum,256);
        }

        renderer.setColor(Color.BLACK);
        renderer.circle(Constants.WORLD_WIDTH/2,Constants.WORLD_HEIGHT/2,radius-5,256);

        renderer.end();

    }

    public int getArcNum() {
        return arcNum;
    }

    public float getRot() {
        return rot;
    }
}
