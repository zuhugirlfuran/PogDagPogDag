package com.ssafy.snuggle_final_app.data.model.dto

data class User(
    val userId: String,      // 사용자 이메일 ID (Primary Key)
    val password: String,    // 비밀번호
    val nickname: String,    // 닉네임
    val age: Int,            // 나이
    val gender: String,      // 성별 ('M' or 'F')
    val path: String,        // 경로 (선택)
    val token: String,       // 토큰
    val img: String,         // 프로필 이미지 (선택)
    val stamps: Int,         // 스탬프
    val stampList: List<Stamp> = ArrayList() // 스탬프 리스트
) {
    constructor() : this("", "", "", 0, "", "", "", "", 0, ArrayList())
    constructor(userId: String, password: String) : this(userId, password, "", 0, "", "", "", "", 0, ArrayList())
    constructor(userId: String, nickname: String, password: String) : this(userId, password, nickname, 0, "", "", "", "", 0, ArrayList())
}
