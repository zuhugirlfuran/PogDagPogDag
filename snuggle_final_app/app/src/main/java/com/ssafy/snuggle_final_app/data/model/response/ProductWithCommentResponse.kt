package com.ssafy.snuggle_final_app.data.model.response

import com.google.gson.annotations.SerializedName
import com.ssafy.snuggle_final_app.data.model.dto.Comment

data class ProductWithCommentResponse(
    @SerializedName("productId") val productId: Int = 0,
    @SerializedName("commentId") val commentId: Int = 0,
    @SerializedName("productName") val productName: String = "",
    @SerializedName("price") val productPrice: Int = 0,
    @SerializedName("content") val content: String = "",
    @SerializedName("img") val productImg: String = "",
    @SerializedName("likeCount") var likeCount: Int = 0,
    @SerializedName("comments") val comments: List<Comment> = emptyList()
)
