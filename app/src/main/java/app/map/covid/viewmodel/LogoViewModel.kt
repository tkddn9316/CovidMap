package app.map.covid.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import app.map.covid.R
import app.map.covid.base.BaseViewModel
import app.map.covid.db.provideCovidDao
import app.map.covid.retrofit.ApiModule
import app.map.covid.util.FLog
import io.reactivex.rxjava3.core.Flowable
import java.util.concurrent.TimeUnit

class LogoViewModel(application: Application) : BaseViewModel(application) {
    private val covidDao by lazy { provideCovidDao(getContext()) }
    val progressingText = ObservableField("")

    fun getData() {
        loading.set(true)
        progressingText.set(getContext().getString(R.string.splash_loading))

        addDisposable(
            covidDao.deleteAll().networkThread().andThen(
                // 10회 반복
                Flowable.intervalRange(1, 10, 100L, 100L, TimeUnit.MILLISECONDS)
            )
                .flatMap {
                    ApiModule.startRetrofit().getCovidCenter(it.toInt(), 10)
                        .networkThread().toFlowable()
                }
                .filter { it.centersModel.isNotEmpty() }
                .flatMapCompletable {
                    FLog.e(it)
                    covidDao.insert(it.centersModel).networkThread()
                }
                .doFinally { loading.set(false) }
                .subscribe({ done.value = true }, {
                    error.value = it
                    progressingText.set(getContext().getString(R.string.splash_fail))
                })
        )
    }
}