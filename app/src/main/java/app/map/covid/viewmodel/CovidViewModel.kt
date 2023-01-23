package app.map.covid.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import app.map.covid.db.provideCovidDao
import app.map.covid.retrofit.CentersApi
import app.map.covid.retrofit.RetrofitDataClass
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CovidViewModel(application: Application) : ViewModel() {
    companion object {
        private const val TAG = "테스트"
    }

//    val count: MutableLiveData<Int> = MutableLiveData()
//    val nextActivity: LiveData<Int>
//        get() = count
//    val responseData: MutableLiveData<CentersApi> = MutableLiveData()
//    private val api = RetrofitDataClass.startRetrofit()
//    internal val covidDao by lazy { provideCovidDao(getContext()) }
//    private val _onClickEvent = MutableLiveData<ClickEvent<Boolean>>()
//    val onClickEvent: LiveData<ClickEvent<Boolean>> = _onClickEvent
//
//    fun callRetrofit() {
//        CoroutineScope(Dispatchers.IO).launch {
//            repeat(10) { a ->    // 1부터 10까지(1P당 서브 페이지 10, 총 100)
//                api.getCovidCenter(a + 1, 10).enqueue(object : Callback<CentersApi> {
//                    override fun onResponse(
//                        call: Call<CentersApi>,
//                        response: Response<CentersApi>
//                    ) {
//                        if (response.isSuccessful) {
//                            // code == 200
//                            responseData.value = response.body()
//                            Log.d(TAG, responseData.value.toString())
//                            CoroutineScope(Dispatchers.IO).launch {
//                                for (i in responseData.value!!.centersModel.indices) {
//                                    covidDao.insertAll(responseData.value!!.centersModel[i])
//                                    Log.d(TAG, responseData.value!!.centersModel[i].id.toString())
//                                }
//
//                                withContext(Dispatchers.Main) {
//                                    // n회 완료
//                                    count.value = a + 1
//                                }
//                            }
//                        } else {
//                            // code == 400
//                            Log.d(TAG, response.toString())
//                        }
//                    }
//
//                    override fun onFailure(call: Call<CentersApi>, t: Throwable) {
//                        // code == 500
//                        Log.d(TAG, t.toString())
//                        cancel()
//                        count.value = 500
//                    }
//                })
//                delay(120L)
//            }
//
//        }
//    }
//
//    fun onClickEvent() {
//        if (count.value == 10) {
//            _onClickEvent.value = ClickEvent(true)
//        } else {
//            _onClickEvent.value = ClickEvent(false)
//        }
//    }
}
