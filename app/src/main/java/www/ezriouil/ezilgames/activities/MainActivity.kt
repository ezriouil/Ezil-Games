package www.ezriouil.ezilgames.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.SearchView
import com.denzcoskun.imageslider.constants.AnimationTypes
import www.ezriouil.ezilgames.R
import www.ezriouil.ezilgames.ads.Admob
import www.ezriouil.ezilgames.ads.Applovin
import www.ezriouil.ezilgames.ads.Unity
import www.ezriouil.ezilgames.databinding.ActivityMainBinding
import www.ezriouil.ezilgames.remote.Game
import www.ezriouil.ezilgames.utils.*

class MainActivity : AppCompatActivity(), Listener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var gameAdapter: GameAdapter
    private lateinit var connectionLiveData: ConnectionLiveData
    private val rowAd = GamesManager.getRowAd()
    private val applovinAd = GamesManager.getApplovinAd()
    private val unityAd = GamesManager.getUnityAd()
    private lateinit var admob: Admob
    private lateinit var applovin: Applovin
    private lateinit var unity: Unity

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(binding.root)
        connectionLiveData = ConnectionLiveData(application)
        connectionLiveData.observe(this) { network ->
            if (network) binding.networkLayout.visibility = View.GONE
            else binding.networkLayout.visibility = View.VISIBLE
        }
        initRef()
        initRecyclerView()
        initAds()
    }

    private fun initAds() {
        admob = Admob(this)
        applovin = Applovin(this)
        unity = Unity(this, unityAd.bannerAd.toString())
        banner()
    }

    private fun initRef() {
        binding.carousel.setImageList(GamesManager.carouselImages())
        binding.carousel.setSlideAnimation(AnimationTypes.ZOOM_OUT)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initRecyclerView() {
        gameAdapter = GameAdapter(this)
        gameAdapter.setGames(GamesManager.getGames())
        binding.recyclerView.adapter = gameAdapter
        search(GamesManager.getGames())
    }

    private fun search(games: List<Game>) {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                val gamesFilter = mutableListOf<Game>()
                for (item in games) {
                    if (item.gameTitle!!.lowercase().contains(newText!!.lowercase())) {
                        gamesFilter.add(item)
                    }
                }
                if (gamesFilter.isEmpty()) Toast.makeText(
                    this@MainActivity,
                    "aucun jeux",
                    Toast.LENGTH_SHORT
                ).show()
                else gameAdapter.setGames(gamesFilter)
                return true
            }
        })
    }

    override fun onCardClick(url: String?, screenMode: String?) {
        interstitial()
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(Constant.GAME_URL, url ?: "www.instagram.com/med_ezriouil")
        intent.putExtra(Constant.GAME_SCREEN_MODE, screenMode?.lowercase() ?: "portrait")
        startActivity(intent)
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

    private fun banner() {
        if (rowAd.adsVisible == 1) {
            when (rowAd.bannerType) {
                Constant.ADMOB -> {
                    rowAd.bannerAd?.let { bannerId ->
                        admob.banner(bannerId, binding.adLayout)
                    }
                }
                Constant.APP_LOVIN -> {
                    applovinAd.bannerAd?.let { bannerId ->
                        applovin.banner(bannerId, binding.adLayout)
                    }
                }
                Constant.UNITY -> {
                    unity.banner(binding.adLayout)
                }
            }
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val alertDialog =
            AlertDialog.Builder(this, R.style.bottomSheetStyle).create()
        val view = layoutInflater.inflate(R.layout.dialog, null)
        val btnExitOui = view.findViewById<Button>(R.id.btnExitOui)
        val btnExitNo = view.findViewById<Button>(R.id.btnExitNo)
        btnExitOui.setOnClickListener {
            this.finish()
        }
        btnExitNo.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.setView(view)
        alertDialog.show()
    }

}