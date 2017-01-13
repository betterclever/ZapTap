package com.betterclever.zaptap.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.betterclever.zaptap.PlatformHelper;
import com.betterclever.zaptap.ZapTapGame;

public class DesktopLauncher {

	public static void main (String[] arg) {

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 720;
		config.width = 1280;

        PlatformHelper platformHelper = new PlatformHelper() {
            @Override
            public void signIn() {

            }

            @Override
            public void signOut() {

            }

            @Override
            public void rateGame() {

            }

            @Override
            public void unlockAchievement() {

            }

            @Override
            public void submitScore(int highScore, int mode) {

            }

            @Override
            public void showAchievement() {

            }

            @Override
            public void showScore() {

            }

            @Override
            public void submitAllScores() {

            }

            @Override
            public void showAd() {

            }

            @Override
            public void endGame() {

            }

            @Override
            public void claimTheZappers(int unclaimedZapperCount) {

            }

            @Override
            public boolean isSignedIn() {
                return false;
            }
        };

		new LwjglApplication(new ZapTapGame(platformHelper), config);
	}
}
