package com.ssafy.snuggle_final_app.data.model.dto

data class Address(
    val userId: String,
    val userName: String,
    val address: String,
    val phone: String
) {

    var addressId = -1

    constructor(
        addressId: Int,
        userId: String,
        userName: String,
        address: String,
        phone: String
    ) : this(userId, userName, address, phone) {
        this.addressId = addressId
    }
}