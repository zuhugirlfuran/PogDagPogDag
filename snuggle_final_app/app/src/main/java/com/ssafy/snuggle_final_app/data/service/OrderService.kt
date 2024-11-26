package com.ssafy.snuggle_final_app.data.service

import com.ssafy.snuggle_final_app.data.model.dto.Order
import com.ssafy.snuggle_final_app.data.model.response.OrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {
    // order 객체를 저장하고 추가된 Order의 id를 반환한다.
    @POST("snuggle/order")
    suspend fun makeOrder(@Body body: Order): Response<Int>

    // {orderId}에 해당하는 주문의 상세 내역을 반환한다.
    @GET("snuggle/order/{orderId}")
    suspend fun getOrderDetail(@Path("orderId") orderId: Int): OrderResponse
}