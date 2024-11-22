package com.ssafy.snuggle_final_app.data.model.dto

import java.io.Serializable

data class Comment(
    val comment: String,
    val productId: Int,
    val userId: String,
    val commentId: Int = -1
) : Serializable
