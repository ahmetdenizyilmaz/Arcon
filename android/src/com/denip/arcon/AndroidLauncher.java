package com.denip.arcon;

import android.annotation.TargetApi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.denip.arcon.GameHelper.GameHelperListener;
import com.denip.arcon.Pow;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.games.Games;
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AndroidLauncher extends AndroidApplication implements AdsController, IGoogleServices
{
	private static final String BANNER_AD_UNIT_ID = "ca-app-pub-7131001808799355/1009535900";
	private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-7131001808799355/7128291509";
	InterstitialAd interstitialAd;
	
	AdView bannerAd;
	private GameHelper _gameHelper;
	
	private final static int REQUEST_CODE_UNUSED = 1234;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
	
		_gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		_gameHelper.enableDebugLog(false);
		GameHelperListener gameHelperListener = new GameHelper.GameHelperListener()
		{
			@Override
			public void onSignInSucceeded()
			{
			}

			@Override
			public void onSignInFailed()
			{
			}
		};

		_gameHelper.setup(gameHelperListener);
		
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		setupAds();
		
		View decorView = getWindow().getDecorView();

		decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		View gameView = initializeForView(new Pow(this, this), config);

		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		layout.addView(bannerAd, params);

		
		Thread.setDefaultUncaughtExceptionHandler(new RetiExceptionHandler(
                Environment.getExternalStorageDirectory().getAbsolutePath()));
		//Thread t = new Thread();
		//new RetiExceptionHandler("")
		System.out.println(Environment.getExternalStorageDirectory().getAbsolutePath());
		setContentView(layout);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		_gameHelper.onStart(this);
		
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		_gameHelper.onStop();
	//	FirebaseCrash.report(new Exception("My first Android non-fatal error"));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		_gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	public void setupAds()
	{
		bannerAd = new AdView(this);
		bannerAd.setVisibility(View.INVISIBLE);
		bannerAd.setBackgroundColor(0xff000000); // black
		bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
		bannerAd.setAdSize(AdSize.SMART_BANNER);
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(INTERSTITIAL_AD_UNIT_ID);

		AdRequest.Builder builder = new AdRequest.Builder().addTestDevice("BAA254E1D59E02763BB1A917CF86CDDE");
		AdRequest ad = builder.build();
		interstitialAd.loadAd(ad);
	}

	@Override
	public void showBannerAd()
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				bannerAd.setVisibility(View.VISIBLE);
				AdRequest.Builder builder = new AdRequest.Builder().addTestDevice("BAA254E1D59E02763BB1A917CF86CDDE");
				AdRequest ad = builder.build();

				bannerAd.loadAd(ad);
			}
		});
	}

	@Override
	public void hideBannerAd()
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				bannerAd.setVisibility(View.INVISIBLE);
			}
		});
	}

	@Override
	public boolean isWifiConnected()
	{
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

		return (ni != null && ni.isConnected());

	}
	

	@Override
	public void showInterstitialAd(final Runnable then)
	{
		runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				if (then != null)
				{
					interstitialAd.setAdListener(new AdListener()
					{
						@Override
						public void onAdClosed()
						{
							Gdx.app.postRunnable(then);
							AdRequest.Builder builder = new AdRequest.Builder().addTestDevice("BAA254E1D59E02763BB1A917CF86CDDE");
							AdRequest ad = builder.build();
							interstitialAd.loadAd(ad);
							
							System.out.println("" + interstitialAd.isLoading());
						}
					});
				}
				
				interstitialAd.show();
				
			}
		});
	
	}

	@Override
	public void signIn()
	{
		// TODO Auto-generated method stub
		try
		{
			runOnUiThread(new Runnable()
			{
				// @Override
				public void run()
				{
					_gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut()
	{
		// TODO Auto-generated method stub
		try
		{
			runOnUiThread(new Runnable()
			{
				// @Override
				public void run()
				{
					_gameHelper.signOut();
				}
			});
		} catch (Exception e)
		{
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame()
	{
		// TODO Auto-generated method stub
		String str ="https://play.google.com/store/apps/details?id=com.denip.arcon";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));

	}

	@Override
	public void submitScore(long score)
	{
		// TODO Auto-generated method stub
		if (isSignedIn() == true)
		{
		
		Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_id), score);
		}
		else
		{
		// Maybe sign in here then redirect to submitting score?
		}
	}
	@Override
	public void showScore()
	{
		// TODO Auto-generated method stub
		if (isSignedIn() == true)
			startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
			else
			{
			// Maybe sign in here then redirect to showing scores?
			}
	}
	@Override
	public void submitSpeedScore(long speed)
	{
		// TODO Auto-generated method stub
		if (isSignedIn() == true)
		{
		Games.Leaderboards.submitScore(_gameHelper.getApiClient(), getString(R.string.leaderboard_Sid), speed);
		//startActivityForResult(Games.Leaderboards.getLeaderboardIntent(_gameHelper.getApiClient(), getString(R.string.leaderboard_Sid)), REQUEST_CODE_UNUSED);
		//startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(_gameHelper.getApiClient()) , REQUEST_CODE_UNUSED);
		}
		
	}
	@Override
	public void showScores()
	{
		// TODO Auto-generated method stub
		if (isSignedIn() == true)
			startActivityForResult(Games.Leaderboards.getAllLeaderboardsIntent(_gameHelper.getApiClient()), REQUEST_CODE_UNUSED);
			else
			{
			// Maybe sign in here then redirect to showing scores?
			}
	}

	@Override
	public boolean isSignedIn()
	{
		// TODO Auto-generated method stub
		return _gameHelper.isSignedIn();
	}
	
	
	@Override
	public void get_ach()
	{
		 if (_gameHelper.isSignedIn()) {
			    startActivityForResult(Games.Achievements.getAchievementsIntent(_gameHelper.getApiClient()), 101);
			  }		
	}
	
	@Override
	public void unlockAchievementGPGS(String achievementId) {
		 if (_gameHelper.isSignedIn()) {
	  Games.Achievements.unlock(_gameHelper.getApiClient(), achievementId);
		 }
	}

}
