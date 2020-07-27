package com.woupyie.thrifty.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.woupyie.thrifty.Interface.ItemClickListener;
import com.woupyie.thrifty.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //access product item layout

    public TextView txtProductName, txtProductDescription, txtPrice;
    public ImageView imageView;
    public  ItemClickListener listener;

    public ProductViewHolder(View itemView) {
        super(itemView);

        //create link between layout and imageView
        imageView =itemView.findViewById(R.id.product_image);
        txtProductName = itemView.findViewById(R.id.product_name);
        txtProductDescription = itemView.findViewById(R.id.product_description);
        txtPrice = itemView.findViewById(R.id.product_price);
    }

    public  void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick (View view){

        listener.onClick(view, getAdapterPosition(),false);

    }

}
