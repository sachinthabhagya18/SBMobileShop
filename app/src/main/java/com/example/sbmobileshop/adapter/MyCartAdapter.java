package com.example.sbmobileshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sbmobileshop.R;
import com.example.sbmobileshop.model.MyCartModel;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> cartModelList;

    public MyCartAdapter(Context context,List<MyCartModel> cartModelList){
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.totalPrice.setText(cartModelList.get(position).getTotalPrice());
//        holder.price.setText(cartModelList.get(position).getProductPrice());
//        holder.quentity.setText(cartModelList.get(position).getTotalQuntity());
//        holder.totalPrice.setText(String.valueOf(cartModelList.get(position).getTotalPrice()));
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,price,quentity,totalPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
//            quentity = itemView.findViewById(R.id.quantity);
//            totalPrice = itemView.findViewById(R.id.subtotal);

        }
    }
}
