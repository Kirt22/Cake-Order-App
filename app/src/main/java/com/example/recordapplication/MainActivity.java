package com.example.recordapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.recordapplication.AddRecordFiles.AddRecord;
import com.example.recordapplication.FlavourListOperationsFiles.FlavourListOperations;
import com.example.recordapplication.ViewRecordFiles.ViewRecord;

public class MainActivity extends AppCompatActivity {

    private Button addRecordBtn, viewRecordBtn, flavourListBtn, viewAllRecordsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseViews(); // def name_function() {  }

        // On clicking the Add record button
        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRecord.class);
                startActivity(intent);
            }
        });
        // On clicking the View record button
        viewRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewRecord.class);
                startActivity(intent);
            }
        });
        // On clicking the edit record button
        flavourListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FlavourListOperations.class);
                startActivity(intent);
            }
        });

        viewAllRecordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowAllRecords.class);
                startActivity(intent);
            }
        });

    }

    private void initialiseViews() {
        addRecordBtn = findViewById(R.id.addRecordBtn);
        viewRecordBtn = findViewById(R.id.viewRecordBtn);
        flavourListBtn = findViewById(R.id.flavourListBtn);
        viewAllRecordsBtn = findViewById(R.id.viewAllRecordsBtn);
    }
}

