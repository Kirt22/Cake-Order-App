package com.example.recordapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FlavourListOperations extends AppCompatActivity implements FlavourRecyclerViewInterface ,AdapterView.OnItemSelectedListener, FlavourDialog.FlavourDialogListener {

    private Spinner flavourSelectorSpinner;
    private RecyclerView flavourListRv;
    private FloatingActionButton addFlavourBtn;
    private String bakeryType = "Cake";
    private String dialogFlavour;
    private ArrayList<flavoursModelClass> cakeFlavourList, cheeseCakeFlavourList, cupCakeFlavourList;
    FlavoursAdapter flavoursAdapter;
    FlavourRecyclerViewInterface flavourRecyclerViewInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flavour_list_operations);

        initialiseViews();

        String[] bakeryTypeList = getResources().getStringArray(R.array.Bakery_Type_Array);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bakeryTypeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flavourSelectorSpinner.setAdapter(adapter);
        flavourSelectorSpinner.setOnItemSelectedListener(this);

        Log.d("FlavourListOperations", "onCreate: bakeryType-" + bakeryType);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        flavourListRv.setLayoutManager(linearLayoutManager);

        if(bakeryType.equals("Cake")) {
            cakeFlavourList = generateFlavourList(cakeFlavourList, bakeryType);
            if(cakeFlavourList != null) {
                flavoursAdapter = new FlavoursAdapter(cakeFlavourList);
                flavourListRv.setAdapter(flavoursAdapter);
            } else {
                Log.d("FlavourListOperations", "onCreate: CakeFlavourList empty");
            }
        } else if(bakeryType.equals("Cheese Cake")) {
            cheeseCakeFlavourList = generateFlavourList(cheeseCakeFlavourList, bakeryType);
            if(cheeseCakeFlavourList != null) {
                flavoursAdapter = new FlavoursAdapter(cheeseCakeFlavourList);
                flavourListRv.setAdapter(flavoursAdapter);
            }
        } else if(bakeryType.equals("Cup Cake")) {
            cupCakeFlavourList = generateFlavourList(cupCakeFlavourList, bakeryType);
            if(cupCakeFlavourList != null) {
                flavoursAdapter = new FlavoursAdapter(cupCakeFlavourList);
                flavourListRv.setAdapter(flavoursAdapter);
            }
        } else {
            Log.d("FlavourListOperations", "onCreate: Error!");
        }


        addFlavourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    private void initialiseViews() {
        flavourListRv = findViewById(R.id.flavourListRv);
        flavourSelectorSpinner = findViewById(R.id.flavourSelectorSpinner);
        addFlavourBtn = findViewById(R.id.addFlavourBtn);
    }

    private void openDialog() {
        FlavourDialog flavourDialog = new FlavourDialog();
        flavourDialog.show(getSupportFragmentManager(), "Add Flavour Dialog");
    }

    private ArrayList<flavoursModelClass> generateFlavourList(ArrayList<flavoursModelClass> flavourList, String bakeryType) {
        databaseHelper dbObj = new databaseHelper(FlavourListOperations.this);

        if(bakeryType.equals("Cake")) {
            flavourList = dbObj.getCakeFlavour();
            return flavourList;
        } else if(bakeryType.equals("Cheese Cake")) {
            flavourList = dbObj.getCheeseCakeFlavour();
            return flavourList;
        } else if(bakeryType.equals("Cup Cake")) {
            flavourList = dbObj.getCupCakeFlavour();
            return flavourList;
        } else {
            Log.d("FlavourListOperations", "generateFlavourList: Error!");
        }

        return null;
    }

    @Override
    public void applyText(String newFlavour) {
        dialogFlavour = newFlavour;
        databaseHelper dbObj = new databaseHelper(this);
        boolean success;
        ArrayList<flavoursModelClass> newFlavourList = new ArrayList<>();

        if(bakeryType.equals("Cake")) {
            success = dbObj.insertCakeFlavour(newFlavour);
            if(success == true) {
                newFlavourList = generateFlavourList(newFlavourList, "Cake");
                if (flavoursAdapter == null) {
                    flavoursAdapter = new FlavoursAdapter(newFlavourList);
                    flavourListRv.setAdapter(flavoursAdapter);
                } else {
                    flavoursAdapter.notifyItemInserted(newFlavourList.size());
                }
            }else {
                Log.d("FlavourListOperations", "applyText: error");
            }
        } else if(bakeryType.equals("Cheese Cake")) {
            success = dbObj.insertCheeseCakeFlavour(newFlavour);
            if(success == true) {
                newFlavourList = generateFlavourList(newFlavourList, "Cheese Cake");
                if (flavoursAdapter == null) {
                    flavoursAdapter = new FlavoursAdapter(newFlavourList);
                    flavourListRv.setAdapter(flavoursAdapter);
                } else {
                    flavoursAdapter.notifyDataSetChanged();
                }
            } else {
                Log.d("FlavourListOperations", "applyText: error");
            }
        } else if(bakeryType.equals("Cup Cake")) {
            success = dbObj.insertCupCakeFlavour(newFlavour);
            if(success == true) {
                newFlavourList = generateFlavourList(newFlavourList, "Cup Cake");
                if (flavoursAdapter == null) {
                    flavoursAdapter = new FlavoursAdapter(newFlavourList);
                    flavourListRv.setAdapter(flavoursAdapter);
                } else {
                    flavoursAdapter.notifyDataSetChanged();
                }
            }
            flavoursAdapter.notifyDataSetChanged();
        } else {
            Log.d("FlavourListOperations", "applyText: Failed to add item");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String valFromSpinner = parent.getItemAtPosition(position).toString();
        bakeryType = valFromSpinner;

        if(bakeryType.equals("Cake")) {
            cakeFlavourList = generateFlavourList(cakeFlavourList, bakeryType);
            if(cakeFlavourList != null) {
                flavoursAdapter = new FlavoursAdapter(cakeFlavourList);
                flavourListRv.setAdapter(flavoursAdapter);
            } else {
                Log.d("FlavourListOperations", "onCreate: CakeFlavourList empty");
            }
        } else if(bakeryType.equals("Cheese Cake")) {
            cheeseCakeFlavourList = generateFlavourList(cheeseCakeFlavourList, bakeryType);
            if(cheeseCakeFlavourList != null) {
                flavoursAdapter = new FlavoursAdapter(cheeseCakeFlavourList);
                flavourListRv.setAdapter(flavoursAdapter);
            }
        } else if(bakeryType.equals("Cup Cake")) {
            cupCakeFlavourList = generateFlavourList(cupCakeFlavourList, bakeryType);
            if(cupCakeFlavourList != null) {
                flavoursAdapter = new FlavoursAdapter(cupCakeFlavourList);
                flavourListRv.setAdapter(flavoursAdapter);
            }
        } else {
            Log.d("FlavourListOperations", "onCreate: Error!");
        }

        Log.d("FlavourListOperations", "onItemSelected: bakeryItem-" + bakeryType);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void itemClicked(flavoursModelClass flavoursModelClassObj) {
        Intent intent = new Intent(FlavourListOperations.this, flavourOperations.class);

    }
}