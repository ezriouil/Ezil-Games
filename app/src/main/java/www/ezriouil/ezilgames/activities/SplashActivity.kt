package www.ezriouil.ezilgames.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import www.ezriouil.ezilgames.databinding.ActivitySplashBinding
import www.ezriouil.ezilgames.remote.Api
import www.ezriouil.ezilgames.utils.ConnectionLiveData
import www.ezriouil.ezilgames.utils.GamesManager

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var connectionLiveData: ConnectionLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(binding.root)
        initRef()
        connectionLiveData.observe(this) { network ->
            if (network) {
                binding.networkLayout.visibility = View.GONE
                binding.splashProgress.visibility = View.VISIBLE
                checkNetwork()
            } else {
                binding.networkLayout.visibility = View.VISIBLE
                binding.splashProgress.visibility = View.INVISIBLE
            }
        }
    }

    private fun initRef() {
        connectionLiveData = ConnectionLiveData(application)
    }

    private fun checkNetwork() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val result = Api.retrofit.getGames()
                if (result.isSuccessful) {
                    result.body()?.let { listGamesDto ->
                        GamesManager.clear()
                        GamesManager.setGamesDto(listGamesDto.reversed())
                        withContext(Dispatchers.Main) {
                            val intent = Intent(this@SplashActivity, MainActivity::class.java)
                            startActivity(intent)
                            this@SplashActivity.finish()
                        }
                    }

                } else this@SplashActivity.finish()
            } catch (ex: Exception) {
                this@SplashActivity.finish()
            }
        }
    }
}