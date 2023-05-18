package app.map.covid.base

import android.app.Application
import android.content.Context
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.map.covid.util.FLog
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.schedulers.Schedulers

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()
    val loading = ObservableBoolean(false)
    val title: MutableLiveData<String> = MutableLiveData()

    /** RxJava 통신을 위한 함수 */
    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    public override fun onCleared() {
        super.onCleared()
        FLog.e(javaClass.simpleName, "onCleared")
        compositeDisposable.clear()
    }

    fun getContext(): Context {
        return getApplication<Application>().applicationContext
    }

    fun <T> Single<Any>.networkThread(loading: Consumer<Boolean>? = null): Single<Any> {
        return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                AndroidSchedulers.mainThread().scheduleDirect { loading?.accept(true) }
            }
            .doOnError {  }
            .doFinally { loading?.accept(false) }
    }

    fun <T> Flowable<Any>.networkThread(loading: Consumer<Boolean>? = null): Flowable<Any> {
        return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                AndroidSchedulers.mainThread().scheduleDirect {
                    loading?.accept(true)
                }
            }
            .doOnError {  }
            .doFinally {
                loading?.accept(false)
            }
    }
}