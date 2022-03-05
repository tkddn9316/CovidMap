package app.map.covid.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object RetrofitDataClass {
    const val BASE_URL: String =
        "https://api.odcloud.kr/api/15077586/v1/centers/"

    fun startRetrofit(): RetrofitInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RetrofitInterface::class.java)
    }
}