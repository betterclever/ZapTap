package com.betterclever.zaptap.objects;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.betterclever.zaptap.utility.Constants;

/**
 * Created by betterclever on 13/01/17.
 */

public class SoundOnOffButton implements RenderableObject {

    Texture soundOnImage;
    Texture soundOffImage;
    Preferences preferences;
    SpriteBatch batch;
    boolean soundOn;
    Rectangle bounds;

    public SoundOnOffButton(SpriteBatch spriteBatch, Preferences preferences){
        bounds = new Rectangle(Constants.WORLD_WIDTH-70,Constants.WORLD_HEIGHT-70,70,70);
        batch = spriteBatch;

        this.preferences = preferences;
        soundOffImage = new Texture("silent.png");
        soundOnImage = new Texture("speaker.png");

        soundOn = preferences.getBoolean(Constants.SOUND_ON,true);
        preferences.putBoolean(Constants.SOUND_ON,soundOn).flush();
        initSound();
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw((soundOn) ? soundOnImage : soundOffImage,
                bounds.x,bounds.y,bounds.width-20,bounds.height-20);
        batch.end();
    }

    public void toggle(){
        soundOn = !soundOn;
        initSound();
        preferences.putBoolean(Constants.SOUND_ON,soundOn).flush();
    }

    private void initSound(){
        if (soundOn){
            Constants.MUSIC.play();
        }
        else {
            Constants.MUSIC.pause();
        }
    }


    public boolean isTouched(Vector2 position){
        if(bounds.contains(position)){
            return true;
        }
        return false;
    }
}
