import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.snuggle_final_app.data.model.dto.Cart
import com.ssafy.snuggle_final_app.data.model.dto.Order
import com.ssafy.snuggle_final_app.data.model.response.OrderResponse
import com.ssafy.snuggle_final_app.data.service.RetrofitUtil.Companion.orderService
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class OrderViewModel : ViewModel() {

    private val _orderId = MutableLiveData<Int>()
    val orderId: LiveData<Int> get() = _orderId

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _orderDetail = MutableLiveData<OrderResponse>()
    val orderDetail: LiveData<OrderResponse> get() = _orderDetail

    private val _isOrderMaked = MutableLiveData<Int?>()
    val isOrderMaked: MutableLiveData<Int?> get() = _isOrderMaked

    private val _shoppingCart = MutableLiveData<List<Cart>>(emptyList())
    val shoppingCart: LiveData<List<Cart>> get() = _shoppingCart

    // 주문 생성 함수
    fun makeOrder(order: Order) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = orderService.makeOrder(order)
                if (response.isSuccessful) {
                    val orderId = response.body()
                    if (orderId != null) {
                        _isOrderMaked.value = orderId
                        Log.d("OrderViewModel", "Order successful: $orderId")
                    } else {
                        Log.e("OrderViewModel", "Response body is null.")
                        _error.value = "주문 생성 실패: 서버 응답 없음."
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("OrderViewModel", "Order failed: ${response.code()} - $errorBody")
                    _error.value = "주문 생성 실패: ${response.message()}"
                }
            } catch (e: HttpException) {
                Log.e("OrderViewModel", "HTTP Exception: ${e.message()}")
                _error.value = "주문 생성 실패: 네트워크 오류 (${e.message()})."
            } catch (e: IOException) {
                Log.e("OrderViewModel", "IO Exception: ${e.message}")
                _error.value = "주문 생성 실패: 서버 연결 문제 (${e.message})."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 주문 상세 조회 함수
    fun getOrderDetail(orderId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val orderResponse = orderService.getOrderDetail(orderId)
                _orderDetail.value = orderResponse
            } catch (e: HttpException) {
                Log.e("OrderViewModel", "HTTP Exception: ${e.message()}")
                _error.value = "주문 상세 조회 실패: 네트워크 오류 (${e.message()})."
            } catch (e: IOException) {
                Log.e("OrderViewModel", "IO Exception: ${e.message}")
                _error.value = "주문 상세 조회 실패: 서버 연결 문제 (${e.message})."
            } finally {
                _isLoading.value = false
            }
        }
    }

    // 장바구니에 항목 추가
    fun addShoppingList(cart: Cart) {
        _shoppingCart.value = _shoppingCart.value?.plus(cart)
    }

    // 장바구니 업데이트
    fun updateShoppingList(updatedList: List<Cart>) {
        _shoppingCart.value = updatedList
    }

    fun resetOrderState() {
        _isOrderMaked.value = null
        _shoppingCart.value = emptyList() // 주문 완료 후 장바구니 비우기
    }

}
