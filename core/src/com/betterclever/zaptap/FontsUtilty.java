package com.betterclever.zaptap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.betterclever.zaptap.screens.HomeScreen;

/**
 * Created by betterclever on 13/01/17.
 */

public class FontsUtilty {

    public static BitmapFont HOMESCREEN_LOGO_FONT;
    public static BitmapFont DEVELOPERS_SCREEN_FONT;
    public static BitmapFont SCORE_FONT;
    public static BitmapFont ZAPPER_FONT;
    public static BitmapFont PLAY_BUTTON_FONT;
    public static BitmapFont MODE_BUTTON_FONT;
    public static BitmapFont GO_TO_HOME_FONT;

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
        parameter.size = 30;
        DEVELOPERS_SCREEN_FONT = generator.generateFont(parameter);
        generator.dispose();

    }
}
