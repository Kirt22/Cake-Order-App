package com.example.recordapplication.ViewRecordFiles;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recordapplication.R;

import java.util.ArrayList;

public class parentRvAdapter extends RecyclerView.Adapter<parentRvAdapter.myViewHolder> {

    ArrayList<parentRvModelClass> parentRvModelClassArrayList;
    Context context;
    SubRecyclerViewInterface subRecyclerViewInterface;

    public parentRvAdapter(ArrayList<parentRvModelClass> parentRvModelClassArrayList, Context context, SubRecyclerViewInterface subRecyclerViewInterface) {
        this.parentRvModelClassArrayList = parentRvModelClassArrayList;
        this.context = context;
        this.subRecyclerViewInterface = subRecyclerViewInterface;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("parentAdapter", "onCreateViewHolder: invoked");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_rv_itemlist, parent, false);
        return new parentRvAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Log.i("parentAdapter", "onBindViewHolder: invoked");
        parentRvModelClass parentItem = parentRvModelClassArrayList.get(position);
        holder.parent_rv_tv2.setText(parentItem.getDate());

        ArrayList<subRvModelClass> subData = parentItem.getSubRvModelClassArrayList();
        if(subData.get(0).getCustomerName() == null) {
            Log.d("parentAdapter", "onBindViewHolder: null case entered");
            holder.noOrderMsgTxt.setVisibility(View.VISIBLE);
            holder.sub_rv.setVisibility(View.INVISIBLE);
        } else {
            holder.noOrderMsgTxt.setVisibility(View.INVISIBLE);
            Log.d("parentAdapter", "onBindViewHolder: valid case entered");
            holder.sub_rv.setVisibility(View.VISIBLE);
            subRvAdapter childAdapter = new subRvAdapter(subData, context, subRecyclerViewInterface);
            holder.sub_rv.setLayoutManager(new LinearLayoutManager(context));
            holder.sub_rv.setAdapter(childAdapter);
        }
    }

    public void refreshAdapter(ArrayList<parentRvModelClass> newArrayList) {
        Log.d("parentAdapter", "refreshAdapter: Entered");
        parentRvModelClassArrayList.clear();
        parentRvModelClassArrayList = newArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return parentRvModelClassArrayList.size();
    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        TextView parent_rv_tv2, noOrderMsgTxt;
        RecyclerView sub_rv;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            parent_rv_tv2 = itemView.findViewById(R.id.parent_rv_tv2);
            sub_rv = itemView.findViewById(R.id.sub_rv);
            noOrderMsgTxt = itemView.findViewById(R.id.noOrderMsgTxt);
        }
    }

}
