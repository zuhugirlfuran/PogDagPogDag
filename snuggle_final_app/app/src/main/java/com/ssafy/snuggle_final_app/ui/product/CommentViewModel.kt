package com.ssafy.snuggle_final_app.ui.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.Comment
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.launch

class CommentViewModel : ViewModel() {

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> get() = _comments

    // 댓글 리스트 불러오기
    fun fetchComments(productId: Int) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.productService.getProductWithComments(productId)
            }, { commentlist ->
                _comments.value = commentlist.comments
            }, { exception ->
                Log.e("Comment", "댓글 불러오기 서버 오류: ${exception.message}")
            })
        }
    }

    // 댓글 추가
    fun addComment(comment: Comment) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.commentService.addComment(comment)
            }, { commentId ->
                if (commentId >= 0) {
                    _comments.value = _comments.value?.toMutableList()?.apply {
                        add(0, comment)
                    }
                } else {
                    Log.e("Comment", "댓글 추가 실패. 반환된 ID: $commentId")
                }
            }, { exception ->
                Log.e("Comment", "댓글 추가 서버 오류: ${exception.message}")
            })
        }
    }

    // 댓글 삭제
    fun deleteComment(commentId: Int) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.commentService.deleteCommemt(commentId)
            }, { result ->
                if (result > 0) {
                    _comments.value = _comments.value!!.filter { it.cId != commentId }
                }
            }, { exception ->
                Log.e("Comment", "댓글 불러오기 서버 오류: ${exception.message}")
            })
        }
    }

    // 댓글 수정
    fun updateComment(comment: Comment) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.commentService.updateComment(comment) // 서버 호출
            }, { updatedCommentId ->
                if (updatedCommentId > 0) {
                    _comments.value = _comments.value?.map {
                        if (it.cId == updatedCommentId) comment else it
                    }
                } else {
                    Log.e("Comment", "댓글 수정 실패. 반환된 ID: $updatedCommentId")
                }
            }, { exception ->
                Log.e("Comment", "댓글 수정하기 서버 오류: ${exception.message}")
            })
        }
    }


    // 공통으로 API 호출을 안전하게 처리하는 함수
    private suspend fun <T> safeApiCall(
        apiCall: suspend () -> T,
        onSuccess: (T) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        try {
            val result = apiCall()
            onSuccess(result)
        } catch (e: Exception) {
            onFailure(e)
        }
    }
}