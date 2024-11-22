package com.ssafy.snuggle_final_app.data.model.dto

data class Favorite(
    val favoriteId: Int,        // 즐겨찾기 ID (Primary Key)
    val userId: String,         // 사용자 ID (Foreign Key -> t_user)
    val taggingId: String,      // 태깅 ID (Foreign Key -> t_tagging)
    val isValid: String         // 유효 여부 ('Y', 'N')
)
