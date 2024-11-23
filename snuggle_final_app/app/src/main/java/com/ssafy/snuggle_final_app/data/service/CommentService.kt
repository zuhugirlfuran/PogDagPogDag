package com.ssafy.snuggle_final_app.data.service

import com.ssafy.snuggle_final_app.data.model.dto.Comment
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CommentService {

    // 댓글 추가
    @POST("/snuggle/comment")
    suspend fun addComment(@Body comment: Comment): Int

    // 댓글 수정
    @PUT("/snuggle/comment")
    suspend fun updateComment(@Body comment: Comment): Int

    // 댓글 삭제
    @DELETE("/snuggle/comment/{commentId}")
    suspend fun deleteCommemt(@Path("commentId") commentId: Int): Int
}