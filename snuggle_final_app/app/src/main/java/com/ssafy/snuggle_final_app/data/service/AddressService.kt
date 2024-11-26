package com.ssafy.snuggle_final_app.data.service

import com.ssafy.snuggle_final_app.data.model.dto.Address
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddressService {

    @PUT("/snuggle/address")
    suspend fun updateAddress(@Body address: Address): Boolean

    @POST("/snuggle/address")
    suspend fun insertAddress(@Body address: Address): Int

    @GET("/snuggle/address/{userId}")
    suspend fun getAddress(@Path("userId") userId: String): Address

    @GET("/snuggle/address/isExist/{userId}")
    suspend fun isExist(@Path("userId") userId: String): Boolean
}