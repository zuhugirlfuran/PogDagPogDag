package com.ssafy.snuggle_final_app.data.service

import com.ssafy.snuggle_final_app.data.model.dto.Category
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.Locale

interface CategoryService {


    // 카테고리 id에 따라 카테고리 목록 불러오기
    // 그냥 전체 카테고리 목록을 불러오는게 낫지 않을까..
    @GET("/snuggle/category/{cId}")
    suspend fun getCategory(
        @Path("cId") cId: Int
    ): Locale.Category

    // 카테고리 id에 따라 카테고리 목록 불러오기
    // 그냥 전체 카테고리 목록을 불러오는게 낫지 않을까..
    @GET("/snuggle/category")
    suspend fun getAllCategory(): List<Category>

}