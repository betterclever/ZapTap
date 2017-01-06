package com.betterclever.zaptap.objects;

/**
 * Created by betterclever on 22/12/16.
 */

public abstract class Ring {

    public float radius;
    boolean stopped = false;

    public abstract void render(float delta);

    public float getRadius() {
        return radius;
    }

    public void stop(){
        stopped = true;
    }

    public void start(){
        stopped = false;
    }

    public boolean isStopped() {
        return stopped;
    }
}
