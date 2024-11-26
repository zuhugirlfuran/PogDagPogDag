package com.ssafy.snuggle_final_app.data.model.dto

import java.io.Serializable

data class Like(
    val userId: String,
    val productId: Int,
    val likeTime: String = ""
) : Serializable {

    var id = -1

    constructor(
        _id: Int,
        userId: String,
        productId: Int,
        likeTime: String
    ) : this(userId, productId, likeTime) {
        id = _id
    }
}
