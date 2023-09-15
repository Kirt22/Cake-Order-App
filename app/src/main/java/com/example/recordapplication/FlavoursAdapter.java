package com.example.recordapplication;

import android.content.ContentValues;
import android.content.Context;
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
    FlavourRecyclerViewInterface flavourRecyclerViewInterface;

    public FlavoursAdapter(ArrayList<flavoursModelClass> newFlavour) {
        this.newFlavour = newFlavour;
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
        flavoursModelClass flavoursModelClassObj = newFlavour.get(position);

        holder.flavourCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flavourRecyclerViewInterface.itemClicked(flavoursModelClassObj);
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
}
