package com.example.recordapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ViewRecord extends AppCompatActivity implements SubRecyclerViewInterface{

    private RecyclerView parentRecyclerView;
    private FloatingActionButton searchBtn;
    ArrayList<parentRvModelClass> parentRvModelClassArrayList;
    ArrayList<subRvModelClass> subRvModelClassArrayList;
    GenerateRequiredArrayList genArrayObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        Log.d("ViewRecord", "onCreate: Entered");

        intiViews();

        parentRvModelClassArrayList = new ArrayList<>();
        subRvModelClassArrayList = new ArrayList<>();

        genArrayObj = new GenerateRequiredArrayList();

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