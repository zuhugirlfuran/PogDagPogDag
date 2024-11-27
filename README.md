# final_project_2

O2O 기반 스마트 스토어 시스템 - 스토어
- 뜨개질 소품샵 어플(폭닥폭닥)

<br>


<center>

<img src="https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=fff&style=for-the-badge">
<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff&style=for-the-badge">
<img src="https://img.shields.io/badge/firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=white">

<img src="https://img.shields.io/badge/Figma-F24E1E?logo=figma&logoColor=fff&style=for-the-badge">
<img src="https://img.shields.io/badge/Android%20Studio-3DDC84?logo=androidstudio&logoColor=fff&style=for-the-badge"> 
<img src="https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=fff&style=for-the-badge"> 
<img src="https://img.shields.io/badge/SQLite-4479A1?style=for-the-badge&logo=SQLite&logoColor=white">
<img src="https://img.shields.io/badge/NFC-002E5F?logo=nfc&logoColor=fff&style=for-the-badge"> 
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">


</center>

<br>

# 📌팀원
## TEAM02 - 즈후걸프란

| 이름   | 나이   | 출신 | 역할         | 특징 |
| ------ | ------ | ---- | ------------ | ---- |
| 서정후 | 00년생 | 대구 | 팀장, 풀스택, 디자인 |      |
| 이승지 | 98년생 | 대구 | 팀원, 풀스택, 발표 |      |
| 김미경 | 00년생 | 제주 | 팀원, 풀스택, 영상편집 |      |

<br>

## Final PJT

### 1. 목표

1.  안드로이드 컴포넌트의 구조를 이해하고 활용할 수 있다.
2.  SpringBoot 서버를 구현하며, 안드로이드 애플리케이션과의 Json 통신 구조를 이해하고 활용할 수 있다.
3.  SpringBoot 서버와 FCM을 활용하여 Foreground/Background 앱 푸시를 구현할 수 있다.
4.  안드로이드 근거리 통신기술(NFC, BLE, Beacon)을 이해하고 이를 활용하여 다양한 서비스를 구현할 수 있다.
5.  안드로이드 JetPack 라이브러리를 활용하여 프로젝트를 확장성 있게 구현할 수 있다.
6.  Flutter 개발환경, Dart 언어를 이해하여 유연한 앱을 구현할 수 있다.

<br>

### 2. 구현기능(주요 서비스)

### UI/UX
1.  ViewPager 활용한 이벤트 배너 화면
2.  비콘을 통해 오프라인 매장 방문시 쿠폰 추가
3.  인기순/최신순/가격순 및 카테고리별로 상품 필터링 조회
4.  커스텀한 바텀 네비게이션

### 응용 기능
1. 부트 페이 활용한 결제 기능
2. NFC를 응용해 영상 띄우고 책갈피 할 수 있는 기능
3. ChatGPT API를 활용한 AI 챗봇



<br>
<br>

## 결과



### 시연 영상

