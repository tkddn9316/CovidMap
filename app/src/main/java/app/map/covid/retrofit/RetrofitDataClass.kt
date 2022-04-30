package app.map.covid.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitDataClass {
    const val BASE_URL: String =
        "https://api.odcloud.kr/api/15077586/v1/centers/"

    fun startRetrofit(): RetrofitInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RetrofitInterface::class.java)
    }

    // 인증서 없이 앱에서 HTTPS 우회 접속 통신
    fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {

            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        val sslSocketFactory = sslContext.socketFactory

        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { hostname, session -> true }

        return builder
    }
}