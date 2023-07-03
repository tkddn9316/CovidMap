package app.map.covid.view.viewmodel

import android.app.Application
import app.map.covid.R
import app.map.covid.view.base.BaseViewModel
import app.map.covid.domain.repository.CovidRepository
import app.map.covid.data.retrofit.ApiModule
import app.map.covid.util.FLog
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.collections.set

@HiltViewModel
class MainViewModel @Inject constructor(
    private val covidRepository: CovidRepository,
    application: Application
) : BaseViewModel(application) {
    //    private val covidDao by lazy { provideCovidDao(getContext()) }
    val markerList = mutableListOf<Marker>()

    init {
        title.set(getContext().getString(R.string.title_main))
        back.set(false)
    }

    fun resetMarkers(count: Long) {
        loading.set(true)
        addDisposable(
            covidRepository.deleteAll().networkThread().andThen(
                Flowable.intervalRange(1, count, 100L, 100L, TimeUnit.MILLISECONDS)
            )
                .flatMap {
                    ApiModule.startRetrofit().getCovidCenter(it.toInt(), 1)
                        .networkThread().toFlowable()
                }
                .filter { it.centersModel.isNotEmpty() }
                .flatMapCompletable {
                    FLog.e(it)
                    covidRepository.insert(it.centersModel).networkThread()
                }
                .doFinally { loading.set(false) }
                .subscribe({ setMarkers() }, { error.value = it })
        )
    }

    fun setMarkers() {
        addDisposable(
            covidRepository.getAll().networkThread(loading::set)
                .map {
                    it.forEach {
                        markerList += Marker().apply {
                            // 마커의 위치 지정
                            position = LatLng(it.lat.toDouble(), it.lng.toDouble())
                            // 마커의 정보를 담을 해쉬맵
                            val hashMap = HashMap<String, Any>().apply {
                                put("id", it.id)
                                put("address", it.address)
                                put("centerName", it.centerName)
                                put("facilityName", it.facilityName)
                                put("phoneNumber", it.phoneNumber)
                                put("updatedAt", it.updatedAt)
                            }
                            // 아이콘 설정
                            icon =
                                if (it.centerType.contains(getContext().getString(R.string.central))) {
                                    MarkerIcons.GREEN.also {
                                        hashMap["icon"] =
                                            R.drawable.navermap_default_marker_icon_green
                                    }
                                } else {
                                    MarkerIcons.BLUE.also {
                                        hashMap["icon"] =
                                            R.drawable.navermap_default_marker_icon_blue
                                    }
                                }
                            // 해당 마커의 정보 지정
                            tag = hashMap
                            isHideCollidedSymbols = true
                            isIconPerspectiveEnabled = true
                        }
                    }
                }
                .subscribe({ done.value = true }, error::setValue)
        )
    }
}
