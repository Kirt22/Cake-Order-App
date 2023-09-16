package com.example.recordapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class flavourOperations extends AppCompatActivity {

    TextView BakeryType;
    TextInputLayout flavour;
    Button flavourUpdateBtn, deleteBtn;
    databaseHelper dbObj = new databaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flavour_operations);

        initialiseViews();

        Intent intent = getIntent();
        int Id = intent.getIntExtra("FlavourId", 0);
        String bakeryType = intent.getStringExtra("BakeryType");

        Log.d("flavoursOperations", "onCreate: FlavourId-" + Id);
        Log.d("flavoursOperations", "onCreate: bakeryType-" + bakeryType);

        Cursor cursor = dbObj.getFlavourForIdAndBakeryType(Id, bakeryType);
        cursor.moveToFirst();
        if(cursor != null) {
            EditText flavourName = flavour.getEditText();
            flavourName.setText(cursor.getString(1));
            BakeryType.setText(bakeryType);
        } else {
            Log.d("flavoursOperations", "onCreate: null db Obj");
        }

        flavour.getEditText().setEnabled(false);

        flavourUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flavour.getEditText().setEnabled(true);
                flavour.setEndIconDrawable(R.drawable.baseline_sync_24);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = dbObj.deleteFlavour(Id, bakeryType);
                if(success == true) {
                    Toast.makeText(flavourOperations.this, "Flavour Deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(flavourOperations.this, "Failed To Delete", Toast.LENGTH_SHORT).show();
                }
            }
        });

        flavour.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = dbObj.updateFlavour(Id, bakeryType, flavour.getEditText().getText().toString());
                if(success == true) {
                    Toast.makeText(flavourOperations.this, "Flavour Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(flavourOperations.this, "Failed To Update!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        

    }

    private void initialiseViews() {
        BakeryType = findViewById(R.id.bakeryType);
        flavour = findViewById(R.id.flavour);
        flavourUpdateBtn = findViewById(R.id.flavourUpdateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
    }
}