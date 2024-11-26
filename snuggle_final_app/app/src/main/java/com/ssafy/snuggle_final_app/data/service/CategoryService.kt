package com.ssafy.snuggle_final_app.data.service

import com.ssafy.snuggle_final_app.data.model.dto.Category
import com.ssafy.snuggle_final_app.data.model.dto.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryService {


    // 카테고리 id에 따라 카테고리 목록 불러오기
    // 그냥 전체 카테고리 목록을 불러오는게 낫지 않을까..
    @GET("/snuggle/category/{commentId}")
    suspend fun getCategory(
        @Path("commentId") commentId: Int
    ): Category

    // 카테고리 목록 불러오기
    @GET("/snuggle/category")
    suspend fun getAllCategory(): List<Category>

    // 카테고리별로 상품 리스트 불러오기
    @GET("/snuggle/category/{commentId}/products")
    suspend fun getProductByCategoryId(@Path("commentId") commentId: Int): List<Product>

}