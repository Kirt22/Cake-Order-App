package com.example.recordapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FlavourListOperations extends AppCompatActivity implements FlavourRecyclerViewInterface ,AdapterView.OnItemSelectedListener, FlavourDialog.FlavourDialogListener {

    private Spinner flavourSelectorSpinner;
    private TextView nullFlavourMsg;
    private RecyclerView flavourListRv;
    private FloatingActionButton addFlavourBtn;
    private String bakeryType = "Cake";
    private ArrayList<flavoursModelClass> cakeFlavourList, cheeseCakeFlavourList, cupCakeFlavourList;
    private FlavoursAdapter flavoursAdapter;
    //FlavourRecyclerViewInterface flavourRecyclerViewInterface;

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
            cakeFlavourList = generateFlavourList(bakeryType);
            if(cakeFlavourList != null) {
                flavoursAdapter = new FlavoursAdapter(cakeFlavourList, "Cake", this);
                flavourListRv.setAdapter(flavoursAdapter);
            } else {
                nullFlavourMsg.setVisibility(View.VISIBLE);
            }
        } else if(bakeryType.equals("Cheese Cake")) {
            cheeseCakeFlavourList = generateFlavourList(bakeryType);
            if(cheeseCakeFlavourList != null) {
                flavoursAdapter = new FlavoursAdapter(cheeseCakeFlavourList, "Cheese Cake", this);
                flavourListRv.setAdapter(flavoursAdapter);
            } else {
                nullFlavourMsg.setVisibility(View.VISIBLE);
            }
        } else if(bakeryType.equals("Cup Cake")) {
            cupCakeFlavourList = generateFlavourList(bakeryType);
            if(cupCakeFlavourList != null) {
                flavoursAdapter = new FlavoursAdapter(cupCakeFlavourList, "Cup Cake", this);
                flavourListRv.setAdapter(flavoursAdapter);
            } else {
                nullFlavourMsg.setVisibility(View.VISIBLE);
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
        nullFlavourMsg = findViewById(R.id.nullFlavourMsg);
    }

    private void openDialog() {
        FlavourDialog flavourDialog = new FlavourDialog();
        flavourDialog.show(getSupportFragmentManager(), "Add Flavour Dialog");
    }

    private ArrayList<flavoursModelClass> generateFlavourList(String bakeryType) {
        databaseHelper dbObj = new databaseHelper(FlavourListOperations.this);

        ArrayList<flavoursModelClass> flavourList;
        if(bakeryType.equals("Cake")) {
            flavourList = dbObj.getCakeItems();
            return flavourList;
        } else if(bakeryType.equals("Cheese Cake")) {
            flavourList = dbObj.getCheeseCakeItems();
            return flavourList;
        } else if(bakeryType.equals("Cup Cake")) {
            flavourList = dbObj.getCupCakeItems();
            return flavourList;
        } else {
            Log.d("FlavourListOperations", "generateFlavourList: Error!");
        }

        return null;
    }

    // code for selecting from Add button
    @Override
    public void applyText(String newFlavour) {
        databaseHelper dbObj = new databaseHelper(this);
        boolean success;
        ArrayList<flavoursModelClass> newFlavourList = new ArrayList<>();

        if(bakeryType.equals("Cake")) {
            success = dbObj.insertCakeFlavour(newFlavour);
            if(success == true) {
                newFlavourList = generateFlavourList("Cake");
                if (flavoursAdapter == null) {
                    flavoursAdapter = new FlavoursAdapter(newFlavourList, "Cake", this);
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
                newFlavourList = generateFlavourList("Cheese Cake");
                if (flavoursAdapter == null) {
                    flavoursAdapter = new FlavoursAdapter(newFlavourList, "Cheese Cake", this);
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
                newFlavourList = generateFlavourList("Cup Cake");
                if (flavoursAdapter == null) {
                    flavoursAdapter = new FlavoursAdapter(newFlavourList, "Cup Cake", this);
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


    // Code for selecting from spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String valFromSpinner = parent.getItemAtPosition(position).toString();
        bakeryType = valFromSpinner;
        nullFlavourMsg.setVisibility(View.INVISIBLE);
        flavourListRv.setVisibility(View.INVISIBLE);
        if(bakeryType.equals("Cake")) {
            cakeFlavourList = generateFlavourList(bakeryType);
            if(cakeFlavourList != null) {
                flavourListRv.setVisibility(View.VISIBLE);
                flavoursAdapter = new FlavoursAdapter(cakeFlavourList, "Cake", this);
                flavourListRv.setAdapter(flavoursAdapter);
            } else {
                nullFlavourMsg.setVisibility(View.VISIBLE);
            }
        } else if(bakeryType.equals("Cheese Cake")) {
            cheeseCakeFlavourList = generateFlavourList(bakeryType);
            if(cheeseCakeFlavourList != null) {
                flavourListRv.setVisibility(View.VISIBLE);
                flavoursAdapter = new FlavoursAdapter(cheeseCakeFlavourList, "Cheese Cake", this);
                flavourListRv.setAdapter(flavoursAdapter);
            } else {
                nullFlavourMsg.setVisibility(View.VISIBLE);
            }
        } else if(bakeryType.equals("Cup Cake")) {
            cupCakeFlavourList = generateFlavourList(bakeryType);
            if(cupCakeFlavourList != null) {
                flavourListRv.setVisibility(View.VISIBLE);
                flavoursAdapter = new FlavoursAdapter(cupCakeFlavourList, "Cup Cake", this);
                flavourListRv.setAdapter(flavoursAdapter);
            } else  {
                nullFlavourMsg.setVisibility(View.VISIBLE);
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
    public void itemClicked(int id, String bakeryType) {
        Intent intent = new Intent(FlavourListOperations.this, flavourOperations.class);
        intent.putExtra("FlavourId", id);
        intent.putExtra("BakeryType", bakeryType);
        Log.d("FlavourListOperations", "itemClicked: intent put extra id-" + id);
        Log.d("FlavourListOperations", "itemClicked: intent put extra bakeryType-" + bakeryType);
        startActivity(intent);
    }
}