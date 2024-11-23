import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Cart
import com.ssafy.snuggle_final_app.util.CommonUtils.makeComma
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CartAdapter(
    private val context: Context, // 컨텍스트를 명시적으로 전달받음
    private var shoppingList: MutableList<Cart>,
    private val onDeleteClickListener: (Int) -> Unit,
    private val onQuantityChange: (List<Cart>) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = shoppingList.size

    override fun getItem(position: Int): Any = shoppingList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            // 새 뷰 생성
            view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            // 재사용 가능한 뷰를 가져옴
            view = convertView
            holder = view.tag as ViewHolder
        }

        val cart = shoppingList[position]

        // 현재 날짜에서 5일 후 계산
        val currentDate = LocalDate.now()
        val deliveryDate = currentDate.plusDays(5).format(DateTimeFormatter.ofPattern("MM.dd"))
        cart.deliveryDate = "${deliveryDate}일이내 배송 예정"

        // 데이터 바인딩
        holder.productTitle.text = cart.title
        holder.productDelivery.text = cart.deliveryDate
        holder.productCount.text = cart.productCnt.toString()
        holder.productPrice.text = makeComma(cart.price * cart.productCnt)

        // 이미지 로드
        Glide.with(context).load(cart.img).into(holder.productImage)

        // 삭제 버튼 클릭 리스너
        holder.deleteButton.setOnClickListener {
            onDeleteClickListener(position) // 삭제 콜백 호출
        }

        // 상품 개수 -1
        holder.minusButton.setOnClickListener {
            if (cart.productCnt > 1) { // 최소 수량 제한
                cart.productCnt -= 1
                holder.productCount.text = cart.productCnt.toString()
                holder.productPrice.text = makeComma(cart.price * cart.productCnt)
                onQuantityChange(shoppingList) // 콜백 호출
                notifyDataSetChanged() // 데이터 갱신
            } else {
                Toast.makeText(context, "최소 수량은 1개입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        // 상품 개수 +1
        holder.plusButton.setOnClickListener {
            cart.productCnt += 1
            holder.productCount.text = cart.productCnt.toString()
            holder.productPrice.text = makeComma(cart.price * cart.productCnt)
            onQuantityChange(shoppingList) // 콜백 호출
            notifyDataSetChanged() // 데이터 갱신
        }

        return view
    }

    // ViewHolder 클래스 정의
    private class ViewHolder(view: View) {
        val productTitle: TextView = view.findViewById(R.id.cart_tv_product_title)
        val productDelivery: TextView = view.findViewById(R.id.cart_tv_delivery)
        val productCount: TextView = view.findViewById(R.id.cart_tv_product_count)
        val productPrice: TextView = view.findViewById(R.id.cart_tv_price)
        val productImage: ImageView = view.findViewById(R.id.cart_iv_product)
        val deleteButton: ImageButton = view.findViewById(R.id.cart_ib_delete)
        val minusButton: ImageButton = view.findViewById(R.id.cart_btn_minus)
        val plusButton: ImageButton = view.findViewById(R.id.cart_btn_plus)
    }

    // 데이터 업데이트 메서드
    fun updateData(newShoppingList: List<Cart>) {
        shoppingList.clear()
        shoppingList.addAll(newShoppingList)
        notifyDataSetChanged()
    }
}
