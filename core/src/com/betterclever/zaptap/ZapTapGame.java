package com.betterclever.zaptap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.betterclever.zaptap.screens.HomeScreen;
import com.betterclever.zaptap.screens.PlayScreen;

public class ZapTapGame extends Game {

	PlayScreen playScreen;
	Music music ;

	PlatformHelper platformHelper;
	HomeScreen homeScreen;

	public ZapTapGame(PlatformHelper platformHelper) {
		this.platformHelper = platformHelper;
	}

	public ZapTapGame(){
        platformHelper = null;
    }

	@Override
	public void create() {
		music = com.betterclever.zaptap.utility.Constants.MUSIC;
		music.setLooping(true);
		music.setVolume(1f);
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

	public PlatformHelper getPlatformHelper() {
		return platformHelper;
	}

    public void exit() {
        platformHelper.endGame();
    }
}
