package com.betterclever.zaptap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.betterclever.zaptap.screens.HomeScreen;
import com.betterclever.zaptap.screens.PlayScreen;

public class ZapTapGame extends Game {

	PlayScreen playScreen;
	Music music ;

	PlayGameServices playGameServices;
	HomeScreen homeScreen;

	public ZapTapGame(PlayGameServices playGameServices) {
		this.playGameServices = playGameServices;
	}

	@Override
	public void create() {
		music = Gdx.audio.newMusic(Gdx.files.internal("background-skyline.mp3"));
		music.play();
		music.setLooping(true);
		music.setVolume(1f);
		homeScreen = new HomeScreen(this);
		setScreen(homeScreen);
		//setScreen(playScreen);
	}

	public void startPlay(int mode){
		playScreen = new PlayScreen(mode,this);
		setScreen(playScreen);
	}

	public PlayGameServices getPlayGameServices() {
		return playGameServices;
	}
}
