package com.betterclever.zaptap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by betterclever on 22/12/16.
 */

public class Constants {

    public static final int WORLD_WIDTH = 800;
    public static final int WORLD_HEIGHT = 450;
    public static final int EASY_MODE = 0;
    public static final int MEDIUM_MODE = 1;
    public static final int HARD_MODE = 2;
    public static final int INSANE_MODE = 3;
    public static final String PREF_KEY = "ZapTapPrefs";
    public static final String PLAY_COUNT = "playcount";
    public static final String ZAPPER_COUNT = "zappercount";
    public static final String MEDIUM_LOCKED = "medium_lock";
    public static final String HARD_LOCKED = "hard_lock";
    public static final String INSANE_LOCKED = "insane_lock";
    public static final String SOUND_ON = "sound_on";
    public static final Music MUSIC = Gdx.audio.newMusic(Gdx.files.internal("background-skyline.mp3"));

}
