package com.ssafy.snuggle_final_app.data.service

import com.ssafy.snuggle_final_app.data.model.dto.Tagging
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TaggingService {
    @GET("snuggle/tagging/{tagId}")
    suspend fun getTaggingById(@Path("tagId") taggingId: String): Tagging
}