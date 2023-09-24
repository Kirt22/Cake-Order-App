package com.example.recordapplication;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FlavoursAdapter extends RecyclerView.Adapter<FlavoursAdapter.myViewHolder> {
    ArrayList<flavoursModelClass> newFlavour;
    String bakeryType;
    FlavourRecyclerViewInterface flavourRecyclerViewInterface;

    public FlavoursAdapter(ArrayList<flavoursModelClass> newFlavour, String bakeryType, FlavourRecyclerViewInterface flavourRecyclerViewInterface) {
        this.newFlavour = newFlavour;
        this.bakeryType = bakeryType;
        this.flavourRecyclerViewInterface = flavourRecyclerViewInterface;
    }

    @NonNull
    @Override
    public FlavoursAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flavour_rv_itemlist, parent, false);
        return new FlavoursAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlavoursAdapter.myViewHolder holder, int position) {
        String flavour = newFlavour.get(position).getFlavour();
        holder.newFlavour.setText(flavour);
        int Id = newFlavour.get(position).getId();

        Log.d("FlavoursAdapter", "onBindViewHolder: flavour and id-" + flavour + Id );

        holder.flavourCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flavourRecyclerViewInterface.itemClicked(Id, bakeryType);
                Log.d("FlavoursAdapter", "onClick: Id in interface onClick-" + Id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newFlavour.size();
    }


    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView newFlavour;
        CardView flavourCardView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            flavourCardView = itemView.findViewById(R.id.flavourCardView);
            newFlavour = itemView.findViewById(R.id.newFlavour);
        }
    }

    public void adapterChange(ArrayList<flavoursModelClass> newFlavoursArrayList) {
        if(newFlavour != null) {
            newFlavour.clear();
            newFlavour = newFlavoursArrayList;
            notifyDataSetChanged();
        } else {
            Log.d("FlavoursAdapter", "adapterChange: null- entered");
            newFlavour = new ArrayList<>(newFlavoursArrayList);
            //notifyDataSetChanged();
            notifyItemInserted(0);
        }
    }

    public ArrayList<flavoursModelClass> getArrayList() {
        return newFlavour;
    }
}
