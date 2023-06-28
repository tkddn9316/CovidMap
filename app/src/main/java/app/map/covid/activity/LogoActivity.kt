package app.map.covid.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import app.map.covid.R
import app.map.covid.base.BaseActivity
import app.map.covid.databinding.ActivityLogoBinding
import app.map.covid.viewmodel.LogoViewModel

class LogoActivity : BaseActivity<ActivityLogoBinding, LogoViewModel>() {

    override fun setup() {
        setBinding(R.layout.activity_logo, LogoViewModel::class.java)
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        viewModel.getData()
    }

    override fun onSingleClick(v: View) {
        when (v.id) {
            R.id.btn_next -> {
                startActivity(Intent(this@LogoActivity, MainActivity::class.java))
                finish()
            }

            else -> super.onSingleClick(v)
        }
    }

    override fun onDone(b: Boolean) {
        if (b) {
            viewModel.progressingText.set(resources.getString(R.string.splash_start))
        }
    }
}