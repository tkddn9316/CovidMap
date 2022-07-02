package app.map.covid.instagram

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.webkit.*
import androidx.databinding.DataBindingUtil
import app.map.covid.R
import app.map.covid.databinding.DialogWebviewBinding

class InstagramLoginDialog(
    context: Context,
    private val mUrl: String,
    private val mListener: OAuthDialogListener
) : Dialog(context) {
    lateinit var binding: DialogWebviewBinding

    companion object {
        private const val TAG = "Instagram-WebView"
        fun newInstance(
            context: Context,
            url: String,
            listener: OAuthDialogListener
        ): InstagramLoginDialog {

            return InstagramLoginDialog(
                context,
                url,
                listener
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.dialog_webview)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_webview,
            null,
            false
        )

        try {
            if (window != null) window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(window!!.attributes)
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
            window!!.attributes = lp
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setCancelable(true)

        setUpWebView()

        CookieSyncManager.createInstance(context)
        val cookieManager = CookieManager.getInstance()
        cookieManager.flush()

        binding.imgClose.setOnClickListener {
            onClose()
        }
    }

    interface OAuthDialogListener {
        fun onComplete(accessToken: String?)
        fun onError(error: String?)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpWebView() {

        with(binding) {
            webview.isVerticalScrollBarEnabled = false
            webview.isHorizontalScrollBarEnabled = false
            webview.webViewClient = OAuthWebViewClient()
            webview.settings.javaScriptEnabled = true
            webview.webChromeClient = WebChromeClient()
            webview.loadUrl(mUrl)
        }
    }

    private inner class OAuthWebViewClient : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            binding.progressBar.visibility = View.GONE
            super.onPageFinished(view, url)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            binding.progressBar.visibility = View.VISIBLE
            super.onPageStarted(view, url, favicon)
        }

        override fun shouldOverrideUrlLoading(
            view: WebView,
            url: String
        ): Boolean {
            Log.e(TAG, "Redirecting URL $url")
            if (url.startsWith(context.getString(R.string.instagram_redirect_uri))) {
                val urls = url.split("=").toTypedArray()
                Log.e("TAG", "Token: " + urls[1])
                mListener.onComplete(urls[1])
                dismiss()
                return true
            }
            return false
        }

        override fun onReceivedError(
            view: WebView, errorCode: Int,
            description: String, failingUrl: String
        ) {
            Log.d(TAG, "Page error: $description")
            super.onReceivedError(view, errorCode, description, failingUrl)
            mListener.onError(description)
            dismiss()
        }
    }

    private fun onClose() {
        dismiss()
    }
}