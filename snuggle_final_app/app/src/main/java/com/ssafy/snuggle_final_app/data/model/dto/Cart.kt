package com.ssafy.snuggle_final_app.data.model.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Cart(
    val productId: Int,   // 상품 ID 추가
    val img: String,      // 상품 이미지
    val title: String,    // 상품 제목
    var productCnt: Int,  // 상품 수량
    val price: Int,       // 상품 가격 (단가)
    var deliveryDate: String, // 배송 날짜
    var totalPrice: Int = productCnt * price // 총 가격 계산
) : Serializable {
    // 장바구니에 동일 상품 추가 시 수량과 가격 업데이트
    fun addProduct(cart: Cart) {
        this.productCnt += cart.productCnt
        this.totalPrice = this.productCnt * this.price
    }
}
