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
        activityLogoBinding = DataBindingUtil.setContentView(this, R.layout.activity_logo)

        moveMain()
    }

    private fun moveMain() {
        viewModel.callRetrofit()
        // 관찰하여 데이터 값이 변경되면 호출
        viewModel.nextActivity.observe(this, Observer { count ->
            showProgress()
            activityLogoBinding.pbProgressing.progress = count
            if (count == 10) {
                hideProgress()
                startActivity(Intent(this@LogoActivity, MainActivity::class.java))
                finish()
            }
        })
    }

    private fun showProgress() {
        activityLogoBinding.pbSplashLoading.visibility = View.VISIBLE
        activityLogoBinding.pbProgressing.visibility = View.VISIBLE
        activityLogoBinding.txtProgressingText.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        activityLogoBinding.pbSplashLoading.visibility = View.GONE
        activityLogoBinding.pbProgressing.visibility = View.GONE
        activityLogoBinding.txtProgressingText.visibility = View.GONE
    }

    override fun onBackPressed() {

    }
}