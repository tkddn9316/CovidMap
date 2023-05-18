package app.map.covid.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.map.covid.base.BaseViewModel
import app.map.covid.db.provideCovidDao
import app.map.covid.retrofit.CentersApi
import app.map.covid.retrofit.RetrofitDataClass
import app.map.covid.util.FLog
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogoViewModel(application: Application) : BaseViewModel(application) {
    val count: MutableLiveData<Int> = MutableLiveData()
    val nextActivity: LiveData<Int>
        get() = count
    val responseData: MutableLiveData<CentersApi> = MutableLiveData()
    private val api = RetrofitDataClass.startRetrofit()
    internal val covidDao by lazy { provideCovidDao(getContext()) }
    private val _onClickEvent = MutableLiveData<ClickEvent<Boolean>>()
    val onClickEvent: LiveData<ClickEvent<Boolean>> = _onClickEvent

    init {

    }

    fun callRetrofit() {
        CoroutineScope(Dispatchers.IO).launch {
            repeat(10) { a ->    // 1부터 10까지(1P당 서브 페이지 10, 총 100)
                api.getCovidCenter(a + 1, 10).enqueue(object : Callback<CentersApi> {
                    override fun onResponse(
                        call: Call<CentersApi>,
                        response: Response<CentersApi>
                    ) {
                        if (response.isSuccessful) {
                            // code == 200
                            responseData.value = response.body()
                            FLog.e(responseData.value.toString())
                            CoroutineScope(Dispatchers.IO).launch {
                                for (i in responseData.value!!.centersModel.indices) {
                                    covidDao.insertAll(responseData.value!!.centersModel[i])
                                    FLog.e(responseData.value!!.centersModel[i].id.toString())
                                }

                                withContext(Dispatchers.Main) {
                                    // n회 완료
                                    count.value = a + 1
                                }
                            }
                        } else {
                            // code == 400
                            FLog.e(response.toString())
                        }
                    }

                    override fun onFailure(call: Call<CentersApi>, t: Throwable) {
                        // code == 500
                        FLog.e(t)
                        cancel()
                        count.value = 500
                    }
                })
                delay(120L)
            }

        }
    }

    fun onClickEvent() {
        if (count.value == 10) {
            _onClickEvent.value = ClickEvent(true)
        } else {
            _onClickEvent.value = ClickEvent(false)
        }
    }
}