package com.ssafy.snuggle_mobile.main;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssafy.snuggle_mobile.R;

import java.util.List;

public class BestProductRecyclerViewAdapter extends RecyclerView.Adapter<BestProductRecyclerViewAdapter.BestProductViewHolder> {

    private List<BestProduct> itemList;

    public BestProductRecyclerViewAdapter(List<BestProduct> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public BestProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BestProductRecyclerViewAdapter.BestProductViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class BestProductViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemName;
        TextView itemPrice;

        public BestProductViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
        }
    }
}
