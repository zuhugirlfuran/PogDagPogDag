package com.ssafy.snuggle_final_app.cart

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_final_app.data.model.dto.Cart

class CartAdapter(private val context: Context, private val dataList: List<Cart>) :

    BaseAdapter() {
    override fun getCount(): Int = dataList.size

    override fun getItem(position: Int): Any = dataList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_cart, parent, false)


        val img = view.findViewById<ImageView>(R.id.cart_iv_product)
        val titleTextView = view.findViewById<TextView>(R.id.cart_tv_title)
        val deliverDateTextView = view.findViewById<TextView>(R.id.cart_tv_delivery)
        val priceTextView = view.findViewById<TextView>(R.id.cart_tv_price)

//        val item = dataList[position]
        val item = getItem(position) as Cart
        titleTextView?.text = item?.title
        deliverDateTextView?.text = item?.deliveryDate
        priceTextView?.text = item?.price
        img.setImageResource(R.drawable.item03)


        return view
    }
}