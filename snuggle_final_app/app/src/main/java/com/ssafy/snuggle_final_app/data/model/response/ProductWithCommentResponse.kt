package com.ssafy.snuggle_final_app.data.model.response

import com.google.gson.annotations.SerializedName
import com.ssafy.snuggle_final_app.data.model.dto.Comment

data class ProductWithCommentResponse(
    @SerializedName("productId") val productId: Int = 0,              // 상품 ID
    @SerializedName("cId") val cId: Int = 0,                   // 카테고리 ID
    @SerializedName("productName") val productName: String = "",      // 상품 이름
    @SerializedName("price") val productPrice: Int = 0,          // 상품 가격
    @SerializedName("content") val content: String = "",              // 상품 설명
    @SerializedName("img") val productImg: String = "",               // 상품 이미지
    @SerializedName("likeCount") var likeCount: Int = 0,              // 좋아요 수
    @SerializedName("comments") val comments: List<Comment> = emptyList() // 댓글 리스트
)
