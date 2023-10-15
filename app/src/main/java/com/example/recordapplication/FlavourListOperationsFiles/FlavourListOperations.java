package com.example.recordapplication.FlavourListOperationsFiles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.example.recordapplication.R;
import com.example.recordapplication.DatabaseHelperFiles.databaseHelper;
import com.example.recordapplication.FlavourListOperationsFiles.FlavourOperationsFiles.flavourOperations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FlavourListOperations extends AppCompatActivity implements FlavourRecyclerViewInterface, AdapterView.OnItemSelectedListener, FlavourDialog.FlavourDialogListener {

    private Spinner flavourSelectorSpinner;
    private TextView nullFlavourMsg;
    private RecyclerView flavourListRv;
    private FloatingActionButton addFlavourBtn;
    private String bakery_Type = "Cake";
    private FlavoursAdapter cakeFlavoursAdapter, cheeseCakeFlavoursAdapter, cupCakeFlavoursAdapter;
    int count = 0;

    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Log.d("FlavourListOperations", "onActivityResult: entered");
            if (result.getResultCode() == RESULT_OK) {
                Log.d("FlavourListOperations", "onActivityResult: result code ok");

                if ("Cake".equals(result.getData().getStringExtra(flavourOperations.KEY))) {
                    Log.d("FlavourListOperations", "onActivityResult: cake refresh");
                    ArrayList<flavoursModelClass> newCakeArrayList = new ArrayList<>();
                    newCakeArrayList = generateFlavourList("Cake");
                    if(newCakeArrayList == null) {
                        Log.d("FlavourListOperations", "onActivityResult: cake refresh- null");
                        nullFlavourMsg.setVisibility(View.VISIBLE);
                        flavourListRv.setVisibility(View.INVISIBLE);
                    } else {
                        Log.d("FlavourListOperations", "onActivityResult: cake refresh- not null");
                        cakeFlavoursAdapter.adapterChange(newCakeArrayList);
                    }
                } else if ("Cheese Cake".equals(result.getData().getStringExtra(flavourOperations.KEY))) {
                    ArrayList<flavoursModelClass> newCheeseCakeArrayList = new ArrayList<>();
                    newCheeseCakeArrayList = generateFlavourList("Cheese Cake");
                    if(newCheeseCakeArrayList == null) {
                        Log.d("FlavourListOperations", "onActivityResult: cheese cake refresh- null");
                        nullFlavourMsg.setVisibility(View.VISIBLE);
                        flavourListRv.setVisibility(View.INVISIBLE);
                    } else {
                        Log.d("FlavourListOperations", "onActivityResult: cheese cake refresh- not null");
                        cheeseCakeFlavoursAdapter.adapterChange(newCheeseCakeArrayList);
                    }
                } else if ("Cup Cake".equals(result.getData().getStringExtra(flavourOperations.KEY))) {
                    ArrayList<flavoursModelClass> newCupCakeArrayList = new ArrayList<>();
                    newCupCakeArrayList = generateFlavourList("Cup Cake");
                    if(newCupCakeArrayList == null) {
                        nullFlavourMsg.setVisibility(View.VISIBLE);
                        flavourListRv.setVisibility(View.INVISIBLE);
                    } else {
                        cupCakeFlavoursAdapter.adapterChange(newCupCakeArrayList);
                    }
                } else if(result.getData().getDataString() == null){
                    Log.d("FlavourListOperations", "onActivityResult: null data");
                }
            } else {
                Log.d("FlavourListOperations", "onActivityResult: result code error");
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flavour_list_operations);

        initialiseViews();

        // Setting the spinner up
        String[] bakeryTypeList = getResources().getStringArray(R.array.Bakery_Type_Array);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bakeryTypeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flavourSelectorSpinner.setAdapter(adapter);
        flavourSelectorSpinner.setOnItemSelectedListener(this);

        Log.d("FlavourListOperations", "onCreate: bakeryType-" + bakery_Type);

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

    private int adapterSettings(int check, String bakeryType) {
        if (check == 0) {
            check++;
            Log.d("FlavourListOperations", "adapterSettings1: check-" + check);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            flavourListRv.setLayoutManager(linearLayoutManager);

            ArrayList<flavoursModelClass> cakeFlavourList = generateFlavourList(bakeryType);
            ArrayList<flavoursModelClass> cheeseCakeFlavourList = generateFlavourList("Cheese Cake");
            ArrayList<flavoursModelClass> cupCakeFlavourList = generateFlavourList("Cup Cake");

            ArrayList<flavoursModelClass> nonNullDummyList = new ArrayList<>();
            flavoursModelClass obj = new flavoursModelClass(-1, "Dummy Flavour");
            nonNullDummyList.add(obj);

            if (cakeFlavourList == null) {
                cakeFlavoursAdapter = new FlavoursAdapter(nonNullDummyList, "Cake", this);
            } else {
                cakeFlavoursAdapter = new FlavoursAdapter(cakeFlavourList, "Cake", this);
            }

            if (cheeseCakeFlavourList == null) {
                cheeseCakeFlavoursAdapter = new FlavoursAdapter(nonNullDummyList, "Cheese Cake", this);
            } else {
                cheeseCakeFlavoursAdapter = new FlavoursAdapter(cheeseCakeFlavourList, "Cheese Cake", this);
            }

            if (cupCakeFlavourList == null) {
                cupCakeFlavoursAdapter = new FlavoursAdapter(nonNullDummyList, "Cup Cake", this);
            } else {
                cupCakeFlavoursAdapter = new FlavoursAdapter(cupCakeFlavourList, "Cup Cake", this);
            }

            if (cakeFlavourList == null) {
                flavourListRv.setVisibility(View.INVISIBLE);
                nullFlavourMsg.setVisibility(View.VISIBLE);
                flavourListRv.setAdapter(cakeFlavoursAdapter);
            } else {
                flavourListRv.setAdapter(cakeFlavoursAdapter);
            }

            return check;
        } else if(check > 0){
            Log.d("FlavourListOperations", "adapterSettings2: check-" + check);
            if (bakeryType.equals("Cake")) {
                ArrayList<flavoursModelClass> cakeFlavourList = generateFlavourList(bakeryType);
                if (cakeFlavourList != null) {
                    flavourListRv.setVisibility(View.VISIBLE);
                    nullFlavourMsg.setVisibility(View.INVISIBLE);
                    flavourListRv.setAdapter(cakeFlavoursAdapter);
                } else {
                    flavourListRv.setVisibility(View.INVISIBLE);
                    flavourListRv.setAdapter(cakeFlavoursAdapter);
                    nullFlavourMsg.setVisibility(View.VISIBLE);
                }
            } else if (bakeryType.equals("Cheese Cake")) {
                ArrayList<flavoursModelClass> cheeseCakeFlavourList = generateFlavourList("Cheese Cake");
                if (cheeseCakeFlavourList != null) {
                    flavourListRv.setVisibility(View.VISIBLE);
                    nullFlavourMsg.setVisibility(View.INVISIBLE);
                    flavourListRv.setAdapter(cheeseCakeFlavoursAdapter);
                } else {
                    flavourListRv.setVisibility(View.INVISIBLE);
                    flavourListRv.setAdapter(cheeseCakeFlavoursAdapter);
                    nullFlavourMsg.setVisibility(View.VISIBLE);
                }
            } else if (bakeryType.equals("Cup Cake")) {
                ArrayList<flavoursModelClass> cupCakeFlavourList = generateFlavourList(bakeryType);
                if (cupCakeFlavourList != null) {
                    flavourListRv.setVisibility(View.VISIBLE);
                    nullFlavourMsg.setVisibility(View.INVISIBLE);
                    flavourListRv.setAdapter(cupCakeFlavoursAdapter);
                } else {
                    flavourListRv.setVisibility(View.INVISIBLE);
                    flavourListRv.setAdapter(cupCakeFlavoursAdapter);
                    nullFlavourMsg.setVisibility(View.VISIBLE);
                }
            } else {
                Log.d("FlavourListOperations", "onCreate: Error!");
            }
        }
        return check;
    }

    private ArrayList<flavoursModelClass> generateFlavourList(String bakeryType) {
        databaseHelper dbObj = new databaseHelper(FlavourListOperations.this);

        ArrayList<flavoursModelClass> flavourList;
        if (bakeryType.equals("Cake")) {
            flavourList = dbObj.getCakeItems();
            return flavourList;
        } else if (bakeryType.equals("Cheese Cake")) {
            flavourList = dbObj.getCheeseCakeItems();
            return flavourList;
        } else if (bakeryType.equals("Cup Cake")) {
            flavourList = dbObj.getCupCakeItems();
            return flavourList;
        } else {
            Log.d("FlavourListOperations", "generateFlavourList: Error!");
        }

        return null;
    }

    // Code for selecting from spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String valFromSpinner = parent.getItemAtPosition(position).toString();
        bakery_Type = valFromSpinner;
        Log.d("FlavourListOperations", "onItemSelected: count-" + count);
        int check = count;
        Log.d("FlavourListOperations", "onItemSelected: check-" + check);
        count = adapterSettings(check, bakery_Type);
        Log.d("FlavourListOperations", "onItemSelected: bakeryItem-" + bakery_Type);
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
        startForResult.launch(intent);
    }

    private void adapterAddSetting(String bakeryType, String newFlavour) {
        databaseHelper dbObj = new databaseHelper(this);
        boolean success;

        if (bakeryType.equals("Cake")) {
            success = dbObj.insertCakeFlavour(newFlavour);
            if (success == true) {
                nullFlavourMsg.setVisibility(View.INVISIBLE);
                flavourListRv.setVisibility(View.VISIBLE);
                ArrayList<flavoursModelClass> newCakeFlavourList = new ArrayList<>();
                newCakeFlavourList = generateFlavourList("Cake");
                cakeFlavoursAdapter.adapterChange(newCakeFlavourList);
            }
        } else if (bakeryType.equals("Cheese Cake")) {
            success = dbObj.insertCheeseCakeFlavour(newFlavour);
            if (success == true) {
                nullFlavourMsg.setVisibility(View.INVISIBLE);
                flavourListRv.setVisibility(View.VISIBLE);
                ArrayList<flavoursModelClass> newCheeseCakeFlavourList = new ArrayList<>();
                newCheeseCakeFlavourList = generateFlavourList("Cheese Cake");
                cheeseCakeFlavoursAdapter.adapterChange(newCheeseCakeFlavourList);
            }
        } else if (bakeryType.equals("Cup Cake")) {
            success = dbObj.insertCupCakeFlavour(newFlavour);
            if (success == true) {
                flavourListRv.setVisibility(View.VISIBLE);
                nullFlavourMsg.setVisibility(View.INVISIBLE);
                ArrayList<flavoursModelClass> newCupCakeFlavourList = new ArrayList<>();
                newCupCakeFlavourList = generateFlavourList("Cup Cake");
                cupCakeFlavoursAdapter.adapterChange(newCupCakeFlavourList);
            }
        }
    }

    @Override
    public void applyText(String newFlavour) {
        adapterAddSetting(bakery_Type, newFlavour);
    }
}