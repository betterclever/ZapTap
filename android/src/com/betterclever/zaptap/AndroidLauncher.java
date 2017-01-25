package com.betterclever.zaptap;

import android.content.Intent;
import android.os.Bundle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.betterclever.zaptap.utility.Constants;
import com.betterclever.zaptap.utility.Encrypt;

public class AndroidLauncher extends AndroidApplication implements PlatformHelper {
	
	private static final String TAG = AndroidLauncher.class.getSimpleName();
	private final static int requestCode = 1;
	Preferences preferences;
	
	int unclaimedZapperCount = 15;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useImmersiveMode = true;
		initialize(new ZapTapGame(this), config);
		
		preferences = Gdx.app.getPreferences(com.betterclever.zaptap.utility.Constants.PREF_KEY);
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
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
		if(highScore >= 15){
			switch (mode) {
				case Constants.EASY_MODE :
					preferences.putBoolean(Constants.MEDIUM_LOCKED,false).flush();
					break;
				case Constants.MEDIUM_MODE:
					preferences.putBoolean(Constants.INSANE_LOCKED,false).flush();
					break;
				case Constants.HARD_MODE:
					preferences.putBoolean(Constants.INSANE_LOCKED,false).flush();
					break;
			}
		}
	}
	
	
	private void checkForAchievements(int score, final int mode) {
	}
	
	@Override
	public void showAd() {
	}
	
	@Override
	public void endGame() {
		finishAffinity();
		System.exit(0);
	}
	
	@Override
	public void claimTheZappers(int unclaimedZapperCount) {
		this.unclaimedZapperCount = unclaimedZapperCount;
		
		int zappercount = Encrypt.decrypt(preferences.getString(Constants.ZAPPER_COUNT)) + unclaimedZapperCount;
		preferences.putString(Constants.ZAPPER_COUNT, Encrypt.encrypt(zappercount)).flush();
		
		this.unclaimedZapperCount = 0;
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
	public boolean isSignedIn() {
		return false;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
