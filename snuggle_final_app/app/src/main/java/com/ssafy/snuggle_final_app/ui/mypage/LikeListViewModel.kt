package com.ssafy.snuggle_final_app.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.Product
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "LikeListViewModel"

class LikeListViewModel : ViewModel() {

    private val _likeProductList = MutableLiveData<List<Product>>()
    val likeProductList: LiveData<List<Product>> get() = _likeProductList

    fun getLikeProductList(userId: String) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.likeService.getLikeListByUser(userId)
            }, { likeProducts ->
                _likeProductList.value = likeProducts
            }, { exception ->
                Log.d(TAG, "getLikeProductList: 좋아요한 상품 리스트를 불러올 수 없습니다.")
                _likeProductList.value = emptyList()
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