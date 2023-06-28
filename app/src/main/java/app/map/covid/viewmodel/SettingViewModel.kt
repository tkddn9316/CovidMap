package app.map.covid.viewmodel

import android.app.Application
import app.map.covid.R
import app.map.covid.base.BaseViewModel

class SettingViewModel: BaseViewModel(Application()) {
    init {
        title.set(getContext().getString(R.string.title_setting))
    }

    fun getData() {

    }
}