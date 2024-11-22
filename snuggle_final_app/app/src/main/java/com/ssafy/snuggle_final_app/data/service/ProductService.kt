package com.ssafy.snuggle_final_app.data.service

import com.ssafy.snuggle_final_app.data.model.dto.Product
import com.ssafy.snuggle_final_app.data.model.response.ProductWithCommentResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.Locale.Category

interface ProductService {

    // 전체 상품 목록 반환
    @GET("/snuggle/product")
    suspend fun getProductList(): List<Product>

    // 특정 productId에 해당하는 상품 정보와 댓글 반환
    @GET("/snuggle/product/{productId}")
    suspend fun getProductWithComments(@Path("productId") productId: Int) : ProductWithCommentResponse

    // best product 5개 조회
    @GET("/snuggle/product/bestProduct")
    suspend fun getBestProduct(): List<Product>

    // 새로운 product 5개 조회
    @GET("/snuggle/product/newProduct")
    suspend fun getNewProduct(): List<Product>
}