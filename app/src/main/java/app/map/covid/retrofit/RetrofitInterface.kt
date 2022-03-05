package app.map.covid.retrofit

import app.map.covid.BuildConfig
import app.map.covid.retrofit.RetrofitDataClass.BASE_URL
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET(BASE_URL)
    fun getCovidCenter(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("serviceKey") serviceKey: String = BuildConfig.API_KEY
    ): Call<CentersApi>
}