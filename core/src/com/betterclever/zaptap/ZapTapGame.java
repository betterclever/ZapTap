package com.betterclever.zaptap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.betterclever.zaptap.screens.HomeScreen;
import com.betterclever.zaptap.screens.PlayScreen;

public class ZapTapGame extends Game {

	PlayScreen playScreen;
	Music music ;

	@Override
	public void create() {
		music = Gdx.audio.newMusic(Gdx.files.internal("background-skyline.mp3"));
		music.play();
		music.setLooping(true);
		music.setVolume(1f);
		//playScreen = new PlayScreen(this);
		setScreen(new HomeScreen(this));
	}

	public void resetPlay(){
		playScreen.dispose();
		playScreen = new PlayScreen(this);
		setScreen(playScreen);
	}

}
