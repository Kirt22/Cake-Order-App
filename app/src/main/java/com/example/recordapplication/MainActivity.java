package com.example.recordapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button addRecordBtn, viewRecordBtn, editRecordBtn, deleteAllBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialiseViews();

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
        editRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditRecord.class);
                startActivity(intent);
            }
        });

        deleteAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelper DbObj = new databaseHelper(MainActivity.this);
                DbObj.deleteAllData();
            }
        });
    }

    private void initialiseViews() {
        addRecordBtn = findViewById(R.id.addRecordBtn);
        viewRecordBtn = findViewById(R.id.viewRecordBtn);
        editRecordBtn = findViewById(R.id.editRecordBtn);
        deleteAllBtn= findViewById(R.id.deleteAllBtn);
    }
}

