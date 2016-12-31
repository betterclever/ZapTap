package com.betterclever.zaptap;

import com.badlogic.gdx.Game;
import com.betterclever.zaptap.screens.PlayScreen;

public class ZapTapGame extends Game {


	@Override
	public void create() {
		setScreen(new PlayScreen(this));
	}

}
