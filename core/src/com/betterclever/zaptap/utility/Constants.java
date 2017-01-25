package com.betterclever.zaptap.utility;

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
    public static final Texture COPYRIGHT_IMAGE = new Texture("copyright.png");
    public static final Texture FACEBOOK_IMAGE = new Texture("facebook.png");
    public static final Texture GOOGLEPLUS_IMAGE = new Texture("google-plus.png");
    public static final Texture GITHUB_IMAGE = new Texture("github-logo.png");
    public static final Texture CLOSE_IMAGE = new Texture("cancel.png");
    public static final Texture INFORMATION_IMAGE = new Texture("information.png");
}
