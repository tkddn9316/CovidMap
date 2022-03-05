# CovidMap
코로나19 예방접종센터 지도 서비스

해당 코로나 서비스 앱은 Splash, Main(Map) 화면으로 이루어져 있으며, 각 화면의 사용 기술 및 간단한 로직은 아래와 같다.

## LogoActivity(Splash)
* 소개

앱 시작 시 가장 먼저 나오는 화면으로 코로나 검진 센터 100개의 데이터를 받아와 내부 DB에 저장한다.
데이터는 시설명, 전화번호, 지역, 위도, 경도 등으로 이루어져 있다.
* 사용 기술
1. Retrofit
2. Room
3. MVVM
4. ViewModel
5. Coroutine
6. DataBinding

## MainActivity(Map)
* 소개

위치 권한 확인 이후 스플래시 화면에서 저장된 DB의 값을 네이버 지도 API를 이용하여 뿌려준다.
마커를 터치 시 카메라가 해당 지역으로 이동하면서 상세 정보를 보여주게 된다.
* 사용 기술
1. Room
2. Coroutine
3. DataBinding
4. Naver Map API
