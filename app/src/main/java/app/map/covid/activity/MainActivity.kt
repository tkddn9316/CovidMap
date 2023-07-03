package app.map.covid.activity

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import app.map.covid.R
import app.map.covid.base.BaseActivity
import app.map.covid.databinding.ActivityMainBinding
import app.map.covid.util.Constant
import app.map.covid.util.Constant.LOCATION_PERMISSION_REQUEST_CODE
import app.map.covid.util.Constant.REFERANCE_LAT_X3
import app.map.covid.util.Constant.REFERANCE_LNG_X3
import app.map.covid.util.FLog
import app.map.covid.view.DialogBottomSheetMap
import app.map.covid.view.DialogRefresh
import app.map.covid.viewmodel.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.NaverMapOptions
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), OnMapReadyCallback {
    private var backTime: Long = 0
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    override val viewModel: MainViewModel by viewModels()

    override fun setup() {
        setBinding(R.layout.activity_main)
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        val mapFragment = supportFragmentManager.run {
            // 옵션 설정
            val option =
                NaverMapOptions().mapType(NaverMap.MapType.Basic)
                    // 초기 카메라 위치: 부산광역시청, 줌: 16배(어차피 바로 현재 위치 받아와서...)
                    .camera(CameraPosition(LatLng(35.1798159, 129.0750222), 16.0))
                    .locationButtonEnabled(false)
            findFragmentById(R.id.map_covid) as MapFragment? ?: MapFragment.newInstance(option)
                .also {
                    beginTransaction().add(R.id.map_covid, it).commit()
                }
        }
        // 프래그먼트(MapFragment)의 getMapAsync() 메서드로 OnMapReadyCallback 을 등록하면 비동기로 NaverMap 객체를 얻을 수 있다고 한다.
        // NaverMap 객체가 준비되면 OnMapReady() 콜백 메서드 호출
        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions,
                grantResults
            )
        ) {
            if (!locationSource.isActivated) {
                // 권한 거부됨
                FLog.e("onRequestPermissionsResult 권한 거부")
                naverMap.locationTrackingMode = LocationTrackingMode.None
            } else {
                FLog.e("onRequestPermissionsResult 권한 승인")
                naverMap.locationTrackingMode = LocationTrackingMode.Follow // 현위치 버튼 컨트롤 활성
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @UiThread
    override fun onMapReady(map: NaverMap) {
        map.locationSource = locationSource
        this.naverMap = map.apply {
            binding.btnLocation.map = this
        }
        viewModel.setMarkers()

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        // 사용자 현재 위치 받아오기
        var currentLocation: Location?
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this@MainActivity).apply {
                lastLocation.addOnSuccessListener { location: Location? ->
                    currentLocation = location
                    // 위치 오버레이의 가시성은 기본적으로 false로 지정되어 있습니다. 가시성을 true로 변경하면 지도에 위치 오버레이가 나타납니다.
                    // 파랑색 점, 현재 위치 표시
                    naverMap.locationOverlay.run {
                        isVisible = true
                        position = LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
                    }

                    // 카메라 현재 위치로 이동
                    val cameraUpdate = CameraUpdate.scrollTo(
                        LatLng(
                            currentLocation!!.latitude,
                            currentLocation!!.longitude
                        )
                    )
                    with(naverMap) {
                        naverMap.moveCamera(cameraUpdate)
                        locationTrackingMode = LocationTrackingMode.Follow
                    }
                }
            }
    }

    override fun onDone(b: Boolean) {
        if (b) {
            // 마커 적용
            viewModel.markerList.forEach { marker ->
                marker.map = naverMap
                marker.setOnClickListener {
                    naverMap.moveCamera(
                        CameraUpdate.scrollAndZoomTo(marker.position, 16.0)
                            .animate(CameraAnimation.Easing)
                    )
                    DialogBottomSheetMap(
                        this@MainActivity,
                        marker.tag as HashMap<String, Any>
                    ).apply {
                        show(supportFragmentManager, tag)
                    }
                    true
                }
            }
            viewModel.done.value = false
        }
    }

    override fun onBackPressed() {
        val time = System.currentTimeMillis() - backTime
        if (time >= Constant.BACK_PRESS_TIME) {
            backTime = System.currentTimeMillis()
            Toast.makeText(this, R.string.app_exit_instructions, Toast.LENGTH_SHORT).show()
        } else {
            super.onBackPressed()
        }
    }

    override fun onRefresh() {
        super.onRefresh()
        DialogRefresh(context) {
            val count = it.toLong()
            if (count <= 200) {
                if (viewModel.markerList.isNotEmpty()) {
                    // 지도상에 표시되고 있는 마커들 삭제
                    for (marker: Marker in viewModel.markerList) {
                        marker.map = null
                    }
                    viewModel.markerList.clear()
                }
                try {
                    viewModel.resetMarkers(count)
                } catch (e: Exception) {
                    Toast.makeText(this, R.string.error_content, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, R.string.to_many_markers, Toast.LENGTH_SHORT).show()
            }
        }.show()
    }

    // 현재 카메라가 보고있는 위치
    private fun getCurrentPosition(naverMap: NaverMap): LatLng {
        val cameraPosition: CameraPosition = naverMap.cameraPosition
        return LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude)
    }

    private fun withinSightMarker(currentPosition: LatLng, markerPosition: LatLng): Boolean {
        val withinSightMarkerLat: Boolean =
            abs(currentPosition.latitude - markerPosition.latitude) <= REFERANCE_LAT_X3
        val withinSightMarkerLng: Boolean =
            abs(currentPosition.longitude - markerPosition.longitude) <= REFERANCE_LNG_X3
        return withinSightMarkerLat && withinSightMarkerLng
    }
}