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

public class ShowFullDetails extends AppCompatActivity {

    TextView OrderId;
    TextInputLayout customerName, deliveryDate, customerPhNumber, deliveryTime, cakePrice, cakeFlavour, cakeWeight, cakeMsg, themeCakeDescription, themeCakeStatus, bakeryType;
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
        EditText BakeryType = bakeryType.getEditText();

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
        BakeryType.setText(cursor.getString(11));
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
                bakeryType.setEndIconDrawable(R.drawable.baseline_sync_24);
            }
        });

        deleteOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = databaseHelperObj.deleteOrderByOrderId(orderId);
                if(success == true) {
                    Toast.makeText(ShowFullDetails.this, "Order Deleted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowFullDetails.this, "Error!", Toast.LENGTH_SHORT).show();
                }
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

        customerPhNumber.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = databaseHelperObj.updateCustomerPhoneNumber(orderId, customerPhNumber.getEditText().getText().toString());
                if(success == true) {
                    Toast.makeText(ShowFullDetails.this, "Phone Number, Successfully Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowFullDetails.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deliveryDate.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = databaseHelperObj.updateDeliveryDate(orderId, deliveryDate.getEditText().getText().toString());
                if(success == true) {
                    Toast.makeText(ShowFullDetails.this, "Delivery Date, Successfully Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowFullDetails.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deliveryTime.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = databaseHelperObj.updateDeliveryTime(orderId, deliveryTime.getEditText().getText().toString());
                if(success == true) {
                    Toast.makeText(ShowFullDetails.this, "Delivery Time, Successfully Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowFullDetails.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cakePrice.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = databaseHelperObj.updateItemPrice(orderId, cakePrice.getEditText().getText().toString());
                if(success == true) {
                    Toast.makeText(ShowFullDetails.this, "Item Price, Successfully Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowFullDetails.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cakeFlavour.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = databaseHelperObj.updateItemFlavour(orderId, cakeFlavour.getEditText().getText().toString());
                if(success == true) {
                    Toast.makeText(ShowFullDetails.this, "Item Flavour, Successfully Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowFullDetails.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cakeWeight.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = databaseHelperObj.updateItemWeight(orderId, cakeWeight.getEditText().getText().toString());
                if(success == true) {
                    Toast.makeText(ShowFullDetails.this, "Item Weight, Successfully Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowFullDetails.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cakeMsg.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = databaseHelperObj.updateItemMessage(orderId, cakeMsg.getEditText().getText().toString());
                if(success == true) {
                    Toast.makeText(ShowFullDetails.this, "Item Message, Successfully Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowFullDetails.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        themeCakeDescription.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = databaseHelperObj.updateThemeCakeDescription(orderId, themeCakeDescription.getEditText().getText().toString());
                if(success == true) {
                    Toast.makeText(ShowFullDetails.this, "Theme Cake Description, Successfully Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowFullDetails.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        themeCakeStatus.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = themeCakeStatus.getEditText().getText().toString();
                boolean success = true;
                if(status.equals("False") || status.equals("false")) {
                    success = databaseHelperObj.updateThemeCakeStatus(orderId, 0);
                    databaseHelperObj.updateThemeCakeDescription(orderId, "");
                } else if(status.equals("True") || status.equals("true")){
                    success = databaseHelperObj.updateThemeCakeStatus(orderId, 1);
                    themeCakeDescription.setVisibility(View.VISIBLE);
                }

                if(success == true) {
                    Toast.makeText(ShowFullDetails.this, "Theme Cake Status, Successfully Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowFullDetails.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bakeryType.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = databaseHelperObj.updateBakeryType(orderId, bakeryType.getEditText().getText().toString());
                if(success == true) {
                    Toast.makeText(ShowFullDetails.this, "Bakery Type, Successfully Updated!", Toast.LENGTH_SHORT).show();
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
        bakeryType.getEditText().setEnabled(editable);
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
        bakeryType.getEditText().setEnabled(editable);
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
        updateBtn = findViewById(R.id.updateOrderBtn);
        deleteOrderBtn = findViewById(R.id.deleteOrderBtn);
        OrderId = findViewById(R.id.orderId);
        bakeryType = findViewById(R.id.bakeryType);
    }
}