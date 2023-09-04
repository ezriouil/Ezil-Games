package www.ezriouil.ezilgames.remote

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import www.ezriouil.ezilgames.utils.Constant

interface Api {

    @GET(Constant.API_GET)
    suspend fun getGames(): Response<List<GameDto>>

    companion object {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(Api::class.java)
    }
}

