package com.ssafy.snuggle_final_app.data.model.dto

data class OrderDetail(
//    val dId: Int = 0,         // 서버에서 생성
    val orderId: Int = 0,     // 서버에서 생성
    val productId: Int,       // 상품 ID
    val quantity: Int,        // 수량
    val orderTime: String,    // 주문 시간
    val completed: String     // 완료 여부 ('Y' or 'N')
)
