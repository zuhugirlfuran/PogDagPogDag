package com.ssafy.snuggle_final_app.data.model.dto


data class Tagging(
    val taggingId: String,      // 태깅 ID (Primary Key)
    val videoSrc: String,       // 비디오 경로
    val videoTitle: String,     // 비디오 제목
    val videoContent: String,   // 비디오 내용
    val videoLike: Int = 0      // 좋아요 수 (기본값: 0)
)
