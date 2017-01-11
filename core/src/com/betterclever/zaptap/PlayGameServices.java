package com.betterclever.zaptap;

/**
 * Created by betterclever on 06/01/17.
 */

public interface PlayGameServices {
    void signIn();
    void signOut();
    void rateGame();
    void unlockAchievement();
    void submitScore(int highScore, int mode);
    void showAchievement();
    void showScore();
    void submitAllScores();
    void showAd();
    boolean isSignedIn();
}
