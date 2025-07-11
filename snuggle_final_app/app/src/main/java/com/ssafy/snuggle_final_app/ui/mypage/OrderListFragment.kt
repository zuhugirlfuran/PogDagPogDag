import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.base.ApplicationClass
import com.ssafy.snuggle_final_app.base.BaseFragment
import com.ssafy.snuggle_final_app.data.model.dto.Cart
import com.ssafy.snuggle_final_app.databinding.FragmentOrderListBinding
import com.ssafy.snuggle_final_app.ui.mypage.OrderCompleteListFragment
import com.ssafy.snuggle_final_app.ui.mypage.OrderListViewModel
import com.ssafy.snuggle_final_app.ui.order.OrderCompleteFragment

class OrderListFragment : BaseFragment<FragmentOrderListBinding>(
    FragmentOrderListBinding::bind,
    R.layout.fragment_order_list
) {

    private lateinit var adapter: OrderListAdapter
    private val orderListViewModel: OrderListViewModel by viewModels()

    override fun onResume() {
        super.onResume()
        // Activity의 BottomNavigationView를 숨김
        activity?.findViewById<ConstraintLayout>(R.id.bottom_navigation)?.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 어댑터 초기화
        adapter = OrderListAdapter(
            mutableListOf(),
            orderListViewModel.productInfoMap,
            viewLifecycleOwner,
            onProductRequest = { productId ->
                orderListViewModel.getProductWithComments(productId)
            },
            onOrderClick = { order ->
                navigateToOrderCompleteFragment(order.orderId) // 클릭 시 주문 완료 내역으로 이동
            }
        )
        binding.orderlistLv.adapter = adapter // ListView와 어댑터 연결

        // 사용자 ID 가져오기
        val userId = ApplicationClass.sharedPreferencesUtil.getUser().userId

        // OrderListViewModel에서 데이터 요청
        orderListViewModel.getOrderListByUserId(userId)

        // ViewModel의 데이터 관찰
        orderListViewModel.orderList.observe(viewLifecycleOwner) { orderList ->
            adapter.updateData(orderList) // 어댑터 데이터 업데이트
        }
    }


    private fun navigateToOrderCompleteFragment(orderId: Int) {
        // OrderCompleteFragment로 데이터 전달
        val fragment = OrderCompleteListFragment().apply {
            arguments = Bundle().apply {
                putInt("orderId", orderId)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_frameLayout, fragment)
            .addToBackStack(null)
            .commit()
    }

}
