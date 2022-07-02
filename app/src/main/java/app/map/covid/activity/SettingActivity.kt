package app.map.covid.activity

import android.os.Bundle
import android.view.View
import app.map.covid.R
import app.map.covid.base.BaseActivity
import app.map.covid.databinding.ActivitySettingBinding
import app.map.covid.viewmodel.SettingViewModel

class SettingActivity : BaseActivity<ActivitySettingBinding, SettingViewModel>() {
    override fun setup() {
        setBinding(R.layout.activity_setting, SettingViewModel::class.java)
    }

    override fun onCreateView(savedInstanceState: Bundle?) {

    }

    override fun onItemClick(v: View) {

    }
}