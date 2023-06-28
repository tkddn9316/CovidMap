package app.map.covid.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import app.map.covid.R
import app.map.covid.base.BaseActivity
import app.map.covid.databinding.ActivityLogoBinding
import app.map.covid.util.Constant.BACK_PRESS_TIME
import app.map.covid.util.FLog
import app.map.covid.viewmodel.LogoViewModel

class LogoActivity : BaseActivity<ActivityLogoBinding, LogoViewModel>() {

    private var backTime: Long = 0

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

    override fun onBackPressed() {
        val time = System.currentTimeMillis() - backTime
        if (time >= BACK_PRESS_TIME) {
            backTime = System.currentTimeMillis()
            Toast.makeText(this, R.string.app_exit_instructions, Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }
}