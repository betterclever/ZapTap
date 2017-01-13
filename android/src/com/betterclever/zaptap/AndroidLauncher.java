package com.betterclever.zaptap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.games.leaderboard.Leaderboards;
import com.google.example.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements PlatformHelper {

    private static final String TAG = AndroidLauncher.class.getSimpleName();
    private GameHelper gameHelper;
    private final static int requestCode = 1;
    Preferences preferences;

    private RewardedVideoAd mRewardedVideoAd;
    private InterstitialAd mInterstitialAd;

    private static String AD_UNIT_ID;
    private static String APP_ID;

    int unclaimedZapperCount = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AD_UNIT_ID = getString(R.string.interstitial_adunit_id);
        APP_ID = getString(R.string.app_id);

        initializeInterstitialAd();

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
        initialize(new ZapTapGame(this), config);

        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.enableDebugLog(false);

        preferences = Gdx.app.getPreferences(com.betterclever.zaptap.utility.Constants.PREF_KEY);

        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
            @Override
            public void onSignInFailed() {
            }

            @Override
            public void onSignInSucceeded() {
                storePlayerData();
            }


        };

        gameHelper.setup(gameHelperListener);
        submitAllScores();

    }

    private void initializeInterstitialAd() {

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                int count = com.betterclever.zaptap.utility.Encrypt.decrypt(preferences.getString(com.betterclever.zaptap.utility.Constants.ZAPPER_COUNT));
                count+=unclaimedZapperCount*2;
                preferences.putString(com.betterclever.zaptap.utility.Constants.ZAPPER_COUNT, com.betterclever.zaptap.utility.Encrypt.encrypt(count)).flush();
                requestNewInterstitial();
                unclaimedZapperCount = 15;
            }
        });

        requestNewInterstitial();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("4B854EFE4E7BA74EDD3A7742E9F2191D")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void showInterstitialAd(){
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mInterstitialAd.isLoaded()){
                        mInterstitialAd.show();
                    }
                    else {
                        int count = com.betterclever.zaptap.utility.Encrypt.decrypt(preferences.getString(com.betterclever.zaptap.utility.Constants.ZAPPER_COUNT));
                        count+=(unclaimedZapperCount);
                        preferences.putString(com.betterclever.zaptap.utility.Constants.ZAPPER_COUNT, com.betterclever.zaptap.utility.Encrypt.encrypt(count)).flush();
                        unclaimedZapperCount = 15;
                        requestNewInterstitial();
                    }
                }
            });
        }
        catch (Exception e){
            Log.i(TAG,"Error ad me "+e.getMessage());
        }

    }

    private void storePlayerData() {

        storeUserId();
        submitAllScores();

        for (int i = 0; i < 4; i++) {
            final int mode = i;
            Games.Leaderboards.loadCurrentPlayerLeaderboardScore(gameHelper.getApiClient(),
                    getStringByMode(mode),
                    LeaderboardVariant.TIME_SPAN_ALL_TIME,
                    LeaderboardVariant.COLLECTION_PUBLIC).
                    setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {

                        @Override
                        public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
                            // if (isScoreResultValid(scoreResult)) {
                            LeaderboardScore c = scoreResult.getScore();
                            if(c!=null) {
                                int modef = mode;
                                Gdx.app.log("score + mode", c.getRawScore() + "  " + modef);
                                if (c.getRawScore() > preferences.getInteger(getStringByMode(modef))) {
                                    preferences.putInteger(getStringByMode(modef), (int) c.getRawScore()).flush();
                                }
                                unlockByCurrentScore((int) c.getRawScore(), modef);
                            }
                        }
                    });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gameHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void signIn() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void signOut() {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.signOut();
                }
            });
        } catch (Exception e) {
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
        }
    }

    @Override
    public void rateGame() {
        String str = "Your PlayStore Link";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    }

    @Override
    public void unlockAchievement() {
        int playCount = preferences.getInteger(com.betterclever.zaptap.utility.Constants.PLAY_COUNT);
        if (playCount >= 50) {
            Games.Achievements.unlock(gameHelper.getApiClient(),
                    getString(R.string.achievement_addicted));
        } else if (playCount >= 200) {
            Games.Achievements.unlock(gameHelper.getApiClient(),
                    getString(R.string.achievement_boredom_killer));
        }
    }

    @Override
    public void submitScore(int highScore, int mode) {

        if (isSignedIn()) {
            String storedPlayerID = preferences.getString("playerid", "");
            String currentPlayerID = Games.Players.getCurrentPlayer(gameHelper.getApiClient()).getPlayerId();

            String modeID = getStringByMode(mode);

            if (storedPlayerID.equals("") || storedPlayerID.equals(currentPlayerID)) {
                if (highScore > preferences.getInteger(modeID)) {
                    preferences.putInteger(modeID, highScore).flush();
                    submitAllScores();
                }
                Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                        modeID, highScore);

            } else {
                resetScores();
                if (highScore > preferences.getInteger(modeID)) {
                    preferences.putInteger(modeID, highScore).flush();
                    submitAllScores();
                    Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                            modeID, highScore);
                }
            }

            checkForAchievements(highScore, mode);

            storeUserId();
            Gdx.app.log(TAG, String.valueOf(highScore));
        }
    }

    private void unlockByCurrentScore(int score, int mode) {

        if(com.betterclever.zaptap.utility.Encrypt.decrypt(preferences.getString(com.betterclever.zaptap.utility.Constants.ZAPPER_COUNT)) >= 2000){
            Games.Achievements.unlock(gameHelper.getApiClient(),
                    getString(R.string.achievement_zapper_collector));
        }

        if (mode == com.betterclever.zaptap.utility.Constants.EASY_MODE) {
            if (score >= 50) {
                preferences.putBoolean(com.betterclever.zaptap.utility.Constants.MEDIUM_LOCKED,false).flush();
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_unlock_medium_mode));
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_zap_ninja_level_1));
            }
            if (score >= 100) {
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_zap_blaster_level_1));
            }
            if (score >= 200) {
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_unstoppable_zapper_level_1));
            }
        } else if (mode == com.betterclever.zaptap.utility.Constants.MEDIUM_MODE) {
            if (score >= 50) {
                preferences.putBoolean(com.betterclever.zaptap.utility.Constants.HARD_LOCKED,false).flush();
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_unlock_hard_mode));
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_zap_ninja_level_2));
            }
            if (score >= 100) {
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_zap_blaster_level_2));
            }
            if (score >= 200) {
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_unstoppable_zapper_level_2));
            }
        } else if (mode == com.betterclever.zaptap.utility.Constants.HARD_MODE) {
            if (score >= 50) {
                preferences.putBoolean(com.betterclever.zaptap.utility.Constants.INSANE_LOCKED,false).flush();
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_unlock_insane_mode));
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_zap_ninja_level_3));
            }
            if (score >= 100) {
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_zap_blaster_level_3));
            }
            if (score >= 200) {
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_unstoppable_zapper_level_3));
            }
        } else if (mode == com.betterclever.zaptap.utility.Constants.INSANE_MODE) {
            if (score >= 50) {
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_ultimate_zap_ninja));
            }
            if (score >= 100) {
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_ultimate_blaster));
            }
            if (score >= 200) {
                Games.Achievements.unlock(gameHelper.getApiClient(),
                        getString(R.string.achievement_insane_zapper));
            }
        }
    }

    private void checkForAchievements(int score, final int mode) {


        Games.Leaderboards.loadCurrentPlayerLeaderboardScore(gameHelper.getApiClient(),
                getStringByMode(mode),
                LeaderboardVariant.TIME_SPAN_ALL_TIME,
                LeaderboardVariant.COLLECTION_PUBLIC).
                setResultCallback(new ResultCallback<Leaderboards.LoadPlayerScoreResult>() {

                    @Override
                    public void onResult(final Leaderboards.LoadPlayerScoreResult scoreResult) {
                        // if (isScoreResultValid(scoreResult)) {
                        LeaderboardScore c = scoreResult.getScore();
                        if(c!=null) {
                            Gdx.app.log("score + mode", c.getRawScore() + "  " + mode);
                            if (c.getRawScore() > preferences.getInteger(getStringByMode(mode))) {
                                preferences.putInteger(getStringByMode(mode), (int) c.getRawScore()).flush();
                            }
                            unlockByCurrentScore((int) c.getRawScore(), mode);
                        }
                    }
                });

        unlockByCurrentScore(score, mode);

    }

    private void resetScores() {
        if (isSignedIn()) {
            for (int i = 0; i < 4; i++) {
                String modeId = getStringByMode(i);
                preferences.putInteger(modeId, 0).flush();
            }

        }
    }

    private void storeUserId() {
        String playerID = Games.Players.getCurrentPlayer(gameHelper.getApiClient()).getPlayerId();
        if(! preferences.getString("playerid").equals(playerID)) {
            preferences.putString("playerid", playerID).flush();
            resetScores();
        }
    }

    public void submitAllScores() {
        if (isSignedIn() == true) {
            for (int i = 0; i < 4; i++) {
                String modeId = getStringByMode(i);
                int highScore = preferences.getInteger(modeId);
                Gdx.app.log(modeId, String.valueOf(highScore));
                Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                        modeId, highScore);
            }

        }
    }

    @Override
    public void showAd() {
        showInterstitialAd();
    }

    @Override
    public void endGame() {
        finishAffinity();
        System.exit(0);
    }

    @Override
    public void claimTheZappers(int unclaimedZapperCount) {
        this.unclaimedZapperCount = unclaimedZapperCount;
        showInterstitialAd();
    }


    private String getStringByMode(int mode) {
        switch (mode) {
            case com.betterclever.zaptap.utility.Constants.EASY_MODE:
                return getString(R.string.leaderboard_easy_highscore);
            case com.betterclever.zaptap.utility.Constants.MEDIUM_MODE:
                return getString(R.string.leaderboard_medium_highscore);
            case com.betterclever.zaptap.utility.Constants.HARD_MODE:
                return getString(R.string.leaderboard_hard_highscore);
            case com.betterclever.zaptap.utility.Constants.INSANE_MODE:
                return getString(R.string.leaderboard_insane_highscore);
            default:
                return getString(R.string.leaderboard_easy_highscore);
        }
    }

    @Override
    public void showAchievement() {
        if (isSignedIn() == true) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
        } else {
            signIn();
        }
    }

    @Override
    public void showScore() {
        if (isSignedIn() == true) {
            startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(gameHelper.getApiClient()), requestCode);
        } else {
            signIn();
        }
    }

    @Override
    public boolean isSignedIn() {
        return gameHelper.isSignedIn();
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
