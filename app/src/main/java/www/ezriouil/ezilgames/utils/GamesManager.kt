package www.ezriouil.ezilgames.utils

import www.ezriouil.ezilgames.remote.Game
import www.ezriouil.ezilgames.remote.GameDto

object GamesManager {

    private var _games = mutableListOf<GameDto>()

    fun setGamesDto(newGamesDto: List<GameDto>) {
        _games.addAll(newGamesDto)
    }

    fun getGames(): List<Game> = _games.map { it.toGame() }

    fun getRowAd() = _games.last().toAd()
    fun getApplovinAd() = _games[_games.size - 2].toAd()
    fun getUnityAd() = _games[_games.size - 3].toAd()

    fun carouselImages() = _games.map { it.toCarousel() }.asSequence().shuffled().take(5).toList()

    fun clear() {
        _games.clear()
    }


}