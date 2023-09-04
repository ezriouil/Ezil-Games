package www.ezriouil.ezilgames.ads

import android.app.Activity
import android.view.ViewGroup
import android.widget.LinearLayout.LayoutParams
import com.unity3d.ads.UnityAds
import com.unity3d.services.banners.BannerView
import com.unity3d.services.banners.UnityBannerSize

class Unity(private val activity: Activity, gameId: String) {

    init {
        UnityAds.initialize(activity, gameId, false)
    }

    fun banner(viewGroup: ViewGroup) {
        val banner =
            BannerView(
                activity,
                "Banner_Android",
                UnityBannerSize(LayoutParams.MATCH_PARENT, 50)
            )
        viewGroup.addView(banner)
        banner.load()
    }

    fun initialize() {
        UnityAds.load("Interstitial_Android")
        UnityAds.show(activity, "Interstitial_Android")
    }
}