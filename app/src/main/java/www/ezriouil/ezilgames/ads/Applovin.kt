package www.ezriouil.ezilgames.ads

import android.app.Activity
import android.view.ViewGroup
import com.applovin.mediation.ads.MaxAdView
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.sdk.AppLovinSdk

class Applovin(private val activity: Activity) {

    init {
        AppLovinSdk.getInstance(activity).mediationProvider = "max"
        AppLovinSdk.initializeSdk(activity)
    }

    fun banner(bannerId: String, viewGroup: ViewGroup) {
        val adView = MaxAdView(bannerId, activity)
        adView.loadAd()
        viewGroup.addView(adView)
    }

    fun interstitial(interId: String) {
        val interstitialAd = MaxInterstitialAd(interId, activity)
        interstitialAd.loadAd()
        interstitialAd.showAd()
    }
}