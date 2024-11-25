package com.ssafy.snuggle_final_app.data.model.dto

import java.io.Serializable

data class Comment(
    val commentId: Int = 0,
    val productId: Int,
    val userId: String,
    val comment: String
)
