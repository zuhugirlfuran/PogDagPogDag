package com.ssafy.snuggle_final_app.data.model.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    val productName: String,        // 제품 이름
    val price: Int,                 // 제품 가격
    @SerializedName("img")
    val image: String? = null,      // 이미지 경로 (nullable)
    val content: String? = null,    // 제품 설명 (nullable)
    val likeCount: Int = 0,         // 좋아요 수 (기본값 0)
    @SerializedName("cId")  val cId: Int             // 카테고리 ID (FOREIGN KEY)
) : Serializable {
    var productId: Int = -1         // 제품 ID (기본값 -1)

    // 추가 생성자
    constructor(
        productId: Int,
        cId: Int,
        productName: String,
        price: Int,
        image: String? = null,
        content: String? = null,
        likeCount: Int = 0
    ) : this(productName, price, image, content, likeCount, cId) {
        this.productId = productId
    }
}
