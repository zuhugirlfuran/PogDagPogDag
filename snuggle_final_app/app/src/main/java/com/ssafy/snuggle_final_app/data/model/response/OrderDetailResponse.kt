package com.ssafy.snuggle_final_app.data.model.response

import com.google.gson.annotations.SerializedName

data class OrderDetailResponse(
    val dId: Int,
    val orderId: Int,
    val productId: Int,
    val quantity: Int,
    val orderTime: String,
    val completed: String,

)