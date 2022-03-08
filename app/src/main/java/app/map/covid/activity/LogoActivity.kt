package app.map.covid.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.map.covid.R
import app.map.covid.databinding.ActivityLogoBinding
import app.map.covid.viewmodel.CovidViewModel
import app.map.covid.viewmodel.CovidViewModelFactory
import kotlinx.coroutines.*

class LogoActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "테스트"
    }

    private lateinit var activityLogoBinding: ActivityLogoBinding
    internal val viewModelFactory by lazy { CovidViewModelFactory(application) }
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(CovidViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_logo)
        activityLogoBinding = DataBindingUtil.setContentView<ActivityLogoBinding>(this, R.layout.activity_logo).apply {
            lifecycleOwner = this@LogoActivity
            vm = viewModel
        }

        moveMain()

        viewModel.onClickEvent.observe(this, Observer {
            if (it.peekContent()) {
                it.getContentIfNotHandled()?.let {
                    startActivity(Intent(this@LogoActivity, MainActivity::class.java))
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
                activityLogoBinding.txtProgressingText.text = resources.getString(R.string.splash_fail)
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