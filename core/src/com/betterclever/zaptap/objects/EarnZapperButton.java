package com.betterclever.zaptap.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.betterclever.zaptap.utility.Constants;

/**
 * Created by betterclever on 11/01/17.
 */

public class EarnZapperButton implements RenderableObject {

    private SpriteBatch spriteBatch;
    private ShapeRenderer renderer;
    private BitmapFont bitmapFont;
    private Texture getImage;
    private Vector2 position;
    private Rectangle bounds;

    public EarnZapperButton(SpriteBatch spriteBatch, ShapeRenderer renderer) {
        this.spriteBatch = spriteBatch;
        this.renderer = renderer;

        position = new Vector2(Constants.WORLD_WIDTH-250, Constants.WORLD_HEIGHT-70);
        bounds = new Rectangle(Constants.WORLD_WIDTH-250,Constants.WORLD_HEIGHT-70,250,70);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Quantify-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color= Color.WHITE;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        bitmapFont = generator.generateFont(parameter);
        getImage = new Texture("get-money.png");
    }

    @Override
    public void render(float delta) {

        spriteBatch.begin();
        spriteBatch.draw(getImage,position.x,position.y,50,50);
        bitmapFont.draw(spriteBatch,"Earn Free",position.x+80,position.y+50);
        bitmapFont.draw(spriteBatch,"Zappers",position.x+80,position.y+20);
        spriteBatch.end();
    }

    public boolean isTouched(Vector2 position){
        if(bounds.contains(position)){
            Gdx.app.log("Tapped","earnzapper button");
            return true;
        }
        return false;
    }
}
