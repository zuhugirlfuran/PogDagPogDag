package com.ssafy.snuggle_final_app.ui.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.Order
import com.ssafy.snuggle_final_app.data.model.response.ProductWithCommentResponse
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "OrderListViewModel_최종"

class OrderListViewModel : ViewModel() {

    // 사용자 아이디에 맞는 주문 내역 받아오기
    private val _orderList = MutableLiveData<List<Order>>()
    val orderList: LiveData<List<Order>> get() = _orderList

    fun getOrderListByUserId(userId: String) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.userService.getUserInfo(userId)
            }, { userResponse ->
                userResponse.let {
                    _orderList.value = it.order
                    Log.e(TAG, "유저 객체의 주문 리스트 불러오기 : ${it.order}")
                } ?: Log.e(TAG, "유저 객체의 주문 리스트가 비어 있습니다.")

            }, { exception ->
                Log.e(TAG, "유저 객체의 주문 리스트 불러오기 오류: ${exception.message}")
            })
        }
    }

    private val _productInfoMap = MutableLiveData<Map<Int, ProductWithCommentResponse>>()
    val productInfoMap: LiveData<Map<Int, ProductWithCommentResponse>> get() = _productInfoMap

    private val productCache = mutableMapOf<Int, ProductWithCommentResponse>()

    fun getProductWithComments(productId: Int) {
        if (productCache.containsKey(productId)) {
            _productInfoMap.value = productCache
            return
        }

        viewModelScope.launch {
            safeApiCall(
                {
                    RetrofitUtil.productService.getProductWithComments(productId)
                }, { productInfo ->
                    productCache[productId] = productInfo
                    _productInfoMap.value = productCache
                }, { exception ->
                    // 예외 처리
                }
            )
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