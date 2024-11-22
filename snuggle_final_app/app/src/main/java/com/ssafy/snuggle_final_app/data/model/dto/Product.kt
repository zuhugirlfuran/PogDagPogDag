package com.ssafy.snuggle_final_app.data.model.dto

import java.io.Serializable

data class Product(
    val name: String,
    val price: String,
    val imageResId: Int // 이미지 리소스 ID
) : Serializable {
    var id = -1

    constructor(
        _id: Int,
        name: String,
        price: String,
        imageResId: Int
    ) : this(name, price, imageResId) {
        id = _id
    }
}