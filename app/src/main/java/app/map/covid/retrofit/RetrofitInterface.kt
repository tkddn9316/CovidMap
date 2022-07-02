package app.map.covid.retrofit

import app.map.covid.BuildConfig
import app.map.covid.MyApplication
import app.map.covid.R
import app.map.covid.retrofit.RetrofitDataClass.BASE_URL
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {
    @GET(BASE_URL)
    fun getCovidCenter(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int,
        @Query("serviceKey") serviceKey: String = BuildConfig.API_KEY
    ): Call<CentersApi>

    @FormUrlEncoded
    @POST("oauth/access_token")
    fun getAccessToken(
        @Field("client_id") client_id: String = MyApplication.myApplication!!.getString(R.string.instagram_app_id),
        @Field("client_secret") client_secret: String = MyApplication.myApplication!!.getString(R.string.instagram_secret),
        @Field("redirect_uri") redirect_uri: String = MyApplication.myApplication!!.getString(R.string.instagram_redirect_uri),
        @Field("code") code: String,
        @Field("grant_type") scope: String = "authorization_code"
    ): Call<String>

    @GET("{media-id}")
    fun media(
        @Path("media-id") mediaId: String,
        @Query("fields") fields: String,
        @Query("access_token") access_token: String
    ): Call<String>
}