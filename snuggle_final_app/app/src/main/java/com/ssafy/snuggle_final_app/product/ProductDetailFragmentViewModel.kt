package com.ssafy.snuggle_final_app.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.Product
import com.ssafy.snuggle_final_app.data.model.response.ProductWithCommentResponse
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.launch

class ProductDetailFragmentViewModel(private val handle: SavedStateHandle) : ViewModel() {

    var productId = handle.get<Int>("productId") ?: 0
        set(value) {
            handle["productId"] = value
            field = value
        }

    // 상품 리스트 반환
    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> get() = _productList

    // 상품 정보 반환 : 댓글이랑 같이
    private val _productInfo = MutableLiveData<ProductWithCommentResponse>()
    val productInfo: LiveData<ProductWithCommentResponse>
        get() = _productInfo

    fun getProductList() {
        viewModelScope.launch {
            safeApiCall(
                {
                    RetrofitUtil.productService.getProductList()
                }, { productList ->
                    if (productList.isNotEmpty()) {
                        _productList.value = productList
                        Log.d("Product", "상품 리스트 불러오기 성공")
                    } else {
                        Log.e("Product", "상품 리스트 불러오기 실패")
                        _productList.value = emptyList()
                    }
                }, { exception ->
                    Log.e("Product", "상품 리스트 불러오기 오류: ${exception.message}")
                    _productList.value = emptyList()
                }
            )
        }
    }

    fun getProductWithComments(productId: Int) {
        viewModelScope.launch {
            safeApiCall(
                {
                    RetrofitUtil.productService.getProductWithComments(productId)
                }, { productInfo ->
                    _productInfo.value = productInfo
                    Log.d("ProductInfo", "상품 정보 불러오기 성공")

                }, { exception ->
                    Log.e("ProductInfo", "상품 정보 불러오기 오류: ${exception.message}")
                    _productInfo.value = ProductWithCommentResponse()
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