package com.example.recordapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class databaseHelper extends SQLiteOpenHelper {

    private static final String ORDER_RECORD_TABLE = "ORDER_RECORD";
    private static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    private static final String COLUMN_PHONE_NUMBER = "PHONE_NUMBER";
    private static final String COLUMN_CAKE_PRICE = "CAKE_PRICE";
    private static final String COLUMN_CAKE_FLAVOUR = "CAKE_FLAVOUR";
    private static final String COLUMN_CAKE_WEIGHT = "CAKE_WEIGHT";
    private static final String COLUMN_CAKE_MESSAGE = "CAKE_MESSAGE";
    private static final String COLUMN_IS_THEME_CAKE = "IS_THEME_CAKE";
    private static final String COLUMN_THEME_CAKE_DESCRIPTION = "THEME_CAKE_DESCRIPTION";
    private static final String COLUMN_DELIVERY_DATE = "DELIVERY_DATE";
    private static final String COLUMN_DELIVERY_TIME= "DELIVERY_TIME";
    private static final String COLUMN_ORDER_ID = "ORDER_ID";

    public databaseHelper(@Nullable Context context) {
        super(context,"orders.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase dbObj) {

        String SQLstatement = "CREATE TABLE " + ORDER_RECORD_TABLE + "( " + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY, "  + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_PHONE_NUMBER + " INTEGER, " + COLUMN_DELIVERY_DATE + " STRING, " + COLUMN_DELIVERY_TIME + " STRING, " + COLUMN_CAKE_PRICE + " INTEGER, " + COLUMN_CAKE_FLAVOUR + " TEXT, " + COLUMN_CAKE_WEIGHT + " INTEGER, " + COLUMN_CAKE_MESSAGE + " TEXT, " + COLUMN_IS_THEME_CAKE + " INTEGER, " + COLUMN_THEME_CAKE_DESCRIPTION + " INTEGER);" ;
        dbObj.execSQL(SQLstatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbObj, int i, int i1) {
        String SQLstatement = "DROP TABLE IF EXISTS " + ORDER_RECORD_TABLE;
        dbObj.execSQL(SQLstatement);
        onCreate(dbObj);
    }

    // This is a method to insert data from 'OrderDetailsModel' in the database
    public boolean insertItem(OrderDetailsModel orderDetailsModelObj) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CUSTOMER_NAME, orderDetailsModelObj.getCustomerName());
        cv.put(COLUMN_PHONE_NUMBER, orderDetailsModelObj.getPhNumber());
        cv.put(COLUMN_DELIVERY_DATE, orderDetailsModelObj.getDeliveryDate());
        cv.put(COLUMN_DELIVERY_TIME, orderDetailsModelObj.getDeliveryTime());
        cv.put(COLUMN_CAKE_PRICE, orderDetailsModelObj.getCakePrice());
        cv.put(COLUMN_CAKE_FLAVOUR, orderDetailsModelObj.getCakeFlavour());
        cv.put(COLUMN_CAKE_WEIGHT, orderDetailsModelObj.getCakeWeight());
        cv.put(COLUMN_CAKE_MESSAGE, orderDetailsModelObj.getCakeMsg());
        cv.put(COLUMN_IS_THEME_CAKE, orderDetailsModelObj.getThemeCake());
        cv.put(COLUMN_THEME_CAKE_DESCRIPTION, orderDetailsModelObj.getThemeCakeDescription());
        cv.put(COLUMN_ORDER_ID, orderDetailsModelObj.getOrderId());

        long insert = dbObj.insert(ORDER_RECORD_TABLE, null, cv);
        if(insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    // This method is used for the auto-suggest feature in add_records activity
    @SuppressLint("Range")
    public ArrayList<String> getName() {

        ArrayList<String> allName = new ArrayList<>();
        SQLiteDatabase dbObj = this.getReadableDatabase();

        Cursor cursor = dbObj.query(ORDER_RECORD_TABLE, null, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            allName.add(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME)));
            cursor.moveToNext();
        }
        dbObj.close();
        cursor.close();
        return allName;
    }

    public Cursor getDataForRV() {

        // for selecting the start and end date, to select the range in the query
        String startDate, endDate, currDate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        currDate = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DATE, -15);
        startDate = dateFormat.format(calendar.getTime());
        Log.d("databaseHelper", "getDataForRV: startDate in databaseHelper-" + startDate);
        calendar.add(Calendar.DATE, 31);
        endDate = dateFormat.format(calendar.getTime());
        Log.d("databaseHelper", "getDataForRV: endDate in databaseHelper-" + endDate);

        SQLiteDatabase dbObj = this.getReadableDatabase();
        String SQLQuery = " SELECT * FROM " + ORDER_RECORD_TABLE + " WHERE substr( " + COLUMN_DELIVERY_DATE + " , 1, 4) || '-' || substr( " + COLUMN_DELIVERY_DATE + " , 6, 2) || '-' || substr( " + COLUMN_DELIVERY_DATE + " , 9, 2) BETWEEN ? AND ?" ;
        //Cursor cursor = dbObj.rawQuery(SQLQuery, new String[] {startDate, endDate});
        String[] args = {startDate, endDate};
        Cursor cursor = dbObj.rawQuery(SQLQuery, args);
        if(cursor.moveToNext())
            Log.d("databaseHelper", "getDataForRV: validity of cursor-" + cursor.getCount());
        return cursor;
    }

    public void deleteAllData() {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        String SQLQuery = "DELETE FROM " + ORDER_RECORD_TABLE ;
        dbObj.execSQL(SQLQuery);
        dbObj.close();
    }

    public Cursor getAllData() {
        SQLiteDatabase dbObj = this.getReadableDatabase();
        String SQLQuery = "SELECT * FROM " + ORDER_RECORD_TABLE;
        Cursor cursor = dbObj.rawQuery(SQLQuery, null);
        return cursor;
    }

    public Cursor getDataForOrderId(long orderId) {
        SQLiteDatabase dbObj = this.getReadableDatabase();
        String[] args = {String.valueOf(orderId)};
        String SQLQuery = "SELECT * FROM " + ORDER_RECORD_TABLE + " WHERE " + COLUMN_ORDER_ID + " =? ";
        Cursor cursor = dbObj.rawQuery(SQLQuery, args);

        return cursor;
    }

    public boolean updateCustomerName(long orderId, String cusName) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        String[] args = {String.valueOf(orderId)};
        String SQLQuery = "UPDATE " + COLUMN_CUSTOMER_NAME + " WHERE " + COLUMN_ORDER_ID + " =?";
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CUSTOMER_NAME, cusName);
        int success = dbObj.update(ORDER_RECORD_TABLE, cv, COLUMN_ORDER_ID + " =? ", args);
        if (success >= 1) {
            return true;
        }
        return false;
    }
}
