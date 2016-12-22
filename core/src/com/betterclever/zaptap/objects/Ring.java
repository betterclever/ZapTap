package com.betterclever.zaptap.objects;

/**
 * Created by betterclever on 22/12/16.
 */

public abstract class Ring {

    public int radius;

    public abstract void render(float delta);

    public int getRadius() {
        return radius;
    }
}
