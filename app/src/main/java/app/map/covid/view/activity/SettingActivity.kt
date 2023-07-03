package app.map.covid.view.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import app.map.covid.R
import app.map.covid.view.base.BaseActivity
import app.map.covid.databinding.ActivitySettingBinding
import app.map.covid.view.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding, SettingViewModel>() {

    override val viewModel: SettingViewModel by viewModels()

    override fun setup() {
        setBinding(R.layout.activity_setting)
    }

    override fun onCreateView(savedInstanceState: Bundle?) {

    }

    override fun onItemClick(v: View) {

    }
}