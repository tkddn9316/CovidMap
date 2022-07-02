package app.map.covid.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.map.covid.R
import app.map.covid.databinding.ActivityLogoBinding
import app.map.covid.instagram.InstagramActivity
import app.map.covid.viewmodel.CovidViewModel
import app.map.covid.viewmodel.CovidViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import java.util.*

class LogoActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "test"
    }

    private lateinit var activityLogoBinding: ActivityLogoBinding
    internal val viewModelFactory by lazy { CovidViewModelFactory(application) }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(CovidViewModel::class.java)
    }
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_logo)
        activityLogoBinding =
            DataBindingUtil.setContentView<ActivityLogoBinding>(this, R.layout.activity_logo)
                .apply {
                    lifecycleOwner = this@LogoActivity
                    vm = viewModel
                }

        moveMain()

        viewModel.onClickEvent.observe(this, Observer {
            if (it.peekContent()) {
                it.getContentIfNotHandled()?.let {
                    startActivity(Intent(this@LogoActivity, InstagramActivity::class.java))
                    finish()
                }
            }
        })
    }

    private fun moveMain() {
        activityLogoBinding.txtProgressingText.visibility = View.VISIBLE
        viewModel.callRetrofit()
        // 관찰하여 데이터 값이 변경되면 호출
        viewModel.nextActivity.observe(this, Observer { count ->

            if (count <= 10) {
                showProgress()

                if (count == 10) {
                    hideProgress()
                    activityLogoBinding.btnNext.isClickable = true
                }
            } else {
                activityLogoBinding.btnNext.isClickable = false
                activityLogoBinding.pbSplashLoading.visibility = View.GONE
                activityLogoBinding.txtProgressingText.text =
                    resources.getString(R.string.splash_fail)
                activityLogoBinding.imgNext.visibility = View.GONE
            }
        })
    }

    private fun showProgress() {
        activityLogoBinding.pbSplashLoading.visibility = View.VISIBLE
        activityLogoBinding.txtProgressingText.text = resources.getString(R.string.splash_loading)
        activityLogoBinding.imgNext.visibility = View.GONE
    }

    private fun hideProgress() {
        activityLogoBinding.pbSplashLoading.visibility = View.GONE
        activityLogoBinding.txtProgressingText.text = resources.getString(R.string.splash_start)
        activityLogoBinding.imgNext.visibility = View.VISIBLE
    }
}