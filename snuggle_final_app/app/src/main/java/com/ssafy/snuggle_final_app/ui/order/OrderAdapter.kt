package com.ssafy.snuggle_final_app.ui.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Cart
import com.ssafy.snuggle_final_app.data.model.response.OrderResponse
import com.ssafy.snuggle_final_app.main.MainActivityViewModel

class OrderAdapter(
    private val context: Context,
    private var shoppingList: MutableList<Cart>
) : BaseAdapter() {

    override fun getCount(): Int = shoppingList.size

    override fun getItem(position: Int): Any = shoppingList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_order_detail, parent, false)

        val cart = shoppingList[position]

        val productTitle = view.findViewById<TextView>(R.id.order_tv_product_name)
        val productDelivery = view.findViewById<TextView>(R.id.order_tv_product_delivery)
        val productCount = view.findViewById<TextView>(R.id.order_tv_product_count)
        val productPrice = view.findViewById<TextView>(R.id.order_tv_product_price)
        val productImage = view.findViewById<ImageView>(R.id.order_iv_product)

        productTitle.text = cart.title
        productDelivery.text = cart.deliveryDate
        productCount.text = cart.productCnt.toString()
        productPrice.text = "${cart.price}원"

        // 이미지 로드 (Glide를 사용하는 경우)
        Glide.with(context).load(cart.img).into(productImage)

        return view
    }

    // 데이터를 갱신하는 메서드 추가
    fun updateData(newShoppingList: List<Cart>) {
        shoppingList.clear()
        shoppingList.addAll(newShoppingList)
        notifyDataSetChanged()  // 어댑터 데이터 갱신
    }
}
