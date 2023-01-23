package app.map.covid.viewmodel

import android.app.Application
import app.map.covid.base.BaseViewModel

class SettingViewModel: BaseViewModel(Application()) {
    init {
        title.value = "설정"
    }

    fun getData() {

    }
}