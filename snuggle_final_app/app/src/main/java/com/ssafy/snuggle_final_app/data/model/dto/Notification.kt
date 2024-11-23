package com.ssafy.snuggle_final_app.data.model.dto

data class Notification(
    val nId: Int = 0, // 서버에서 생성
    val userId: String,
    val title: String,
    val content: String,
    val channel: String,
    val isSubscribed: Boolean,
    val time: String = ""
)