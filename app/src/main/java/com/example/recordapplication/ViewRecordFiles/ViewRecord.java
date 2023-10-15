package com.example.recordapplication.ViewRecordFiles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.example.recordapplication.R;
import com.example.recordapplication.ViewRecordFiles.ShowFullDetailsFiles.ShowFullDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ViewRecord extends AppCompatActivity implements SubRecyclerViewInterface, DatePickerDialog.OnDateSetListener {

    private RecyclerView parentRecyclerView;
    private FloatingActionButton searchBtn;
    ArrayList<parentRvModelClass> parentRvModelClassArrayList;
    ArrayList<subRvModelClass> subRvModelClassArrayList;
    GenerateRequiredOrdersArrayList genArrayObj;
    parentRvAdapter parentAdapter;
    String selectedDate;

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                ArrayList<parentRvModelClass> newParentArrayList = genArrayObj.generateArrayListForSearchDate(ViewRecord.this, selectedDate);
                parentAdapter.refreshAdapter(newParentArrayList);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_record);
        Log.d("ViewRecord", "onCreate: Entered");

        intiViews();

        selectedDate = getCurrDate();

        parentRvModelClassArrayList = new ArrayList<>();
        subRvModelClassArrayList = new ArrayList<>();

        genArrayObj = new GenerateRequiredOrdersArrayList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        parentRecyclerView.setLayoutManager(linearLayoutManager);

        parentRvModelClassArrayList = genArrayObj.generateArrayList(ViewRecord.this);

        parentAdapter = new parentRvAdapter(parentRvModelClassArrayList, ViewRecord.this, this);
        parentRecyclerView.setAdapter(parentAdapter);

        // Scrolling to the current date
        String currDate = getCurrDate();
        int currIndx = findPosByDate(currDate);
        parentRecyclerView.scrollToPosition(currIndx);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

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
        startForResult.launch(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        selectedDate = dateFormat.format(c.getTime());
        Log.d("ViewRecord", "onDateSet: selected Date-" + selectedDate);

        ArrayList<parentRvModelClass> newParentArrayList = genArrayObj.generateArrayListForSearchDate(ViewRecord.this, selectedDate);
        parentAdapter.refreshAdapter(newParentArrayList);
    }
}