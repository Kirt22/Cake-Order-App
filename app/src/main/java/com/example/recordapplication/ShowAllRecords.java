package com.example.recordapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.recordapplication.ViewRecordFiles.GenerateRequiredOrdersArrayList;
import com.example.recordapplication.ViewRecordFiles.SubRecyclerViewInterface;
import com.example.recordapplication.ViewRecordFiles.ViewRecord;
import com.example.recordapplication.ViewRecordFiles.parentRvAdapter;
import com.example.recordapplication.ViewRecordFiles.parentRvModelClass;
import com.example.recordapplication.ViewRecordFiles.subRvModelClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ShowAllRecords extends AppCompatActivity implements SubRecyclerViewInterface {

    RecyclerView allRecordsParentRv;
    ArrayList<parentRvModelClass> parentRvModelClassArrayList;
    ArrayList<subRvModelClass> subRvModelClassArrayList;
    GenerateRequiredOrdersArrayList genArrayObj;
    parentRvAdapter parentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_records);

        intiViews();

        parentRvModelClassArrayList = new ArrayList<>();
        subRvModelClassArrayList = new ArrayList<>();

        genArrayObj = new GenerateRequiredOrdersArrayList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        allRecordsParentRv.setLayoutManager(linearLayoutManager);

        parentRvModelClassArrayList = genArrayObj.generateArrayListForViewAllRecords(ShowAllRecords.this);

        parentAdapter = new parentRvAdapter(parentRvModelClassArrayList, ShowAllRecords.this, this);
        allRecordsParentRv.setAdapter(parentAdapter);

        // Scrolling to the current date
        String currDate = getCurrDate();
        int currIndx = findPosByDate(currDate);
        allRecordsParentRv.scrollToPosition(currIndx);
    }

    private void intiViews() {
        allRecordsParentRv = findViewById(R.id.allRecordsParentRv);
    }

    private String getCurrDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = Calendar.getInstance().getTime();
        return dateFormat.format(currentDate);
    }

    private int findPosByDate(String currDate) {
        for (int i = 0; i < parentRvModelClassArrayList.size(); i++) {
            if (parentRvModelClassArrayList.get(i).getDate().equals(currDate)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void itemClicked(subRvModelClass subRvModelClass) {

    }
}