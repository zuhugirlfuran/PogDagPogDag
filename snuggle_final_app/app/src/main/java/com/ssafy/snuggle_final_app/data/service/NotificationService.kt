package com.ssafy.snuggle_final_app.data.service

import com.ssafy.snuggle_final_app.data.model.dto.Notification
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NotificationService {

    @POST("/snuggle/notification")
    suspend fun addNotification(@Body notification: Notification): Response<Int>

    @GET("/snuggle/notification")
    suspend fun getNotificationByUserId(@Query("userId") userId: String) : Response<List<Notification>>
}