[![시연 영상](http://img.youtube.com/vi/nk1NqiET128?si=lUORe3QOvnetR7YZ/0.jpg)](https://youtu.be/nk1NqiET128?si=lUORe3QOvnetR7YZ) 


<br>
<br>

### UI 별 기능 구현

### 1. 회원 관리(로그인/회원가입/로그아웃)
- 회원 가입
    - 회원정보 추가
    - id 중복 체크
    - 비밀번호 보이기/숨기기 기능
- 로그인
    - 쿠키를 sharedpreference에 저장해 로그아웃 전까지 앱 실행시 로그인 유지
- 로그아웃
    - 로그인 정보 삭제

<img width=700 src="https://github.com/user-attachments/assets/66d80c3a-eaad-4188-a7a4-fcc67bfa4161">

### 2. 상품 페이지(상품 리스트/상품 상세/댓글(리뷰))
- 홈 화면
    - viewPager를 활용한 움직이는 배너 구현
    - 검색 기능 구현
- 상품 리스트
    - 카테고리별로 상품 조회 가능
    - 인기순/가격순/최신순으로 정렬 가능
- 상품 상세
    - 상품 좋아요 기능. 마이페이지에서 조회 가능
    - 상품 상세 정보 조회(가격, 이름, 상품 설명, 리뷰 확인)
- 댓글(리뷰) 작성
    - 다른 사용자의 리뷰를 조회 가능
    - 본인의 리뷰도 수정/삭제/추가 가능

<img width=700 src="https://github.com/user-attachments/assets/24da8d90-3ef5-46a5-b8bd-838277c26c9c">

### 3. 주문 관리(주문화면/장바구니/결제화면/결제완료)
- 주문 화면
    - 바로 구매 / 장바구니로 이동 기능
    - 바로 구매 입력 시, 주문 화면으로 이동
    - 장바구니 입력 시, 장바구니로 상품 이동
- 장바구니
    - 주문 하기전, 상품 삭제 및 수량 수정 가능    
    - 주문하기 누르면 주문 화면으로 이동
- 주문 화면
    - 주문 시, 상품 내역을 확인하고 개인 정보(주소 정보) 입력 가능)
    - 이미 주소 정보 존재 시 그 정보를 띄워줌
- 결제 화면
    - Bootpay API를 통해 결제 기능 구현
- 결제 완료 화면
    - 영수증처럼 결제 완료 화면 구현


<img width=700 src="https://github.com/user-attachments/assets/24da8d90-3ef5-46a5-b8bd-838277c26c9c">

### 4. 마이페이지(주문내역/관심상품/영상 책갈피/회원정보)
- 마이페이지
    - 회원 정보 조회 가능
    - 비콘을 통해 얻은 쿠폰 조회 기능
    - 결제 시, 스탬프 적립
    - 스템프 10개 적립 될때마다 등급 레벨 1씩 상승
- 주문 내역
    - 결제한 내역 조회 가능
- 영상 책갈피
    - 북마크한 nfc로 띄운 영상을 조회 가능
    - 클릭 시, nfc 영상으로 이동해 다시 조회 가능
- 관심 상품
    - 상품 상세에서 좋아요한 상품 리스트 조회
    - 상픔 상세 클릭 시, 상세로 이동

<img width=700 src="https://github.com/user-attachments/assets/1324df03-59e9-4e33-b0d4-75e0a7676368">


### 5. nfc/비콘 활용
- nfc 활용
    - nfc를 스캔해 해당 상품의 추가 정보를 띄움
- 비콘 
    - 매장 입장 시, 비콘 수신해 쿠폰 다이얼로그 띄움
    - 다이얼로그에서 확인 클릭 시, 쿠폰 등록
    - 쿠폰 정보 마이페이지에서 조회 가능

<img width=700 src="https://github.com/user-attachments/assets/1ce4549f-7a4c-4248-8ccd-e9962fb03768">

### 5. FCM 알림 조회
- 알림을 채널별로 수신해, 특정 사용자에게만 알림 수신 가능하게 구현
    - delivery, broad 채널로 구분
    - 배달과 전체 알림으로 구별하여 수신할 수 있게함
- 사용자 별로 해당하는 서로 다른 알림 기록을 가지며 조회 가능

<img height=400 src="https://github.com/user-attachments/assets/12d3c1f5-8a67-4aa2-9793-be1b6f61f28e">

### 7. 후기

<img width=700 src="https://github.com/user-attachments/assets/c9330fb6-7b47-4045-96e2-d55b6bedb021">



<br>
<br>


### ERD 다이어그램
<img width=500 src="https://github.com/user-attachments/assets/cce7e7df-07f8-4cc1-8148-44835a1b9918">

### flow chart

<img width=500 src="https://github.com/user-attachments/assets/4597cb50-119d-4c98-8fce-732d3a03fb16">

### API 명세서

<img width=500 src="https://github.com/user-attachments/assets/1fc7a3c2-a242-43be-8feb-576959561a42">

### 아키텍처

<img width=500 src="https://github.com/user-attachments/assets/429bf776-e26e-4104-af35-dfabe8a2c8a3">

