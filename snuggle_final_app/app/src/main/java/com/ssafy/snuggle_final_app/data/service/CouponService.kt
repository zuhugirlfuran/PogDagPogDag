package com.ssafy.snuggle_final_app.data.service

import com.ssafy.snuggle_final_app.data.model.dto.Coupon
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CouponService {

    // 사용자가 가진 쿠폰 정보 수정
    @PUT("/snuggle/coupon")
    suspend fun updaetCoupon(@Body coupon: Coupon): Int

    @GET("/snuggle/coupon/{userId}")
    suspend fun getCouponByUserId(@Path("userId") userId: String): List<Coupon>

    @POST("/snuggle/coupon")
    suspend fun insertCoupon(@Body coupon: Coupon) : Int
}