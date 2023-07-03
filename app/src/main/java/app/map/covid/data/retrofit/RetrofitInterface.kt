package app.map.covid.data.retrofit

import app.map.covid.BuildConfig
import app.map.covid.data.model.CentersApi
import app.map.covid.data.retrofit.ApiModule.BASE_URL
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("api/15077586/v1/centers/")
    fun getCovidCenter(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("serviceKey") serviceKey: String = BuildConfig.API_KEY
    ): Single<CentersApi>

    @GET("api/15077586/v1/centers/")
    fun getCovidCenterC(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("serviceKey") serviceKey: String = BuildConfig.API_KEY
    ): Call<CentersApi>
}