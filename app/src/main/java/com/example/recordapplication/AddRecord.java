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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class AddRecord extends AppCompatActivity {

    private EditText deliveryDateInput, customerPhoneNumberInput, cakePriceInput, cakeWeightInput, cakeMsgInput, themeDescription, deliveryTimeInput;
    private Button submitDetailsBtn;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchBtn;
    private CheckBox isThemeCheckBtn;
    private AutoCompleteTextView nameInput, cakeFlavourInput;
    private ArrayList<String> arrayList;
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

        // Auto Suggest feature for customer Name ( using database )
        databaseHelperObj = new databaseHelper(AddRecord.this);
        arrayList = databaseHelperObj.getName();
        ArrayAdapter<String> arrayAdapterForCusName = new ArrayAdapter<>(AddRecord.this, android.R.layout.simple_list_item_1, arrayList);
        nameInput.setAdapter(arrayAdapterForCusName);

        // Auto Suggest feature for cake Flavour ( using arrayList )
        String[] cakeFlavourList = getResources().getStringArray(R.array.cakeFlavourList);
        ArrayAdapter<String> arrayAdapterForCakeFlavour = new ArrayAdapter<String>(AddRecord.this, android.R.layout.simple_list_item_1, cakeFlavourList);
        cakeFlavourInput.setAdapter(arrayAdapterForCakeFlavour);

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

                long orderId = generateOrderId();
                Log.d("AddRecord", "onClick: orderId-" + orderId);
                String cusName = nameInput.getText().toString();
                long phNumber = Long.valueOf(customerPhoneNumberInput.getText().toString());
                int cakePrice = Integer.valueOf(cakePriceInput.getText().toString());
                String cakeFlavour = cakeFlavourInput.getText().toString();
                int cakeWeight = Integer.valueOf(cakeWeightInput.getText().toString());
                String cakeMsg = cakeMsgInput.getText().toString();
                Boolean isTheme = isThemeCheckBtn.isChecked();
                String themeCakeDesc = themeDescription.getText().toString();

                OrderDetailsModel orderDetailsModelObj;
                try {
                    orderDetailsModelObj = new OrderDetailsModel(orderId, cusName, phNumber, cakePrice, dateDb, timeDb, cakeFlavour, cakeWeight, cakeMsg, isTheme, themeCakeDesc);
                    Log.d("AddRecord", "onClick: input data:-" + orderDetailsModelObj.getCustomerName());
                    Toast.makeText(AddRecord.this, "Order Successfully added!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    orderDetailsModelObj = new OrderDetailsModel(0, null, 0, 0, null, null, null, 0, null, false, null);
                    Toast.makeText(AddRecord.this, "Filed Empty!", Toast.LENGTH_SHORT).show();
                }

                //OrderDetailsModel orderDetailsModelObj = new OrderDetailsModel(orderId, cusName, phNumber, cakePrice, dateDb, timeDb, cakeFlavour, cakeWeight, cakeMsg, isTheme, themeCakeDesc);
                //Log.d("AddRecord", "onClick: input data:-" + orderDetailsModelObj.getCakeMsg());
                //Toast.makeText(AddRecord.this, "Order Successfully added!", Toast.LENGTH_SHORT).show();


                try {
                    databaseHelper databaseHelperObj = new databaseHelper(AddRecord.this);
                    Boolean success = databaseHelperObj.insertItem(orderDetailsModelObj);
                    Log.d("MyTag", "onClick: InsertItem method success:" + success);
                } catch (Exception e) {
                    Toast.makeText(AddRecord.this, "Could not add record in DataBase!", Toast.LENGTH_SHORT).show();
                } finally {
                    databaseHelperObj.close();
                }
            }
        });


        // On clicking the switch button
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true) {

                    // This is for cake details
                    switchBtn.setText("Cake");

                    nameInput.setVisibility(View.INVISIBLE);
                    deliveryDateInput.setVisibility(View.INVISIBLE);
                    customerPhoneNumberInput.setVisibility(View.INVISIBLE);
                    cakePriceInput.setVisibility(View.INVISIBLE);
                    deliveryTimeInput.setVisibility(View.INVISIBLE);

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
                // This is for customer details
                } else {
                    switchBtn.setText("Customer");

                    nameInput.setVisibility(View.VISIBLE);
                    deliveryDateInput.setVisibility(View.VISIBLE);
                    customerPhoneNumberInput.setVisibility(View.VISIBLE);
                    cakePriceInput.setVisibility(View.VISIBLE);
                    deliveryTimeInput.setVisibility(View.VISIBLE);

                    cakeFlavourInput.setVisibility(View.INVISIBLE);
                    cakeWeightInput.setVisibility(View.INVISIBLE);
                    cakeMsgInput.setVisibility(View.INVISIBLE);
                    themeDescription.setVisibility(View.INVISIBLE);
                    isThemeCheckBtn.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private long generateOrderId() {
        long orderId;
        Random random = new Random();
        orderId = random.nextInt(200000001-100000001)-100000001;
        Cursor cursor = databaseHelperObj.getAllData();
        while(cursor.moveToNext()) {
            long temp = cursor.getLong(0);
            if(temp == orderId) {
                orderId = random.nextInt(200000001-100000001)-100000001;
                cursor.moveToFirst();
            }
        }
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

    }

}