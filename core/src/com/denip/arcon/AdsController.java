package com.denip.arcon;

 interface AdsController
{
	public void showBannerAd();
	public void hideBannerAd();
	public void showInterstitialAd (Runnable then);
	public boolean isWifiConnected();
}
