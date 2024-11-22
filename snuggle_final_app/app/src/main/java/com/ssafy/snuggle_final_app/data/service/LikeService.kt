package com.ssafy.snuggle_final_app.data.service

import com.ssafy.snuggle_final_app.data.model.dto.Like
import com.ssafy.snuggle_final_app.data.model.dto.Product
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface LikeService {

    @POST("/snuggle/like/insert")
    suspend fun addLike(@Body like: Like): Boolean

    @GET("/snuggle/like/info/{userId}")
    suspend fun getLikeListByUser(@Path("userId") userId: String): List<Product>


}