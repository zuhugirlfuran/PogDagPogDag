package com.ssafy.snuggle_final_app.data.model.response

import com.google.gson.annotations.SerializedName

data class OrderDetailResponse(
    @SerializedName("d_id") val dId: Int,
    @SerializedName("order_id") val orderId: Int,
    @SerializedName("product_id") val productId: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("order_time") val orderTime: String,
    @SerializedName("completed") val completed: String,

//    @SerializedName("name") val productName: String,
//    @SerializedName("img") val productImg: String,
//    @SerializedName("unitPrice") val unitPrice: Int,
//    @SerializedName("sumPrice") val sumPrice: Int
)