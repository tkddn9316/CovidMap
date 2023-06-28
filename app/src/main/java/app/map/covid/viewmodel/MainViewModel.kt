package app.map.covid.viewmodel

import android.app.Application
import androidx.lifecycle.*
import app.map.covid.R
import app.map.covid.base.BaseViewModel

class MainViewModel(application: Application) : BaseViewModel(application) {
    init {
        title.set(getContext().getString(R.string.title_main))
        back.set(false)
    }
}
