package com.ssafy.snuggle_final_app.data.service

import com.ssafy.snuggle_final_app.data.model.dto.User
import com.ssafy.snuggle_final_app.data.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Locale.Category

interface UserService {

    @POST("snuggle/user")
    suspend fun insert(@Body body: User): Boolean

    @GET("snuggle/user/info")
//    suspend fun getUserInfo(@Query("userId") id: String): Response<UserResponse>
    suspend fun getUserInfo(@Query("id", encoded = true) id: String): Response<UserResponse>

    @GET("snuggle/user/isUsed")
    suspend fun isUsedId(@Query("userId") id: String): Boolean

    @POST("snuggle/user/login")
    suspend fun login(@Body body: User): User

}
