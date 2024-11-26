package com.ssafy.snuggle_final_app.data.service

import com.ssafy.snuggle_final_app.data.model.dto.Favorite
import com.ssafy.snuggle_final_app.data.model.dto.FavoriteRequest
import com.ssafy.snuggle_final_app.data.model.dto.Tagging
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FavoriteService {

    // 즐겨찾기 추가
    @POST("snuggle/favorite/insert")
    suspend fun toggleFavorite(@Body favorite: FavoriteRequest): Response<Boolean>

    // 사용자 즐겨찾기 조회
    @GET("snuggle/favorite/info")
    suspend fun getFavoriteTaggingList(@Query("userId") userId: String): Response<List<Tagging>>

    @GET("snuggle/favorite/{userId}")
    suspend fun getUserFavorites(@Path("userId") userId: String): Response<List<Favorite>>
}
