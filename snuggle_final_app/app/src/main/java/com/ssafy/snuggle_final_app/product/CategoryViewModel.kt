package com.ssafy.snuggle_final_app.product

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.Category
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val _categoryList = MutableLiveData<List<Category>>()
    val categoryList: LiveData<List<Category>> = _categoryList

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