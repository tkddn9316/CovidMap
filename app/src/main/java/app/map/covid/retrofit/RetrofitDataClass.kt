package app.map.covid.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

object RetrofitDataClass {
    const val BASE_URL: String =
        "https://api.odcloud.kr/api/15077586/v1/centers/"
    const val BASE_URL2: String =
        "https://api.instagram.com/"
    const val BASE_URL3: String =
        "https://graph.instagram.com/"


    fun startRetrofit(): RetrofitInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getUnsafeOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RetrofitInterface::class.java)
    }

    fun startRetrofit2(): RetrofitInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL2)
            .client(getUnsafeOkHttpClient().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RetrofitInterface::class.java)
    }

    fun startRetrofit3(): RetrofitInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL3)
            .client(getUnsafeOkHttpClient().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build())
            .addConverterFactory(ScalarsConverterFactory.create())
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