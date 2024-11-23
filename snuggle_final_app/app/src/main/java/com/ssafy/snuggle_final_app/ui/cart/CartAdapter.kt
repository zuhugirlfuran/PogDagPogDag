package com.ssafy.snuggle_final_app.ui.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Cart

class CartAdapter(
    private val context: Context,
    private var shoppingList: MutableList<Cart>,
    private val onDeleteClickListener: (Int) -> Unit // 추가
) : BaseAdapter() {

    override fun getCount(): Int = shoppingList.size

    override fun getItem(position: Int): Any = shoppingList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)

        val cart = shoppingList[position]

        val productTitle = view.findViewById<TextView>(R.id.cart_tv_product_title)
        val productDelivery = view.findViewById<TextView>(R.id.cart_tv_delivery)
        val productCount = view.findViewById<TextView>(R.id.cart_tv_product_count)
        val productPrice = view.findViewById<TextView>(R.id.cart_tv_price)
        val productImage = view.findViewById<ImageView>(R.id.cart_iv_product)
        val deleteButton = view.findViewById<ImageView>(R.id.cart_ib_delete) // 삭제 버튼

        productTitle.text = cart.title
        productDelivery.text = cart.deliveryDate
        productCount.text = cart.productCnt.toString()
        productPrice.text = "${cart.price}원"

        // 이미지 로드 (Glide를 사용하는 경우)
        Glide.with(context).load(cart.img).into(productImage)

        // 삭제 버튼 클릭 리스너 추가
        deleteButton.setOnClickListener {
            onDeleteClickListener(position) // 아이템 위치를 상위에 전달
        }

        return view
    }

    fun updateData(newShoppingList: List<Cart>) {
        shoppingList.clear()
        shoppingList.addAll(newShoppingList)
        notifyDataSetChanged()
    }
}
