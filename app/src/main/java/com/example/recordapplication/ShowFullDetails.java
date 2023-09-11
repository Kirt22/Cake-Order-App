package com.example.recordapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ShowFullDetails extends AppCompatActivity {

    TextView OrderId;
    TextInputLayout customerName, deliveryDate, customerPhNumber, deliveryTime, cakePrice, cakeFlavour, cakeWeight, cakeMsg, themeCakeDescription, themeCakeStatus;
    Button updateBtn, deleteOrderBtn;
    databaseHelper databaseHelperObj = new databaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_full_details);

        initialiseViews();

        Intent intent = getIntent();
        long orderId = intent.getLongExtra("OrderId", 0);
        if(orderId == 0) {
            Log.e("ShowFullDetails", "onCreate() OrderId-" + orderId);
        }

        Cursor cursor = databaseHelperObj.getDataForOrderId(orderId);
        cursor.moveToFirst();

        EditText CusName = customerName.getEditText();
        EditText DelDate = deliveryDate.getEditText();
        EditText delTime = deliveryTime.getEditText();
        EditText PhNumber = customerPhNumber.getEditText();
        EditText CakeMsg = cakeMsg.getEditText();
        EditText cakeFla = cakeFlavour.getEditText();
        EditText CakWeight = cakeWeight.getEditText();
        EditText CakePrice = cakePrice.getEditText();
        EditText Description = themeCakeDescription.getEditText();
        EditText ThemeCakeStatus = themeCakeStatus.getEditText();

        CusName.setText(cursor.getString(1));
        OrderId.setText(String.valueOf(orderId));
        DelDate.setText(cursor.getString(3));
        delTime.setText(cursor.getString(4));
        PhNumber.setText(String.valueOf(cursor.getInt(2)));
        CakeMsg.setText(cursor.getString(8));
        Log.d("ShowFullDetails", "onCreate: CakeMsg-" + cursor.getString(8));
        cakeFla.setText(cursor.getString(6));
        CakWeight.setText(String.valueOf(cursor.getInt(7)));
        CakePrice.setText(String.valueOf(cursor.getInt(5)));
        int checked = cursor.getInt(9);
        if(checked == 1) {
            ThemeCakeStatus.setText("True");
            Description.setText(cursor.getString(10));
            themeCakeDescription.setVisibility(View.VISIBLE);
        } else {
            ThemeCakeStatus.setText("False");
        }

        setAllTextInputLayoutsNotClickable(false);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllTextInputLayoutsClickable(true);
                customerName.setEndIconDrawable(R.drawable.baseline_sync_24);
                customerPhNumber.setEndIconDrawable(R.drawable.baseline_sync_24);
                deliveryDate.setEndIconDrawable(R.drawable.baseline_sync_24);
                deliveryTime.setEndIconDrawable(R.drawable.baseline_sync_24);
                cakePrice.setEndIconDrawable(R.drawable.baseline_sync_24);
                cakeFlavour.setEndIconDrawable(R.drawable.baseline_sync_24);
                cakeWeight.setEndIconDrawable(R.drawable.baseline_sync_24);
                cakeMsg.setEndIconDrawable(R.drawable.baseline_sync_24);
                themeCakeDescription.setEndIconDrawable(R.drawable.baseline_sync_24);
                themeCakeStatus.setEndIconDrawable(R.drawable.baseline_sync_24);
            }
        });

        customerName.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ShowFullDetails", "onClick: clicked");
                boolean success = databaseHelperObj.updateCustomerName(orderId, customerName.getEditText().getText().toString());
                if(success == true) {
                    Toast.makeText(ShowFullDetails.this, "Customer Name, Successfully Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowFullDetails.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    void setAllTextInputLayoutsNotClickable(boolean editable) {
        customerName.getEditText().setEnabled(editable);
        customerPhNumber.getEditText().setEnabled(editable);
        deliveryDate.getEditText().setEnabled(editable);
        deliveryTime.getEditText().setEnabled(editable);
        cakePrice.getEditText().setEnabled(editable);
        cakeFlavour.getEditText().setEnabled(editable);
        cakeWeight.getEditText().setEnabled(editable);
        cakeMsg.getEditText().setEnabled(editable);
        themeCakeDescription.getEditText().setEnabled(editable);
        themeCakeStatus.getEditText().setEnabled(editable);
    }

    void setAllTextInputLayoutsClickable(boolean editable) {
        customerName.getEditText().setEnabled(editable);
        customerPhNumber.getEditText().setEnabled(editable);
        deliveryDate.getEditText().setEnabled(editable);
        deliveryTime.getEditText().setEnabled(editable);
        cakePrice.getEditText().setEnabled(editable);
        cakeFlavour.getEditText().setEnabled(editable);
        cakeWeight.getEditText().setEnabled(editable);
        cakeMsg.getEditText().setEnabled(editable);
        themeCakeDescription.getEditText().setEnabled(editable);
        themeCakeStatus.getEditText().setEnabled(editable);
    }

    private void initialiseViews() {
        customerName = findViewById(R.id.customerName);
        deliveryDate = findViewById(R.id.deliveryDate);
        customerPhNumber = findViewById(R.id.customerPhNumber);
        deliveryTime = findViewById(R.id.deliveryTime);
        cakePrice = findViewById(R.id.cakePrice);
        cakeFlavour = findViewById(R.id.cakeFlavour);
        cakeWeight = findViewById(R.id.cakeWeight);
        cakeMsg = findViewById(R.id.cakeMsg);
        themeCakeStatus = findViewById(R.id.themeCakeStatus);
        themeCakeDescription = findViewById(R.id.themeCakeDescription);
        updateBtn = findViewById(R.id.updateBtn);
        deleteOrderBtn = findViewById(R.id.deleteOrderBtn);
        OrderId = findViewById(R.id.orderId);
    }
}