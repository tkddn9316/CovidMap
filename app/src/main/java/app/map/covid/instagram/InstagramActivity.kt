package app.map.covid.instagram

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import app.map.covid.R
import app.map.covid.databinding.ActivityInstagramBinding
import app.map.covid.retrofit.RetrofitDataClass
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URLDecoder

class InstagramActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInstagramBinding
    private val api = RetrofitDataClass.startRetrofit2()
    private val api2 = RetrofitDataClass.startRetrofit3()

    private val BASE_URL = "https://api.instagram.com/"
    private val OAUTH_PARAM = "oauth"
    private val CLIENT_ID_PARAM = "client_id"
    private val AUTHORIZE_PARAM = "authorize"
    private val SECRET_KEY_PARAM = "client_secret"
    private val REDIRECT_URI = "redirect_uri"
    private val SCOPE_PARAM = "scope"
    private val RESPONSE_TYPE_PARAM = "response_type"
    private val USER_PROFILE = "user_profile"
    private val USER_MEDIA = "user_media"
    private val ass = "instagram_graph_user_media"
    private val CODE = "code"
    private val GRANT_TYPE_PARAM = "authorization_code"
    private val REDIRECT_URI_PARAM = "redirect_uri"
    private val BASIC_DETAILS_FIELDS = "id,username"
    private val QUESTION_MARK: String? = "?"
    private val AMPERSAND = "&"
    private val EQUALS = "="
    private val SLASH = "/"
    private val COMMA = ","

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_instagram)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_instagram)
        loadWebView()
    }

    private fun getAccessToken(authCode: String) {
        api.getAccessToken(code = authCode).enqueue(object : Callback<String?> {
            override fun onFailure(call: Call<String?>, t: Throwable) {
                Log.e("TAG", "UserOnFailure ${t.localizedMessage}")
            }

            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                try {
                    val json = JSONObject(response.body()!!)
                    val accessToken = json.getString("access_token")
                    val userId = json.getString("user_id")

                    Log.e("결과", json.toString())
                    getUserData(accessToken, userId)
                    binding.text2.text = "accessToken: " + accessToken
                    binding.webview.visibility = View.GONE
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })

    }

    private fun getUserData(accessToken: String, userId: String) {
        api2.media(
            "CdzhzWdFR7HqasyGAs9oo93TexzcKDmWjU9I1o0",
            "id,media_type,media_url,username,timestamp",
            accessToken
        ).enqueue(object : Callback<String?> {
            override fun onFailure(call: Call<String?>, t: Throwable) {

                Log.e("결과", "getUserDataOnFailure ${t.localizedMessage}")
            }

            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {

                try {
                    val json = JSONObject(response.body()!!)
                    Log.e("결과", json.toString())
                    val id = json.getString("id")
                    val userName = json.getString("username")
                    val mediaUrl = json.getString("media_url")
                    val mediaType = json.getString("media_type")
                    val timestamp = json.getString("timestamp")

                    val details = "id: $id\nName: $userName\nmediaUrl: $mediaUrl\n" +
                            "media_type: $mediaType\n" +
                            "timestamp: $timestamp"

                    binding.text3.text = details
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebView() {
        with(binding.webview) {
            settings.javaScriptEnabled = true
            webViewClient = MyWebViewClient()
            val authUrl: String = getAuthorizationUrl()
            Log.e("하하하하하ㅏ하하하ㅏ하", "Authorize: $authUrl")

            loadUrl(authUrl)
        }
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val authorizationUrl = request?.url.toString()
            if (authorizationUrl.contains("https://socialsizzle.herokuapp.com/auth/?code")) {
                val uri = Uri.parse(authorizationUrl)
                val codeEncoded = uri.encodedQuery
                val afterDecode: String = URLDecoder.decode("$codeEncoded", "UTF-8")
                val token = afterDecode.split("code=")
                val codePart1 = token[1]
                val codePart2 = codePart1.split("#_")
                val code = codePart2[0]
                Log.e("###", "code:  $token")
                binding.text1.text = "authCode: " + code

                getAccessToken(code)
            } else {
                view!!.loadUrl(authorizationUrl)
            }
            return true
        }
    }

    private fun getAuthorizationUrl(): String {
        return (BASE_URL
                + OAUTH_PARAM + SLASH + AUTHORIZE_PARAM + SLASH + QUESTION_MARK + CLIENT_ID_PARAM + EQUALS + "685661945883631" +
                AMPERSAND + SECRET_KEY_PARAM + EQUALS + "882240f089a3ce1de7121454be3ba74c" +
                AMPERSAND + REDIRECT_URI + EQUALS + "https://socialsizzle.herokuapp.com/auth/" +
                AMPERSAND + SCOPE_PARAM + EQUALS + USER_PROFILE + COMMA + USER_MEDIA + COMMA + ass +
                AMPERSAND + RESPONSE_TYPE_PARAM + EQUALS + CODE)
    }
}