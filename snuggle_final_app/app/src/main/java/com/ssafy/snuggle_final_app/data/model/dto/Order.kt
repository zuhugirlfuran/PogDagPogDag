package com.ssafy.snuggle_final_app.data.model.dto

data class Order(
    val orderId: Int,        // 주문 ID (Primary Key)
    val userId: String,      // 사용자 ID (Foreign Key)
    val addressId: Int,      // 주소 ID (Foreign Key)
    val totalPrice: Double   // 총 가격
)
