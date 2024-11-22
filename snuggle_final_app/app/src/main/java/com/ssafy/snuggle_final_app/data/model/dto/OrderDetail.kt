package com.ssafy.snuggle_final_app.data.model.dto

data class OrderDetail(
    val detailId: Int,       // 주문 상세 ID (Primary Key)
    val orderId: Int,        // 주문 ID (Foreign Key)
    val productId: Int,      // 상품 ID (Foreign Key)
    val quantity: Int,       // 상품 개수
    val orderTime: String,   // 주문 시간
    val completed: String    // 배송 상태 ('Y', 'N')
)
