package com.denip.arcon;

public interface IGoogleServices
{
public void signIn();
public void signOut();
public void rateGame();
public void submitScore(long score);
public void showScores();
public boolean isSignedIn();
public void get_ach();
public void unlockAchievementGPGS(String achievementId);
public void submitSpeedScore(long speed);
void showScore();

}