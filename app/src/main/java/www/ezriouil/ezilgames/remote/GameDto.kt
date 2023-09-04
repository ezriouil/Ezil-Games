package www.ezriouil.ezilgames.remote

import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

data class GameDto(
    val adsVisible: Int? = null,
    val bannerAd: String? = null,
    val bannerType: String? = null,
    val gameBg: String? = null,
    val gameTitle: String? = null,
    val gameUrl: String? = null,
    val interAd: String? = null,
    val interType: String? = null,
    val screenType: String? = null
) {
    fun toGame() = Game(gameBg, gameTitle, gameUrl, screenType)
    fun toAd() = Ad(adsVisible, bannerAd, bannerType, interAd, interType)
    fun toCarousel() = SlideModel(gameBg, ScaleTypes.FIT)
}