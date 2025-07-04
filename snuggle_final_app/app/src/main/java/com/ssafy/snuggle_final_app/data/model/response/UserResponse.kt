package com.ssafy.snuggle_final_app.data.model.response

import com.ssafy.snuggle_final_app.data.model.dto.Order
import com.ssafy.snuggle_final_app.data.model.dto.User


data class UserResponse(
    val user: User,
    val order: List<Order>
)
