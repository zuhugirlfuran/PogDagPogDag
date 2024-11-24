package com.ssafy.snuggle_final_app.data.model.dto

import java.sql.Timestamp

data class Coupon(
    val userId: String,
    val couponName: String,
    val couponStart: Timestamp,
    val couponEnd: Timestamp,
    val couponDiscount: Double,
    val couponUse: Boolean
) {
    private var _couponId = -1

    constructor(
        couponId: Int,
        userId: String,
        couponName: String,
        couponStart: Timestamp,
        couponEnd: Timestamp,
        couponDiscount: Double,
        couponUse: Boolean
    ) : this(userId, couponName, couponStart, couponEnd, couponDiscount, couponUse) {
        _couponId = couponId
    }
}
