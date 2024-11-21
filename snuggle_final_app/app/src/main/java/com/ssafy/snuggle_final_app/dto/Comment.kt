package com.ssafy.snuggle_final_app.dto

import java.io.Serializable

data class Comment(
    val comment: String,
    val productId: Int,
    val userId: String
) : Serializable {
    var id = -1

    constructor(
        _id: Int,
        comment: String,
        productId: Int,
        userId: String
    ) : this(comment, productId, userId) {
        id = _id
    }
}