package com.ssafy.snuggle_final_app.data.model.response

import com.google.gson.annotations.SerializedName

data class OrderResponse(
//    @SerializedName("order_id") val orderId: Int,
//    @SerializedName("user_id") val userId: String,
//    @SerializedName("address_id") val addressId: Int,
//    @SerializedName("total_price") val totalPrice: Double,
//    @SerializedName("order_time") val orderDate: Date = Date(),
//    @SerializedName("completed") val orderCompleted: Char = 'N',
//    @SerializedName("details") var details: List<OrderDetailResponse> = emptyList()

    @SerializedName("order_id") val orderId: Int,
    @SerializedName("user_id") val userId: String,
    @SerializedName("total_price") val totalPrice: Double,
    @SerializedName("name") val name: String,
    @SerializedName("address") val address: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("details") var details: List<OrderDetailResponse> = emptyList()
)