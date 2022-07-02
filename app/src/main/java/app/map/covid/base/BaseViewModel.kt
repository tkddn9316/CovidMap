package app.map.covid.base

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

open class BaseViewModel() : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val loading = ObservableBoolean(false)
    val title: MutableLiveData<String> = MutableLiveData()

    /** RxJava 통신을 위한 함수 */
    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}