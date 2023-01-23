package app.map.covid.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import app.map.covid.R
import app.map.covid.base.BaseActivity
import app.map.covid.databinding.ActivityLogoBinding
import app.map.covid.viewmodel.LogoViewModel
import kotlinx.coroutines.*
import java.util.*

class LogoActivity : BaseActivity<ActivityLogoBinding, LogoViewModel>() {

    override fun setup() {
        setBinding(R.layout.activity_logo, LogoViewModel::class.java)
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        moveMain()
        viewModel.onClickEvent.observe(this, {
            if (it.peekContent()) {
                it.getContentIfNotHandled()?.let {
                    startActivity(Intent(this@LogoActivity, MainActivity::class.java))
                    finish()
                }
            }
        })
    }

    private fun moveMain() {
        binding.txtProgressingText.visibility = View.VISIBLE
        viewModel.callRetrofit()
        // 관찰하여 데이터 값이 변경되면 호출
        viewModel.nextActivity.observe(this, { count ->

            if (count <= 10) {
                showProgress()

                if (count == 10) {
                    hideProgress()
                    binding.btnNext.isClickable = true
                }
            } else {
                binding.btnNext.isClickable = false
                binding.pbSplashLoading.visibility = View.GONE
                binding.txtProgressingText.text =
                    resources.getString(R.string.splash_fail)
                binding.imgNext.visibility = View.GONE
            }
        })
    }

    private fun showProgress() {
        binding.pbSplashLoading.visibility = View.VISIBLE
        binding.txtProgressingText.text = resources.getString(R.string.splash_loading)
        binding.imgNext.visibility = View.GONE
    }

    private fun hideProgress() {
        binding.pbSplashLoading.visibility = View.GONE
        binding.txtProgressingText.text = resources.getString(R.string.splash_start)
        binding.imgNext.visibility = View.VISIBLE
    }
}