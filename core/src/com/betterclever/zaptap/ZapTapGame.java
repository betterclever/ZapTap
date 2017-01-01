package com.betterclever.zaptap;

import com.badlogic.gdx.Game;
import com.betterclever.zaptap.screens.PlayScreen;

public class ZapTapGame extends Game {

	PlayScreen playScreen;

	@Override
	public void create() {
		playScreen = new PlayScreen(this);
		setScreen(playScreen);
	}

	public void resetPlay(){
		playScreen.dispose();
		playScreen = new PlayScreen(this);
		setScreen(playScreen);
	}

}
