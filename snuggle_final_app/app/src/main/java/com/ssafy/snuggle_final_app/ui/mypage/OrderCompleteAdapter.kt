import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.response.OrderDetailResponse
import com.ssafy.snuggle_final_app.data.model.response.ProductWithCommentResponse
import com.ssafy.snuggle_final_app.ui.product.ProductDetailFragmentViewModel
import com.ssafy.snuggle_final_app.util.CommonUtils.makeComma

private const val TAG = "OrderCompleteAdapter"
class OrderCompleteAdapter(
    private val context: Context,
    private var orderList: MutableList<OrderDetailResponse>,
    private val productViewModel: ProductDetailFragmentViewModel
) : BaseAdapter() {

    private var productMap: Map<Int, ProductWithCommentResponse> = emptyMap()

    override fun getCount(): Int = orderList.size

    override fun getItem(position: Int): Any = orderList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_order_detail, parent, false)

        val order = orderList[position]
        val productTitle = view.findViewById<TextView>(R.id.order_tv_product_name)
        val productCount = view.findViewById<TextView>(R.id.order_tv_product_count)
        val productPrice = view.findViewById<TextView>(R.id.order_tv_product_price)

        // 기본 데이터 설정
        productCount.text = order.quantity.toString()

        // Map에서 Product 데이터 가져오기
        val productn = mutableListOf<String>()
        val product = productMap[order.productId]
        product?.let {
            productTitle.text = it.productName
            productn.add(it.productName)
            productPrice.text = makeComma(it.productPrice)
        }
        Log.d(TAG, "getView: $product $productn")

        return view
    }

    fun updateData(newOrderList: List<OrderDetailResponse>) {
        orderList.clear()
        orderList.addAll(newOrderList)
        notifyDataSetChanged()
    }

    fun updateProductMap(newProductMap: Map<Int, ProductWithCommentResponse>) {
        productMap = newProductMap
        notifyDataSetChanged()
    }
}
