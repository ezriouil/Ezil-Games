package www.ezriouil.ezilgames.utils

import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.monstertechno.adblocker.AdBlockerWebView
import com.monstertechno.adblocker.util.AdBlocker

class AdBlock() : WebViewClient() {

    override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
        return if (AdBlockerWebView.blockAds(
                view,
                url
            )
        ) AdBlocker.createEmptyResource() else super.shouldInterceptRequest(view, url)
    }
}