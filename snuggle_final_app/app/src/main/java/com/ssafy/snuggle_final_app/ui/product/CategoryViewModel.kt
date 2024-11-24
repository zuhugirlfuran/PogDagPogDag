package com.ssafy.snuggle_final_app.ui.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.Category
import com.ssafy.snuggle_final_app.data.model.dto.Product
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.launch

private const val TAG = "CategoryViewModel"
class CategoryViewModel : ViewModel() {

    private val _categoryList = MutableLiveData<List<Category>>()
    val categoryList: LiveData<List<Category>> = _categoryList

    // 전체 카테고리 목록 불러오기
    fun getCategoryList() {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.categoryService.getAllCategory()
            }, { categories ->
                if (categories.isNotEmpty()) {
                    _categoryList.value = categories
                } else {
                    _categoryList.value = emptyList()
                }
            }, { exception ->
                Log.e("CategoryList", "모든 카테고리 불러오기 오류: ${exception.message}")
                _categoryList.value = emptyList()
            })
        }
    }

    private val _filteredProductList = MutableLiveData<List<Product>>()
    val filteredProductList: LiveData<List<Product>> get() = _filteredProductList

    // 카테고리별 상품 불러오기
    fun getProductByCategory(categoryId: Int) {
        viewModelScope.launch {
            safeApiCall({
                RetrofitUtil.categoryService.getProductByCategoryId(categoryId)
            }, { productlist ->
                Log.d(TAG, "getProductByCategory: $categoryId $productlist")
                if (productlist.isNotEmpty()) {
                    _filteredProductList.value = productlist
                } else {
                    _filteredProductList.value = emptyList()
                }
            }, {exception ->
                Log.e("Category", "카테고리별 상품 리스트 불러오기 오류: ${exception.message}")
                _categoryList.value = emptyList()
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