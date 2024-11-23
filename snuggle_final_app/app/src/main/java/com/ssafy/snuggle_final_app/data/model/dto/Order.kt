package com.ssafy.snuggle_final_app.data.model.dto

data class Order(
    val orderId: Int = 0,
    val userId: String,
    val addressId: Int,
    val details: List<OrderDetail>
)
