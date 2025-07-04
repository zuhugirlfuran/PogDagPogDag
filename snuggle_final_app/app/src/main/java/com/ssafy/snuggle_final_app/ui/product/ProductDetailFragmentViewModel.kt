package com.ssafy.snuggle_final_app.ui.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.Like
import com.ssafy.snuggle_final_app.data.model.dto.Product
import com.ssafy.snuggle_final_app.data.model.response.ProductWithCommentResponse
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.launch

class ProductDetailFragmentViewModel(private val handle: SavedStateHandle) : ViewModel() {

    var productId = handle.get<Int>("productId") ?: 0
        set(value) {
            handle["productId"] = value
            field = value
            fetchProductInfo(value)
        }

    private fun fetchProductInfo(productId: Int) {
        if (productId > 0) {
            viewModelScope.launch {
                val response = RetrofitUtil.productService.getProductWithComments(productId)
                Log.d("ViewModel", "fetchProductInfo: $response")
                if (response.productId > 0) {
                    _productInfo.value = response
                } else {
                    _productInfo.value = null
                }
            }
        }
    }

    private val _check = MutableLiveData<Boolean>()
    fun setCheck(data: Boolean) {
        _check.postValue(data)
    }

    // 상품 리스트 반환
    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> get() = _productList

    // 베스트 상품 리스트 반환
    private val _bestProductList = MutableLiveData<List<Product>>()
    val bestProductList: LiveData<List<Product>> get() = _bestProductList

    // 새로운 상품 리스트 반환
    private val _newProductList = MutableLiveData<List<Product>>()
    val newProductList: LiveData<List<Product>> get() = _newProductList

    // 상품 정보 반환 : 댓글이랑 같이
    private val _productInfo = MutableLiveData<ProductWithCommentResponse?>()
    val productInfo: LiveData<ProductWithCommentResponse?>
        get() = _productInfo

    // 상품의 좋아요가 눌려있는 지
    private val _isProductLiked = MutableLiveData<Boolean>(false)
    val isProductLiked: LiveData<Boolean> get() = _isProductLiked

    // 데이터
    private val _isSuccess = MutableLiveData(false)
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    fun setIsSucess() {
        _isSuccess.value = false
    }

    // 스피너 정렬 데이터 부분
    val sortedProductList = MutableLiveData<List<Product>>() // 정렬된 데이터

    // 전체 상품 리스트 불러오기
    fun getProductList() {
      //  if (isProductListFetched) return // 이미 호출된 경우
        viewModelScope.launch {
            safeApiCall(
                {
                    RetrofitUtil.productService.getProductList()
                }, { productList ->
                    if (productList.isNotEmpty()) {
                        _productList.value = productList
                     //   isProductListFetched = true
                        _isSuccess.value = true
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

    // 베스트 상품 5개 불러오기
    fun getBestProductList() {
        viewModelScope.launch {
            safeApiCall(
                {
                    RetrofitUtil.productService.getBestProduct()
                }, { bestProducts ->
                    if (bestProducts.isNotEmpty()) {
                        _bestProductList.value = bestProducts
                    } else {
                        _bestProductList.value = emptyList()
                    }
                }, { exception ->
                    Log.e("Product", "베스트 상품 리스트 불러오기 오류: ${exception.message}")
                    _bestProductList.value = emptyList()
                }
            )
        }
    }

    // 새로운 상품 5개 불러오기
    fun getNewProductList() {
        viewModelScope.launch {
            safeApiCall(
                {
                    RetrofitUtil.productService.getNewProduct()
                }, { newProducts ->
                    if (newProducts.isNotEmpty()) {
                        _newProductList.value = newProducts
                    } else {
                        _newProductList.value = emptyList()
                    }
                }, { exception ->
                    Log.e("Product", "새로운 상품 리스트 불러오기 오류: ${exception.message}")
                    _newProductList.value = emptyList()
                }
            )
        }
    }

    private val _isProductInfo = MutableLiveData<Boolean>(false)
    val isProductInfo: LiveData<Boolean> get() = _isProductInfo

    fun setIsProductInfo() {
        _isProductInfo.value = false
    }

    // productId에 맞는 상품 정보 불러오기 + 댓글
    fun getProductWithComments(productId: Int) {
        viewModelScope.launch {
            safeApiCall(
                {
                    RetrofitUtil.productService.getProductWithComments(productId)
                }, { productInfo ->
                    _productInfo.value = productInfo
                    _isProductInfo.value = true
                    Log.d("ProductInfo", "상품 정보 불러오기 성공 $productInfo")

                }, { exception ->
                    Log.e("ProductInfo", "상품 정보 불러오기 오류: ${exception.message}")
                    _productInfo.value = ProductWithCommentResponse()
                }
            )
        }
    }

    // 좋아요 추가
    fun likeProduct(like: Like) {
        Log.d("LikeRequest", "Sending Like object: $like")
        viewModelScope.launch {
            safeApiCall(
                {
                    RetrofitUtil.likeService.addLike(like)
                }, { isLiked ->
                    // 좋아요 상태
                    _isProductLiked.value = isLiked
                    // 현재 likeCount 가져오기
                    val currentLikeCount = _productInfo.value?.likeCount ?: 0

                    // 좋아요 상태에 따라 likeCount 변경
                    val updatedLikeCount = if (isLiked) {
                        currentLikeCount + 1
                    } else {
                        (currentLikeCount - 1).coerceAtLeast(0) // 최소 0으로 제한
                    }
                    // productInfo 업데이트
                    _productInfo.value = _productInfo.value?.apply {
                        likeCount = updatedLikeCount
                    }
                    Log.d("Like", if (isLiked) "좋아요 등록" else "좋아요 해제")
                }, { exception ->
                    Log.e("Like", "좋아요 상태 업데이트 오류: ${exception.message}")
                }
            )
        }
    }

    // 좋아요 여부 확인
    fun isLikeStatus(userId: String, productId: Int) {
        viewModelScope.launch {
            safeApiCall(
                { RetrofitUtil.likeService.getLikeListByUser(userId) },
                { likedProducts ->
                    // 좋아요 리스트에서 현재 상품의 좋아요 여부 확인
                    val isLiked = likedProducts.any { it.productId == productId }
                    _isProductLiked.value = isLiked
                    Log.d("Like", "초기 좋아요 상태: $isLiked")
                },
                { exception ->
                    Log.e("Like", "좋아요 상태 초기화 오류: ${exception.message}")
                    _isProductLiked.value = false // 오류 시 기본값 설정
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