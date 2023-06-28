# CovidMap
코로나19 예방접종센터 지도 서비스

해당 코로나 서비스 앱은 Splash, Main(Map) 화면으로 이루어져 있으며, 각 화면의 사용 기술 및 간단한 로직은 아래와 같다.

## LogoActivity(Splash)
* 소개

|![KakaoTalk_20220308_150627820](https://user-images.githubusercontent.com/50853634/157177224-23a66abb-6a0a-42b0-be76-011b88cbbf5b.jpg)|![KakaoTalk_20220308_150627820_01](https://user-images.githubusercontent.com/50853634/157177391-f9417a0e-e8b1-4525-a06c-11c9b775d754.jpg)|
|:---:|:---:|
|공공 데이터로 정보 받는 중|정보 받기 성공 이후|

앱 시작 시 가장 먼저 나오는 화면으로 코로나 검진 센터 100개의 데이터를 받아와 내부 DB에 저장한다.
데이터는 시설명, 전화번호, 지역, 위도, 경도 등으로 이루어져 있다.
* 사용 기술
1. Retrofit
2. Room
3. MVVM
4. ViewModel
5. RxJava
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
