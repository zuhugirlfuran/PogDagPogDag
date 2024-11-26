package com.ssafy.snuggle_final_app.data.model.dto

data class Stamp(
    val sId: Int,        // 스탬프 ID (Primary Key)
    val userId: String,  // 사용자 ID (Foreign Key)
    val orderId: Int,    // 주문 ID (Foreign Key)
    val quantity: Int    // 스탬프 개수
) {
    // 기본 생성자
    constructor() : this(0, "", 0, 0)

    // 매개변수 생성자
    constructor(userId: String, orderId: Int, quantity: Int) : this(0, userId, orderId, quantity)
}
