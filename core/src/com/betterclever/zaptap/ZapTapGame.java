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

	public ZapTapGame(){
        playGameServices = null;
    }

	@Override
	public void create() {
		music = Constants.MUSIC;
		music.setLooping(true);
		music.setVolume(0.5f);
		homeScreen = new HomeScreen(this);
		setScreen(homeScreen);
		//setScreen(playScreen);
	}

	public void startPlay(int mode){
		if(playScreen == null) {
			playScreen = new PlayScreen(mode, this);
		}
		playScreen.setPlayMode(mode);
		setScreen(playScreen);
	}

	public void setHomeScreen(){
		setScreen(homeScreen);
	}

	public PlayGameServices getPlayGameServices() {
		return playGameServices;
	}

    public void exit() {
        playGameServices.endGame();
    }
}
