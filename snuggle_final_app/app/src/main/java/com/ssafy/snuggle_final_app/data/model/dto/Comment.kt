package com.ssafy.snuggle_final_app.data.model.dto

import java.io.Serializable

data class Comment(
    val comment: String,
    val productId: Int,
    val userId: String
) {

    var cId = -1

    constructor(
        cId: Int,
        comment: String,
        productId: Int,
        userId: String
    ) : this(comment, productId, userId) {
        this.cId = cId
    }
}
