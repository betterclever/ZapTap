package com.betterclever.zaptap.utility;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by betterclever on 13/01/17.
 */

public class FontsUtilty {

    public static BitmapFont HOMESCREEN_LOGO_FONT;
    public static BitmapFont DEVELOPERS_SCREEN_FONT;
    public static BitmapFont GAMEOVER_FONT;
    public static BitmapFont SCORE_FONT;
    public static BitmapFont ZAPPER_FONT;
    public static BitmapFont PENDING_ZAPPER_FONT;
    public static BitmapFont PLAY_BUTTON_FONT;
    public static BitmapFont MODE_BUTTON_FONT;
    public static BitmapFont GO_TO_HOME_FONT;
    public static BitmapFont RESUME_BUTTON_FONT;
    public static BitmapFont COPYRIGHT_FONT;

    static {
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Teacher_a.ttf"));
        parameter.size = 100;
        parameter.color= Color.WHITE;
        HOMESCREEN_LOGO_FONT = generator.generateFont(parameter);
        generator.dispose();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("Quantify-Bold.ttf"));
        parameter.size = 50;
        DEVELOPERS_SCREEN_FONT = generator.generateFont(parameter);
        parameter.size = 30;
        PLAY_BUTTON_FONT = generator.generateFont(parameter);
        parameter.size = 30;
        parameter.color = Color.BLACK;
        RESUME_BUTTON_FONT = generator.generateFont(parameter);
        parameter.size = 20;
        parameter.color = Color.WHITE;
        MODE_BUTTON_FONT = generator.generateFont(parameter);
        parameter.size = 15;
        COPYRIGHT_FONT = generator.generateFont(parameter);
        parameter.size = 30;
        parameter.color = new Color(0.9f,0.9f,0.9f,1);
        GO_TO_HOME_FONT = generator.generateFont(parameter);
        generator.dispose();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("Track.ttf"));
        parameter.size = 100;
        parameter.color = Color.WHITE;
        SCORE_FONT = generator.generateFont(parameter);
        parameter.size = 45;
        ZAPPER_FONT = generator.generateFont(parameter);
        parameter.size = 30;
        PENDING_ZAPPER_FONT = generator.generateFont(parameter);
        generator.dispose();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("VikingHell.ttf"));
        parameter.size = 100;
        parameter.color= Color.WHITE;
        GAMEOVER_FONT = generator.generateFont(parameter);
        generator.dispose();
    }
}
