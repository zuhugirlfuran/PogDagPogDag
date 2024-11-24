import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Order
import com.ssafy.snuggle_final_app.data.model.response.ProductWithCommentResponse
import com.ssafy.snuggle_final_app.util.CommonUtils.makeComma

class OrderListAdapter(
    private val dataList: MutableList<Order>,
    private val productInfoLiveData: LiveData<Map<Int, ProductWithCommentResponse>>,
    private val lifecycleOwner: LifecycleOwner,
    private val onProductRequest: (Int) -> Unit,
    private val onOrderClick: (Order) -> Unit // 클릭 리스너 추가
) : BaseAdapter() {

    override fun getCount(): Int = dataList.size

    override fun getItem(position: Int): Order = dataList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_orderlist, parent, false)

        val img = view.findViewById<ImageView>(R.id.orderlist_iv_img)
        val titleTextView = view.findViewById<TextView>(R.id.orderlist_tv_order_title)
        val deliverTextView = view.findViewById<TextView>(R.id.orderlist_tv_delivery)
        val priceTextView = view.findViewById<TextView>(R.id.orderlist_tv_price)

        val order = getItem(position)

        // 주문 제목
        val firstProductId = order.details.firstOrNull()?.productId
        if (firstProductId != null) {
            onProductRequest(firstProductId)
            productInfoLiveData.observe(lifecycleOwner) { productMap ->
                val productInfo = productMap[firstProductId]
                val productSize = order.details.size

                // Glide를 사용하여 이미지 로드
                Glide.with(img.context)
                    .load(productInfo?.productImg)
//                    .placeholder(R.drawable.placeholder_image) // 로딩 중 이미지
//                    .error(R.drawable.error_image) // 로드 실패 시 이미지
                    .into(img)

                if (productSize == 1) {
                    titleTextView.text = productInfo?.productName ?: "상품 정보 없음"
                } else {
                    titleTextView.text = "${productInfo?.productName} 외 ${productSize - 1}건"
                }

            }
        } else {
            titleTextView.text = "상품 정보 없음"
        }

        // 배송 상태
        val isCompleted = order.details.all { it.completed == "Y" }
        deliverTextView.text = if (isCompleted) "배송 완료" else "배송중"

        // 총 가격
        priceTextView.text = makeComma(order.totalPrice.toInt())


        // 클릭 이벤트
        view.setOnClickListener {
            onOrderClick(order)
        }

        return view
    }

    fun updateData(newData: List<Order>) {
        dataList.clear()
        dataList.addAll(newData)
        notifyDataSetChanged()
    }
}
