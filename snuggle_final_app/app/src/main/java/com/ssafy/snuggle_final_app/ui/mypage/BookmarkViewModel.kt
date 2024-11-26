package com.ssafy.snuggle_final_app.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.Tagging
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "BookmarkViewModel"

class BookmarkViewModel : ViewModel() {

    private val _bookmarkList = MutableLiveData<List<Tagging>>()
    val bookmarkList: LiveData<List<Tagging>> get() = _bookmarkList

    fun getBookmarkList(userId: String) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.favoriteService.getFavoriteTaggingList(userId)
            }, { response ->
                if (response.isSuccessful) {
                    _bookmarkList.value = response.body()
                }
            }, {
                Log.d(TAG, "getBookmarkList: 북마크 리스트 불러오는데 실패했습니다.")
                _bookmarkList.value = emptyList()
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