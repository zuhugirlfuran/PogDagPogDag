package com.ssafy.snuggle_final_app.service

import com.ssafy.snuggle_final_app.dto.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("snuggle/user")
    suspend fun insert(@Body body: User): Boolean

    @GET("snuggle/user/info")
    suspend fun getUserInfo(@Query("id") id: String): UserResponse

    @GET("snuggle/user/isUsed")
    suspend fun isUsedId(@Query("id") id: String): Boolean

    @POST("snuggle/user/login")
    suspend fun login(@Body body: User): User
}
