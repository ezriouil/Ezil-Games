package www.ezriouil.ezilgames.activities

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import www.ezriouil.ezilgames.R
import www.ezriouil.ezilgames.ads.Admob
import www.ezriouil.ezilgames.ads.Applovin
import www.ezriouil.ezilgames.ads.Unity
import www.ezriouil.ezilgames.databinding.ActivityGameBinding
import www.ezriouil.ezilgames.utils.AdBlock
import www.ezriouil.ezilgames.utils.Constant
import www.ezriouil.ezilgames.utils.GamesManager

class GameActivity : AppCompatActivity() {

    private lateinit var adBlock: AdBlock
    private lateinit var binding: ActivityGameBinding
    private val rowAd = GamesManager.getRowAd()
    private val applovinAd = GamesManager.getApplovinAd()
    private val unityAd = GamesManager.getUnityAd()
    private lateinit var admob: Admob
    private lateinit var applovin: Applovin
    private lateinit var unity: Unity

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGameBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(binding.root)
        intent.getStringExtra(Constant.GAME_SCREEN_MODE)?.let { screenMode ->
            when (screenMode) {
                Constant.MODE_PORTRAIT -> this.requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                Constant.MODE_LANDSCAPE -> this.requestedOrientation =
                    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                else -> null
            }
        }
        intent.getStringExtra(Constant.GAME_URL)?.let { gameUrl ->
            webView(gameUrl)
        }
        initAds()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webView(url: String) {
        adBlock = AdBlock()
        binding.webView.webViewClient = adBlock
        binding.webView.settings.useWideViewPort = true
        binding.webView.settings.setSupportMultipleWindows(true)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(url)
    }

    private fun initAds() {
        admob = Admob(this)
        applovin = Applovin(this)
        unity = Unity(this, unityAd.bannerAd.toString())
    }

    private fun interstitial() {
        if (rowAd.adsVisible == 1) {
            when (rowAd.interType) {
                Constant.ADMOB -> {
                    rowAd.interAd?.let { interId ->
                        admob.interstitial(interId)
                    }
                }
                Constant.APP_LOVIN -> {
                    applovinAd.interAd?.let { interId ->
                        applovin.interstitial(interId)
                    }
                }
                Constant.UNITY -> {
                    unity.initialize()
                }
            }
        }
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            val alertDialog = AlertDialog.Builder(this, R.style.bottomSheetStyle).create()
            val view = layoutInflater.inflate(R.layout.dialog, null)
            val btnExitOui = view.findViewById<Button>(R.id.btnExitOui)
            val btnExitNo = view.findViewById<Button>(R.id.btnExitNo)
            btnExitOui.setOnClickListener {
                interstitial()
                this.finish()
            }
            btnExitNo.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.setView(view)
            alertDialog.show()
        }
    }
}

//this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
