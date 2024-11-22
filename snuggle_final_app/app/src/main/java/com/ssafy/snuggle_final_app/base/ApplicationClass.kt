package com.ssafy.snuggle_final_app.base

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssafy.snuggle_final_app.data.local.SharedPreferencesUtil
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    // ipconfig를 통해 IP확인하기
    // 핸드폰으로 접속은 같은 인터넷으로 연결 되어있어야함 (유,무선)
//    val SERVER_URL = "http://192.168.33.117:8080/"
    val SERVER_URL = "http://192.168.33.118:8080/"
//    val SERVER_URL = "http://192.168.0.3:8080/"

    companion object {
        // 전역변수 문법을 통해 Retrofit 인스턴스를 앱 실행시 1번만 생성하여 사용 (Singleton)
        lateinit var retrofit: Retrofit
        lateinit var sharedPreferencesUtil: SharedPreferencesUtil
    }

    override fun onCreate() {
        super.onCreate()

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
//            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(ReceivedCookiesInterceptor())
            .build()


        // 앱이 처음 생성되는 순간, retrofit 인스턴스를 생성
        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        // 초기화
        sharedPreferencesUtil = SharedPreferencesUtil(applicationContext)
    }

    //GSon은 엄격한 json type을 요구하는데, 느슨하게 하기 위한 설정.
    // success, fail등 문자로 리턴될 경우 오류 발생한다. json 문자열이 아니라고..
    val gson: Gson = GsonBuilder()
        .setLenient()
        .create()
}

