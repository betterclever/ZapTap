package com.betterclever.zaptap.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.betterclever.zaptap.Constants;
import com.betterclever.zaptap.objects.Ring;

/**
 * Created by betterclever on 22/12/16.
 */

public class NormalRing extends Ring {

    ShapeRenderer renderer;
    Color color;

    public NormalRing(ShapeRenderer renderer){
        this.renderer = renderer;
        radius = 200;
        color = new Color(MathUtils.random(0.3f,0.8f),MathUtils.random(0.3f,0.8f),MathUtils.random(0.3f,0.8f),1);
    }

    @Override
    public void render(float delta) {

        if(!stopped) {
            radius -= 60*delta;
        }

        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(color);
        renderer.circle(Constants.WORLD_WIDTH/2,Constants.WORLD_HEIGHT/2,radius,256);
        renderer.setColor(Color.BLACK);
        renderer.circle(Constants.WORLD_WIDTH/2,Constants.WORLD_HEIGHT/2,radius-5,256);

        renderer.end();

    }


}
