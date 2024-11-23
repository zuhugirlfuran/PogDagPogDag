package com.ssafy.snuggle_final_app.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.Product
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(private val handle: SavedStateHandle) : ViewModel() {

    private val _navigateToSearch = MutableLiveData<Boolean>() // 검색 프래그먼트 전환 이벤트
    val navigateToSearch: LiveData<Boolean> get() = _navigateToSearch


    private val _searchQuery = MutableLiveData<String?>() // 검색어 LiveData
    val searchQuery: LiveData<String?> get() = _searchQuery


    private val _searchProducts = MutableLiveData<List<Product>>() // 검색 결과 LiveData
    val searchProducts: LiveData<List<Product>> get() = _searchProducts


    private var productList = listOf<Product>() // 전체 상품 리스트

    private var debounceJob: Job? = null


    private fun setProductList(products: List<Product>) {
        productList = products
    }

    fun clearData() {
        _searchQuery.value = null
    }

    fun fetchProducts() {
        viewModelScope.launch {
            try {
                val products = RetrofitUtil.productService.getProductList()
                setProductList(products) // 검색 기능에 사용할 리스트 설정
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 검색어 변동 감지
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query

        // Debounce 적용
        debounceJob?.cancel() // 기존 Job 취소
        debounceJob = viewModelScope.launch {
            delay(300) // 300ms 대기
            performSearch(query)
            if (query.isNotBlank()) {
                _navigateToSearch.value = true // 검색창으로 이동 이벤트 트리거
            }
        }
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) {
            _searchProducts.value = emptyList()
            return
        }

        val filteredProducts = productList.filter { product ->
            product.productName.contains(query, ignoreCase = true) // 이름에 검색어 포함 여부 확인
        }

        println("Filtered Products: $filteredProducts")
        _searchProducts.value = filteredProducts
    }


}