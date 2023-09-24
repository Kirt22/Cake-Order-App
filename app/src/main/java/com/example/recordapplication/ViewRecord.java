package com.example.recordapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewRecord extends AppCompatActivity implements SubRecyclerViewInterface{

    private RecyclerView parentRecyclerView;
    private FloatingActionButton searchBtn;
    ArrayList<parentRvModelClass> parentRvModelClassArrayList;
    ArrayList<subRvModelClass> subRvModelClassArrayList;
    GenerateRequiredOrdersArrayList genArrayObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        Log.d("ViewRecord", "onCreate: Entered");

        intiViews();

        parentRvModelClassArrayList = new ArrayList<>();
        subRvModelClassArrayList = new ArrayList<>();

        genArrayObj = new GenerateRequiredOrdersArrayList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        parentRecyclerView.setLayoutManager(linearLayoutManager);

        parentRvModelClassArrayList = genArrayObj.generateArrayList(ViewRecord.this);

        parentRvAdapter parentAdapter = new parentRvAdapter(parentRvModelClassArrayList, ViewRecord.this, this);
        parentRecyclerView.setAdapter(parentAdapter);

        // Scrolling to the current date
        String currDate = getCurrDate();
        int currIndx = findPosByDate(currDate);
        parentRecyclerView.scrollToPosition(currIndx);

        

    }

    private int findPosByDate(String currDate) {
        for (int i = 0; i < parentRvModelClassArrayList.size(); i++) {
            if (parentRvModelClassArrayList.get(i).getDate().equals(currDate)) {
                return i;
            }
        }
        return -1;
    }

    private String getCurrDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = Calendar.getInstance().getTime();
        return dateFormat.format(currentDate);
    }

    private void intiViews() {
        parentRecyclerView = findViewById(R.id.parentRecyclerView);
        searchBtn = findViewById(R.id.searchBtn);
    }

    @Override
    public void itemClicked(subRvModelClass subRvModelClass) {
        Intent intent = new Intent(ViewRecord.this, ShowFullDetails.class);
        intent.putExtra("OrderId", subRvModelClass.getOrderId());
        startActivity(intent);
    }
}