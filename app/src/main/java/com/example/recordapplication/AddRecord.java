package com.example.recordapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class AddRecord extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText deliveryDateInput, customerPhoneNumberInput, cakePriceInput, cakeWeightInput, cakeMsgInput, themeDescription, deliveryTimeInput;
    private Button submitDetailsBtn;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchBtn;
    private CheckBox isThemeCheckBtn;
    private AutoCompleteTextView nameInput, cakeFlavourInput;
    private Spinner bakeryItemTypeSelector;
    private String BakeryType;
    private ArrayList<String> CakeArrayList, CheeseCakeArrayList, CupCakeArrayList, arrayList;
    databaseHelper databaseHelperObj;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private String date , dateDb, time, timeDb;
    private int hour, Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        initialiseViews();
        Log.d("AddRecord", "onCreate: Entered");
        databaseHelperObj = new databaseHelper(AddRecord.this);

        // Bakery type dialogBox selector
        String[] bakeryType = getResources().getStringArray(R.array.Bakery_Type_Array);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bakeryType);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bakeryItemTypeSelector.setAdapter(adapter);
        bakeryItemTypeSelector.setOnItemSelectedListener(this);

        // Auto Suggest feature for customer Name ( using database )
        arrayList = databaseHelperObj.getName();
        ArrayAdapter<String> arrayAdapterForCusName = new ArrayAdapter<>(AddRecord.this, android.R.layout.simple_list_item_1, arrayList);
        nameInput.setAdapter(arrayAdapterForCusName);

        // Auto Suggest feature for cake Flavour ( using arrayList )
        if(bakeryType.equals("Cake")) {
            Log.d("AddRecord", "onCreate: Auto Suggest bakeryType-" + bakeryType);
            CakeArrayList = databaseHelperObj.getCakeFlavour();
            ArrayAdapter<String> arrayAdapterForCakeFla = new ArrayAdapter<>(AddRecord.this, android.R.layout.simple_list_item_1, arrayList);
            cakeFlavourInput.setAdapter(arrayAdapterForCakeFla);
        } else if(bakeryType.equals("Cheese Cake")) {
            Log.d("AddRecord", "onCreate: Auto Suggest bakeryType-" + bakeryType);
            CheeseCakeArrayList = databaseHelperObj.getCakeFlavour();
            ArrayAdapter<String> arrayAdapterForCheeseCakeFla = new ArrayAdapter<>(AddRecord.this, android.R.layout.simple_list_item_1, arrayList);
            cakeFlavourInput.setAdapter(arrayAdapterForCheeseCakeFla);
        } else if(bakeryType.equals("Cup Cake")) {
            Log.d("AddRecord", "onCreate: Auto Suggest bakeryType-" + bakeryType);
            CheeseCakeArrayList = databaseHelperObj.getCakeFlavour();
            ArrayAdapter<String> arrayAdapterForCupCakeFla = new ArrayAdapter<>(AddRecord.this, android.R.layout.simple_list_item_1, arrayList);
            cakeFlavourInput.setAdapter(arrayAdapterForCupCakeFla);
        }

        // On clicking the Delivery date edtText
        deliveryDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar  calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat format1 = new SimpleDateFormat("yyyy/MM/dd");
                        dateDb = format.format(calendar.getTime());
                        date =  format1.format(calendar.getTime());
                        Log.d("AddRecord", "onDateSet: date- " + date);
                        Log.d("AddRecord", "onDateSet: date- " + dateDb);
                    }
                };

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(AddRecord.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();

                deliveryDateInput.setText(date);
            }
        });

        // On clicking the delivery Time edtTxt
        deliveryTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                Minute = c.get(Calendar.MINUTE);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar  calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
                        time = format.format(calendar.getTime());
                        timeDb = hourOfDay + ":" + minute;
                    }
                };

                timePickerDialog = new TimePickerDialog(AddRecord.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,timeSetListener, hour, Minute, false);
                timePickerDialog.setTitle("Select Time");
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();

                deliveryTimeInput.setText(time);
            }
        });

        // On clicking the submit button
        submitDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertItem();
            }
        });
    }

    private void insertItem() {
        long orderId = generateOrderId();

        if(BakeryType.equals("Cake")) {
            if(nameInput.getText().toString().trim().length() == 0 || customerPhoneNumberInput.getText().toString().trim().length() == 0
                    || cakePriceInput.getText().toString().trim().length() == 0 || cakeFlavourInput.getText().toString().trim().length() == 0
                    || cakeWeightInput.getText().toString().trim().length() == 0 || cakeMsgInput.getText().toString().trim().length() == 0
                    || dateDb.length() == 0 || timeDb.length() == 0) {
                Toast.makeText(this, "Some Field Empty!", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (BakeryType.equals("Cheese Cake")) {
            if(nameInput.getText().toString().trim().length() == 0 || customerPhoneNumberInput.getText().toString().trim().length() == 0
                    || cakePriceInput.getText().toString().trim().length() == 0 || cakeFlavourInput.getText().toString().trim().length() == 0
                    || cakeWeightInput.getText().toString().trim().length() == 0 || cakeMsgInput.getText().toString().trim().length() == 0
                    || dateDb.length() == 0 || timeDb.length() == 0) {
                Toast.makeText(this, "Some Field Empty!", Toast.LENGTH_SHORT).show();
                return;
            }
        } else if (BakeryType.equals("Cup Cake")) {
            if(nameInput.getText().toString().trim().length() == 0 || customerPhoneNumberInput.getText().toString().trim().length() == 0
                    || cakePriceInput.getText().toString().trim().length() == 0 || cakeFlavourInput.getText().toString().trim().length() == 0
                    || cakeWeightInput.getText().toString().trim().length() == 0 || dateDb.length() == 0 || timeDb.length() == 0) {
                Toast.makeText(this, "Some Field Empty!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        OrderDetailsModel orderDetailsModelObj = new OrderDetailsModel(orderId, nameInput.getText().toString(),
                Long.valueOf(customerPhoneNumberInput.getText().toString()), Integer.valueOf(cakePriceInput.getText().toString()),
                dateDb, timeDb, cakeFlavourInput.getText().toString(), Integer.valueOf(cakeWeightInput.getText().toString()), cakeMsgInput.getText().toString(),
                isThemeCheckBtn.isChecked(), themeDescription.getText().toString(), BakeryType);
        Boolean success = databaseHelperObj.insertItem(orderDetailsModelObj);
        if(success == true) {
            Toast.makeText(this, "Order Successfully Added!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error Occurred!", Toast.LENGTH_SHORT).show();
        }
        databaseHelperObj.close();

    }

    private long generateOrderId() {
        long orderId;
        Random random = new Random();
        orderId = random.nextInt(200000001-100000001)-100000001;
        Cursor cursor = databaseHelperObj.getAllData();
        if (cursor.moveToFirst() == false) {
            orderId = random.nextInt(200000001-100000001)-100000001;
            return Math.abs(orderId);
        }
        cursor.moveToFirst();
        do {
            long temp = cursor.getLong(0);
            if(temp == orderId) {
                orderId = random.nextInt(200000001-100000001)-100000001;
                cursor.moveToFirst();
            }
        } while(cursor.moveToNext());
        Log.d("AddRecord", "generateOrderId: orderId-" + Math.abs(orderId));
        return Math.abs(orderId);
    }

    private void initialiseViews() {
        nameInput = findViewById(R.id.nameInput);
        deliveryDateInput = findViewById(R.id.deliveryDateInput);
        deliveryTimeInput = findViewById(R.id.deliveryTimeInput);
        customerPhoneNumberInput = findViewById(R.id.customerPhoneNumberInput);
        submitDetailsBtn = findViewById(R.id.submitDetailsBtn);
        cakePriceInput = findViewById(R.id.cakePriceInput);
        switchBtn = findViewById(R.id.switchBtn);
        cakeFlavourInput = findViewById(R.id.cakeFlavourInput);
        cakeWeightInput = findViewById(R.id.cakeWeightInput);
        cakeMsgInput = findViewById(R.id.cakeMsgInput);
        themeDescription = findViewById(R.id.themeDescription);
        isThemeCheckBtn = findViewById(R.id.isThemeCheckBtn);
        bakeryItemTypeSelector = findViewById(R.id.bakeryItemTypeSelector);
    }

    private void setOn(int signifier) {
        if(signifier == 1) {

            nameInput.setVisibility(View.INVISIBLE);
            deliveryDateInput.setVisibility(View.INVISIBLE);
            customerPhoneNumberInput.setVisibility(View.INVISIBLE);
            cakePriceInput.setVisibility(View.INVISIBLE);
            deliveryTimeInput.setVisibility(View.INVISIBLE);

            cakeWeightInput.setHint("Cake Weight");
            cakeFlavourInput.setHint("Cake Flavour");
            cakeMsgInput.setHint("Cake Message");

            cakeFlavourInput.setVisibility(View.VISIBLE);
            cakeWeightInput.setVisibility(View.VISIBLE);
            cakeMsgInput.setVisibility(View.VISIBLE);
            isThemeCheckBtn.setVisibility(View.VISIBLE);
            // Theme cake description visibility feature
            if (isThemeCheckBtn.isChecked() == true) {
                themeDescription.setVisibility(View.VISIBLE);
            }
            isThemeCheckBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isTrue) {
                    if (isTrue == true) {
                        themeDescription.setVisibility(View.VISIBLE);
                    } else {
                        themeDescription.setVisibility(View.INVISIBLE);
                        themeDescription.setText("");
                    }
                }
            });
        } else if(signifier == 2) {

            nameInput.setVisibility(View.INVISIBLE);
            deliveryDateInput.setVisibility(View.INVISIBLE);
            customerPhoneNumberInput.setVisibility(View.INVISIBLE);
            cakePriceInput.setVisibility(View.INVISIBLE);
            deliveryTimeInput.setVisibility(View.INVISIBLE);

            cakeFlavourInput.setVisibility(View.VISIBLE);
            cakeWeightInput.setVisibility(View.VISIBLE);
            cakeMsgInput.setVisibility(View.VISIBLE);

            isThemeCheckBtn.setVisibility(View.INVISIBLE);

            cakeWeightInput.setHint("Cheese Cake Weight");
            cakeFlavourInput.setHint("Cheese Cake Flavour");
            cakeMsgInput.setHint("Cheese Cake Message");
        } else if(signifier == 3) {
            nameInput.setVisibility(View.INVISIBLE);
            deliveryDateInput.setVisibility(View.INVISIBLE);
            customerPhoneNumberInput.setVisibility(View.INVISIBLE);
            cakePriceInput.setVisibility(View.INVISIBLE);
            deliveryTimeInput.setVisibility(View.INVISIBLE);

            cakeWeightInput.setHint("Cup Cake Quantity");
            cakeFlavourInput.setHint("Cup Cake Flavour");
            isThemeCheckBtn.setText("Theme Cup Cake");

            cakeFlavourInput.setVisibility(View.VISIBLE);
            cakeWeightInput.setVisibility(View.VISIBLE);
            cakeMsgInput.setVisibility(View.INVISIBLE);
            isThemeCheckBtn.setVisibility(View.VISIBLE);
            // Theme cake description visibility feature
            if (isThemeCheckBtn.isChecked() == true) {
                themeDescription.setVisibility(View.VISIBLE);
            }
            isThemeCheckBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isTrue) {
                    if (isTrue == true) {
                        themeDescription.setVisibility(View.VISIBLE);
                    } else {
                        themeDescription.setVisibility(View.INVISIBLE);
                        themeDescription.setText("");
                    }
                }
            });
        }

    }

    private void setOff(int signifier) {
        if(signifier == 1) {

            nameInput.setVisibility(View.VISIBLE);
            deliveryDateInput.setVisibility(View.VISIBLE);
            customerPhoneNumberInput.setVisibility(View.VISIBLE);
            cakePriceInput.setVisibility(View.VISIBLE);
            deliveryTimeInput.setVisibility(View.VISIBLE);

            cakePriceInput.setHint("Cake Price");

            cakeFlavourInput.setVisibility(View.INVISIBLE);
            cakeWeightInput.setVisibility(View.INVISIBLE);
            cakeMsgInput.setVisibility(View.INVISIBLE);
            themeDescription.setVisibility(View.INVISIBLE);
            isThemeCheckBtn.setVisibility(View.INVISIBLE);
        } else if(signifier == 2) {

            nameInput.setVisibility(View.VISIBLE);
            deliveryDateInput.setVisibility(View.VISIBLE);
            customerPhoneNumberInput.setVisibility(View.VISIBLE);
            cakePriceInput.setVisibility(View.VISIBLE);
            deliveryTimeInput.setVisibility(View.VISIBLE);

            cakePriceInput.setHint("Cheese Cake Price");

            cakeFlavourInput.setVisibility(View.INVISIBLE);
            cakeWeightInput.setVisibility(View.INVISIBLE);
            cakeMsgInput.setVisibility(View.INVISIBLE);
            themeDescription.setVisibility(View.INVISIBLE);
            isThemeCheckBtn.setVisibility(View.INVISIBLE);
        } else if(signifier == 3) {
            nameInput.setVisibility(View.VISIBLE);
            deliveryDateInput.setVisibility(View.VISIBLE);
            customerPhoneNumberInput.setVisibility(View.VISIBLE);
            cakePriceInput.setVisibility(View.VISIBLE);
            deliveryTimeInput.setVisibility(View.VISIBLE);

            cakePriceInput.setHint("Cup Cake Price");

            cakeFlavourInput.setVisibility(View.INVISIBLE);
            cakeWeightInput.setVisibility(View.INVISIBLE);
            cakeMsgInput.setVisibility(View.INVISIBLE);
            themeDescription.setVisibility(View.INVISIBLE);
            isThemeCheckBtn.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.bakeryItemTypeSelector) {
            String valFromSpinner = parent.getItemAtPosition(position).toString();
            BakeryType = valFromSpinner;
            Log.d("AddRecord", "onItemSelected: Item-" + valFromSpinner);
            if(valFromSpinner.equals("Cake")) {
                Log.d("AddRecord", "onItemSelected: Entered Cake");
                if(switchBtn.isChecked() == false) {
                    switchBtn.setText("Customer");
                    setOff(1);
                } else {
                    switchBtn.setText("Cake");
                    setOn(1);
                }
                switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if(isChecked == false) {
                            switchBtn.setText("Customer");
                            setOff(1);
                        } else {
                            switchBtn.setText("Cake");
                            setOn(1);
                        }
                    }
                });
            } else if(valFromSpinner.equals("Cheese Cake")) {
                Log.d("AddRecord", "onItemSelected: Entered Cheese Cake");
                if(switchBtn.isChecked() == false) {
                    switchBtn.setText("Customer");
                    setOff(2);
                } else {
                    switchBtn.setText("Cheese Cake");
                    setOn(2);
                }
                switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if(isChecked == false) {
                            switchBtn.setText("Customer");
                            setOff(2);
                        } else {
                            switchBtn.setText("Cheese Cake");
                            setOn(2);
                        }
                    }
                });
            } else if(valFromSpinner.equals("Cup Cake")) {
                Log.d("AddRecord", "onItemSelected: Entered Cup Cake");
                if(switchBtn.isChecked() == false) {
                    switchBtn.setText("Customer");
                    setOff(3);
                } else {
                    switchBtn.setText("Cup Cake");
                    setOn(3);
                }
                switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if(isChecked == false) {
                            switchBtn.setText("Customer");
                            setOff(3);
                        } else {
                            switchBtn.setText("Cup Cake");
                            setOn(3);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}