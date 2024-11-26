package com.ssafy.snuggle_final_app.fcm

import retrofit2.Call
import retrofit2.http.*

interface FirebaseTokenService {

    // Retrofit 관련 애노테이션 @POST, @Query

    // Token정보 서버로 전송
    @POST("token")
    fun uploadToken(@Query("token") token: String): Call<String>

}
