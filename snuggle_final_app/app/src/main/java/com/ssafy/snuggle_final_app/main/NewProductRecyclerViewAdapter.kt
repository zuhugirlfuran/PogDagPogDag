package com.ssafy.snuggle_final_app.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.snuggle_final_app.R
import com.ssafy.snuggle_mobile.main.NewProduct

class NewProductRecyclerViewAdapter(
    private val itemList: MutableList<NewProduct>
) : RecyclerView.Adapter<NewProductRecyclerViewAdapter.BestProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_horizontal_recycler_item, parent, false)
        return BestProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: BestProductViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemImage.setImageResource(item.imageResId)
        holder.itemName.text = item.name
        holder.itemPrice.text = item.price
    }

    override fun getItemCount(): Int = itemList.size

    class BestProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        val itemName: TextView = itemView.findViewById(R.id.item_name)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
    }
}
