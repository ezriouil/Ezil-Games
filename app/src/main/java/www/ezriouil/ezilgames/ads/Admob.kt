package www.ezriouil.ezilgames.ads

import android.app.Activity
import android.view.ViewGroup
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class Admob(private val activity: Activity) {

    private var adRequest: AdRequest

    init {
        MobileAds.initialize(activity)
        adRequest = AdRequest.Builder().build()
    }

    fun banner(bannerId: String, viewGroup: ViewGroup) {
        val mAdView = AdView(activity)
        mAdView.setAdSize(AdSize.BANNER)
        mAdView.adUnitId = bannerId
        mAdView.loadAd(adRequest)
        viewGroup.addView(mAdView)
    }

    fun interstitial(interId: String) {
        InterstitialAd.load(
            activity,
            interId,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {}
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitialAd.show(activity)
                }
            })
    }

}