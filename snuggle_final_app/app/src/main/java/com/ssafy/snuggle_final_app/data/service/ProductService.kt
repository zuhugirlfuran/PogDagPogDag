package com.ssafy.snuggle_final_app.data.service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.Locale.Category

interface ProductService {

    // 카테고리 id에 따라 카테고리 목록 불러오기
    @GET("/snuggle/category/{cId}")
    suspend fun getCategory(
        @Path("cId") cId: Int
    ) : Response<Category>

}