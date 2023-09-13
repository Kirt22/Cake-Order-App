package com.example.recordapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class subRvAdapter extends RecyclerView.Adapter<subRvAdapter.myViewHolder> {

    ArrayList<subRvModelClass> subRvModelClassArrayList;
    Context context;
    SubRecyclerViewInterface subRecyclerViewInterface;

    public subRvAdapter(ArrayList<subRvModelClass> subRvModelClassArrayList, Context context, SubRecyclerViewInterface subRecyclerViewInterface) {
        this.subRvModelClassArrayList = subRvModelClassArrayList;
        this.context = context;
        this.subRecyclerViewInterface = subRecyclerViewInterface;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_rv_itemlist, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

        String bakeryType = subRvModelClassArrayList.get(position).getBakeryType();
        if(bakeryType.equals("Cake")) {
            holder.SubRvCusNameInp.setText(subRvModelClassArrayList.get(position).getCustomerName());
            holder.SubRvCakeFlaInp.setText(subRvModelClassArrayList.get(position).getCakeFlavour());
            holder.SubRvDelDateInp.setText(subRvModelClassArrayList.get(position).getDeliveryDate());
            holder.SubRvDelTimeInp.setText(subRvModelClassArrayList.get(position).getDeliveryTime());
            holder.subRvOrderIdInp.setText(String.valueOf(subRvModelClassArrayList.get(position).getOrderId()));
            holder.SubRvBakeryTypeInp.setText(subRvModelClassArrayList.get(position).getBakeryType());
        } else if (bakeryType.equals("Cheese Cake")) {
            holder.subRvCakeFla.setText("Cheese Cake Flavour:");
            holder.SubRvCusNameInp.setText(subRvModelClassArrayList.get(position).getCustomerName());
            holder.SubRvCakeFlaInp.setText(subRvModelClassArrayList.get(position).getCakeFlavour());
            holder.SubRvDelDateInp.setText(subRvModelClassArrayList.get(position).getDeliveryDate());
            holder.SubRvDelTimeInp.setText(subRvModelClassArrayList.get(position).getDeliveryTime());
            holder.subRvOrderIdInp.setText(String.valueOf(subRvModelClassArrayList.get(position).getOrderId()));
            holder.SubRvBakeryTypeInp.setText(subRvModelClassArrayList.get(position).getBakeryType());
        } else if(bakeryType.equals("Cup Cake")) {
            holder.subRvCakeFla.setText("Cup Cake Flavour:");
            holder.SubRvCusNameInp.setText(subRvModelClassArrayList.get(position).getCustomerName());
            holder.SubRvCakeFlaInp.setText(subRvModelClassArrayList.get(position).getCakeFlavour());
            holder.SubRvDelDateInp.setText(subRvModelClassArrayList.get(position).getDeliveryDate());
            holder.SubRvDelTimeInp.setText(subRvModelClassArrayList.get(position).getDeliveryTime());
            holder.subRvOrderIdInp.setText(String.valueOf(subRvModelClassArrayList.get(position).getOrderId()));
            holder.SubRvBakeryTypeInp.setText(subRvModelClassArrayList.get(position).getBakeryType());
        } else {

        }


        holder.subRvCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subRecyclerViewInterface.itemClicked(subRvModelClassArrayList.get(holder.getAdapterPosition()));
                }
            });
    }

    @Override
    public int getItemCount() {
        return subRvModelClassArrayList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        TextView SubRvCusNameInp, SubRvCakeFlaInp, SubRvDelDateInp, SubRvDelTimeInp, subRvOrderIdInp, SubRvBakeryTypeInp, subRvCakeFla;
        CardView subRvCardView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            SubRvCusNameInp = itemView.findViewById(R.id.SubRvCusNameInp);
            SubRvCakeFlaInp = itemView.findViewById(R.id.SubRvCakeFlaInp);
            SubRvDelDateInp = itemView.findViewById(R.id.SubRvDelDateInp);
            SubRvDelTimeInp = itemView.findViewById(R.id.SubRvDelTimeInp);
            subRvCardView = itemView.findViewById(R.id.subRvCardView);
            subRvOrderIdInp = itemView.findViewById(R.id.subRvOrderIdInp);
            SubRvBakeryTypeInp = itemView.findViewById(R.id.SubRvBakeryTypeInp);
            subRvCakeFla = itemView.findViewById(R.id.subRvCakeFla);
        }
    }
}
