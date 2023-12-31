package com.example.recordapplication.DatabaseHelperFiles;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.recordapplication.FlavourListOperationsFiles.flavoursModelClass;
import com.example.recordapplication.AddRecordFiles.OrderDetailsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class databaseHelper extends SQLiteOpenHelper {

    private static final String ORDER_RECORD_TABLE = "ORDER_RECORD";
    private static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    private static final String COLUMN_PHONE_NUMBER = "PHONE_NUMBER";
    private static final String COLUMN_ITEM_PRICE = "ITEM_PRICE";
    private static final String COLUMN_ITEM_FLAVOUR = "ITEM_FLAVOUR";
    private static final String COLUMN_ITEM_WEIGHT = "ITEM_WEIGHT";
    private static final String COLUMN_ITEM_MESSAGE = "ITEM_MESSAGE";
    private static final String COLUMN_IS_THEME_CAKE = "IS_THEME_CAKE";
    private static final String COLUMN_THEME_CAKE_DESCRIPTION = "THEME_CAKE_DESCRIPTION";
    private static final String COLUMN_DELIVERY_DATE = "DELIVERY_DATE";
    private static final String COLUMN_DELIVERY_TIME= "DELIVERY_TIME";
    private static final String COLUMN_ORDER_ID = "ORDER_ID";
    public static final String COLUMN_BAKERY_TYPE = "BAKERY_TYPE";

    public static final String CAKE_FLAVOUR_TABLE = "CAKE_FLAVOUR";
    public static final String COLUMN_CAKE_ID = "CAKE_ID";
    public static final String COLUMN_CAKE_FLAVOUR = "CAKE_FLAVOUR";

    public static final String CHEESE_CAKE_FLAVOUR_TABLE = "CHEESE_CAKE_FLAVOUR";
    public static final String COLUMN_CHEESE_CAKE_ID = "CHEESE_CAKE_ID";
    public static final String COLUMN_CHEESE_CAKE_FLAVOUR = "CHEESE_CAKE_FLAVOUR";

    public static final String CUP_CAKE_FLAVOUR_TABLE = "CUP_CAKE_FLAVOUR";
    public static final String COLUMN_CUP_CAKE_ID = "CUP_CAKE_ID";
    public static final String COLUMN_CUP_CAKE_FLAVOUR = "CUP_CAKE_FLAVOUR";


    public databaseHelper(@Nullable Context context) {
        super(context,"orders.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase dbObj) {

        String SQLOrderTable = "CREATE TABLE " + ORDER_RECORD_TABLE + "( " + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY, "  + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_PHONE_NUMBER + " INTEGER, " + COLUMN_DELIVERY_DATE + " STRING, " + COLUMN_DELIVERY_TIME + " STRING, " + COLUMN_ITEM_PRICE + " INTEGER, " + COLUMN_ITEM_FLAVOUR + " TEXT, " + COLUMN_ITEM_WEIGHT + " INTEGER, " + COLUMN_ITEM_MESSAGE + " TEXT, " + COLUMN_IS_THEME_CAKE + " INTEGER, " + COLUMN_THEME_CAKE_DESCRIPTION + " INTEGER, " + COLUMN_BAKERY_TYPE + " TEXT);" ;
        dbObj.execSQL(SQLOrderTable);

        String SQLCakeFlavoursTable = "CREATE TABLE " + CAKE_FLAVOUR_TABLE + "( " + COLUMN_CAKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CAKE_FLAVOUR + " TEXT);";
        dbObj.execSQL(SQLCakeFlavoursTable);

        String SQLCheeseCakeFlavourTable = "CREATE TABLE " + CHEESE_CAKE_FLAVOUR_TABLE + "( " + COLUMN_CHEESE_CAKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CHEESE_CAKE_FLAVOUR + " TEXT);";
        dbObj.execSQL(SQLCheeseCakeFlavourTable);

        String SQLCupCakeFlavourTable = "CREATE TABLE " + CUP_CAKE_FLAVOUR_TABLE + "( " + COLUMN_CUP_CAKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUP_CAKE_FLAVOUR + " TEXT);";
        dbObj.execSQL(SQLCupCakeFlavourTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dbObj, int i, int i1) {
        String SQLstatement = "DROP TABLE IF EXISTS " + ORDER_RECORD_TABLE;
        dbObj.execSQL(SQLstatement);
        onCreate(dbObj);
    }

    public boolean insertCakeFlavour(String cakeFlavour) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CAKE_FLAVOUR, cakeFlavour);

        long insert = dbObj.insert(CAKE_FLAVOUR_TABLE, null, cv);
        if(insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertCheeseCakeFlavour(String CheeseCakeFlavour) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CHEESE_CAKE_FLAVOUR, CheeseCakeFlavour);

        long insert = dbObj.insert(CHEESE_CAKE_FLAVOUR_TABLE, null, cv);
        if(insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertCupCakeFlavour(String CupCakeFlavour) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CUP_CAKE_FLAVOUR, CupCakeFlavour);

        long insert = dbObj.insert(CUP_CAKE_FLAVOUR_TABLE, null, cv);
        if(insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    // This is a method to insert data from 'OrderDetailsModel' in the database
    public boolean insertItem(OrderDetailsModel orderDetailsModelObj) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CUSTOMER_NAME, orderDetailsModelObj.getCustomerName());
        cv.put(COLUMN_PHONE_NUMBER, orderDetailsModelObj.getPhNumber());
        cv.put(COLUMN_DELIVERY_DATE, orderDetailsModelObj.getDeliveryDate());
        cv.put(COLUMN_DELIVERY_TIME, orderDetailsModelObj.getDeliveryTime());
        cv.put(COLUMN_ITEM_PRICE, orderDetailsModelObj.getCakePrice());
        cv.put(COLUMN_ITEM_FLAVOUR, orderDetailsModelObj.getCakeFlavour());
        cv.put(COLUMN_ITEM_WEIGHT, orderDetailsModelObj.getCakeWeight());
        cv.put(COLUMN_ITEM_MESSAGE, orderDetailsModelObj.getCakeMsg());
        cv.put(COLUMN_IS_THEME_CAKE, orderDetailsModelObj.getThemeCake());
        cv.put(COLUMN_THEME_CAKE_DESCRIPTION, orderDetailsModelObj.getThemeCakeDescription());
        cv.put(COLUMN_ORDER_ID, orderDetailsModelObj.getOrderId());
        cv.put(COLUMN_BAKERY_TYPE, orderDetailsModelObj.getBakeryType());

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
        return allName;
    }

    @SuppressLint("Range")
    public ArrayList<String> getNumber() {
        ArrayList<String> allNumber = new ArrayList<>();
        SQLiteDatabase dbObj = this.getReadableDatabase();

        Cursor cursor = dbObj.query(ORDER_RECORD_TABLE, null, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            allNumber.add(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER)));
            cursor.moveToNext();
        }
        dbObj.close();
        return allNumber;
    }

    public Cursor getDataForRV() {

        // for selecting the start and end date, to select the range in the query
        String startDate, endDate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        calendar.add(Calendar.DATE, -15);
        startDate = dateFormat.format(calendar.getTime());
        Log.d("databaseHelper", "getDataForRV: startDate in databaseHelper-" + startDate);

        calendar.add(Calendar.DATE, 31);
        endDate = dateFormat.format(calendar.getTime());
        Log.d("databaseHelper", "getDataForRV: endDate in databaseHelper-" + endDate);

        SQLiteDatabase dbObj = this.getReadableDatabase();
        String SQLQuery = " SELECT * FROM " + ORDER_RECORD_TABLE + " WHERE substr( " + COLUMN_DELIVERY_DATE + " , 1, 4) || '-' || substr( " + COLUMN_DELIVERY_DATE + " , 6, 2) || '-' || substr( " + COLUMN_DELIVERY_DATE + " , 9, 2) BETWEEN ? AND ?" ;
        String[] args = {startDate, endDate};
        Cursor cursor = dbObj.rawQuery(SQLQuery, args);
        if(cursor.moveToNext())
            Log.d("databaseHelper", "getDataForRV: cursor success-" + cursor.moveToNext());
        cursor.moveToFirst();
        return cursor;
    }

    public Cursor getDataForSearchDateRV(String searchDate) {
        String startDate, endDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date date = dateFormat.parse(searchDate); // Parse the searchDate
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date); // Set the calendar to the searchDate

            calendar.add(Calendar.DATE, -15); // Calculate startDate
            startDate = dateFormat.format(calendar.getTime());
            Log.d("databaseHelper", "getDataForSearchDateRV: startDate in databaseHelper-" + startDate);

            calendar.add(Calendar.DATE, 31); // Calculate endDate
            endDate = dateFormat.format(calendar.getTime());
            Log.d("databaseHelper", "getDataForSearchDateRV: endDate in databaseHelper-" + endDate);

            SQLiteDatabase dbObj = this.getReadableDatabase();
            String SQLQuery = " SELECT * FROM " + ORDER_RECORD_TABLE + " WHERE substr( " + COLUMN_DELIVERY_DATE + " , 1, 4) || '-' || substr( " + COLUMN_DELIVERY_DATE + " , 6, 2) || '-' || substr( " + COLUMN_DELIVERY_DATE + " , 9, 2) BETWEEN ? AND ?" ;
            String[] args = {startDate, endDate};
            Cursor cursor = dbObj.rawQuery(SQLQuery, args);

            cursor.moveToFirst();

            return cursor;
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the parsing exception here, e.g., return an error Cursor.
            return null;
        }
    }

    public Cursor getAllDataRv() {
        SQLiteDatabase dbObj = this.getReadableDatabase();
        String SQLQuery = " SELECT * FROM " + ORDER_RECORD_TABLE;
        Cursor cursor = dbObj.rawQuery(SQLQuery, null);
        if(cursor.moveToNext())
            Log.d("databaseHelper", "getDataForRV: cursor success-" + cursor.moveToNext());
        cursor.moveToFirst();
        return cursor;
    }

    // Used for generating OrderId
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
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CUSTOMER_NAME, cusName);
        int success = dbObj.update(ORDER_RECORD_TABLE, cv, COLUMN_ORDER_ID + " =? ", args);
        if (success >= 1) {
            return true;
        }
        return false;
    }

    public boolean updateCustomerPhoneNumber(long orderId, String cusPhoneNumber) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        String[] args = {String.valueOf(orderId)};
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CUSTOMER_NAME, cusPhoneNumber);
        int success = dbObj.update(ORDER_RECORD_TABLE, cv, COLUMN_ORDER_ID + " =? ", args);
        if (success >= 1) {
            return true;
        }
        return false;
    }

    public boolean updateDeliveryDate(long orderId, String deliveryDate) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        String[] args = {String.valueOf(orderId)};
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DELIVERY_DATE, deliveryDate);
        int success = dbObj.update(ORDER_RECORD_TABLE, cv, COLUMN_ORDER_ID + " =? ", args);
        if (success >= 1) {
            return true;
        }
        return false;
    }

    public boolean updateDeliveryTime(long orderId, String deliveryTime) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        String[] args = {String.valueOf(orderId)};
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DELIVERY_TIME, deliveryTime);
        int success = dbObj.update(ORDER_RECORD_TABLE, cv, COLUMN_ORDER_ID + " =? ", args);
        if (success >= 1) {
            return true;
        }
        return false;
    }

    public boolean updateItemPrice(long orderId, String itemPrice) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        String[] args = {String.valueOf(orderId)};
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ITEM_PRICE, itemPrice);
        int success = dbObj.update(ORDER_RECORD_TABLE, cv, COLUMN_ORDER_ID + " =? ", args);
        if (success >= 1) {
            return true;
        }
        return false;
    }

    public boolean updateItemFlavour(long orderId, String itemFlavour) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        String[] args = {String.valueOf(orderId)};
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ITEM_FLAVOUR, itemFlavour);
        int success = dbObj.update(ORDER_RECORD_TABLE, cv, COLUMN_ORDER_ID + " =? ", args);
        if (success >= 1) {
            return true;
        }
        return false;
    }

    public boolean updateItemWeight(long orderId, String itemWeight) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        String[] args = {String.valueOf(orderId)};
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ITEM_WEIGHT, itemWeight);
        int success = dbObj.update(ORDER_RECORD_TABLE, cv, COLUMN_ORDER_ID + " =? ", args);
        if (success >= 1) {
            return true;
        }
        return false;
    }

    public boolean updateItemMessage(long orderId, String itemMessage) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        String[] args = {String.valueOf(orderId)};
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ITEM_MESSAGE, itemMessage);
        int success = dbObj.update(ORDER_RECORD_TABLE, cv, COLUMN_ORDER_ID + " =? ", args);
        if (success >= 1) {
            return true;
        }
        return false;
    }

    public boolean updateThemeCakeDescription(long orderId, String themeCakeDescription) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        String[] args = {String.valueOf(orderId)};
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_THEME_CAKE_DESCRIPTION, themeCakeDescription);
        int success = dbObj.update(ORDER_RECORD_TABLE, cv, COLUMN_ORDER_ID + " =? ", args);
        if (success >= 1) {
            return true;
        }
        return false;
    }

    public boolean updateThemeCakeStatus(long orderId, int themeCakeStatus) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        String[] args = {String.valueOf(orderId)};
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_IS_THEME_CAKE, themeCakeStatus);
        int success = dbObj.update(ORDER_RECORD_TABLE, cv, COLUMN_ORDER_ID + " =? ", args);
        if (success >= 1) {
            return true;
        }
        return false;
    }

    public boolean updateBakeryType(long orderId, String BakeryType) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        String[] args = {String.valueOf(orderId)};
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BAKERY_TYPE, BakeryType);
        int success = dbObj.update(ORDER_RECORD_TABLE, cv, COLUMN_ORDER_ID + " =? ", args);
        if (success >= 1) {
            return true;
        }
        return false;
    }

    public boolean deleteOrderByOrderId(long orderId) {
        SQLiteDatabase dbObj = this.getWritableDatabase();
        String[] args = {String.valueOf(orderId)};
        int success = dbObj.delete(ORDER_RECORD_TABLE, COLUMN_ORDER_ID + " =? ", args);
        if(success >= 1) {
            return true;
        }
        return false;
    }

    @SuppressLint("Range")
    public ArrayList<flavoursModelClass> getCakeItems() {
        SQLiteDatabase dbObj = this.getReadableDatabase();

        String SQLQuery = "SELECT * FROM " + CAKE_FLAVOUR_TABLE;
        Cursor cursor = dbObj.rawQuery(SQLQuery, null);

        ArrayList<flavoursModelClass> flavoursModelClassArrayList = new ArrayList<>();

        if(cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();
        do {
            flavoursModelClass flavoursModelClassObj = new flavoursModelClass(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_CAKE_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CAKE_FLAVOUR))
            );
            flavoursModelClassArrayList.add(flavoursModelClassObj);
        } while(cursor.moveToNext());
        return flavoursModelClassArrayList;
    }

    @SuppressLint("Range")
    public ArrayList<flavoursModelClass> getCheeseCakeItems() {
        SQLiteDatabase dbObj = this.getReadableDatabase();
        ArrayList<flavoursModelClass> CheesecakeFlavour = new ArrayList<>();

        String SQLQuery = "SELECT * FROM " + CHEESE_CAKE_FLAVOUR_TABLE;
        Cursor cursor = dbObj.rawQuery(SQLQuery, null);

        if(cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();
        do {
            flavoursModelClass flavoursModelClassObj = new flavoursModelClass(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_CHEESE_CAKE_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CHEESE_CAKE_FLAVOUR))
            );
            CheesecakeFlavour.add(flavoursModelClassObj);
            cursor.moveToNext();
        } while(!cursor.isAfterLast());
        return CheesecakeFlavour;
    }

    @SuppressLint("Range")
    public ArrayList<flavoursModelClass> getCupCakeItems() {
        SQLiteDatabase dbObj = this.getReadableDatabase();
        ArrayList<flavoursModelClass> CupcakeFlavour = new ArrayList<>();

        String SQLQuery = "SELECT * FROM " + CUP_CAKE_FLAVOUR_TABLE;
        Cursor cursor = dbObj.rawQuery(SQLQuery, null);

        if(cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();
        do {
            flavoursModelClass flavoursModelClassObj = new flavoursModelClass(
                    cursor.getInt(cursor.getColumnIndex(COLUMN_CUP_CAKE_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CUP_CAKE_FLAVOUR))
            );
            CupcakeFlavour.add(flavoursModelClassObj);
            cursor.moveToNext();
        } while(!cursor.isAfterLast());
        return CupcakeFlavour;
    }

    public Cursor getFlavourForIdAndBakeryType(int id, String bakeryType) {
        SQLiteDatabase dbObj = this.getReadableDatabase();
        Cursor cursor = null;
        Log.d("dbheler", "getFlavourForIdAndBakeryType: entered");
        if(bakeryType.equals("Cake")) {
            String[] args = {String.valueOf(id)};
            String SQLQuery = "SELECT * FROM " + CAKE_FLAVOUR_TABLE + " WHERE " + COLUMN_CAKE_ID + " =? ";
            cursor = dbObj.rawQuery(SQLQuery, args);
            Log.d("dbhelper", "getFlavourForIdAndBakeryType: selected Cake");
        }
        if(bakeryType.equals("Cheese Cake")) {
            String[] args = {String.valueOf(id)};
            String SQLQuery = "SELECT * FROM " + CHEESE_CAKE_FLAVOUR_TABLE + " WHERE " + COLUMN_CHEESE_CAKE_ID + " =? ";
            cursor = dbObj.rawQuery(SQLQuery, args);
            Log.d("dbhelper", "getFlavourForIdAndBakeryType: selected Cheese Cake");
        }
        if(bakeryType.equals("Cup Cake")) {
            String[] args = {String.valueOf(id)};
            String SQLQuery = "SELECT * FROM " + CUP_CAKE_FLAVOUR_TABLE + " WHERE " + COLUMN_CUP_CAKE_ID + " =? ";
            cursor = dbObj.rawQuery(SQLQuery, args);
            Log.d("dbhelper", "getFlavourForIdAndBakeryType: selected Cup Cake");
        }

        return cursor;
    }

    public boolean updateFlavour(int id, String bakeryType, String newFlavour) {
        SQLiteDatabase dbObj = this.getWritableDatabase();

        if(bakeryType.equals("Cake")) {
            String[] args = {String.valueOf(id)};
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_CAKE_FLAVOUR, newFlavour);
            int success = dbObj.update(CAKE_FLAVOUR_TABLE, cv, COLUMN_CAKE_ID + " =? ", args);
            if (success >= 1) {
                return true;
            }
        } else if(bakeryType.equals("Cheese Cake")) {
            String[] args = {String.valueOf(id)};
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_CHEESE_CAKE_FLAVOUR, newFlavour);
            int success = dbObj.update(CHEESE_CAKE_FLAVOUR_TABLE, cv, COLUMN_CHEESE_CAKE_ID + " =? ", args);
            if (success >= 1) {
                return true;
            }
        } else if(bakeryType.equals("Cup Cake")) {
            String[] args = {String.valueOf(id)};
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_CUP_CAKE_FLAVOUR, newFlavour);
            int success = dbObj.update(CUP_CAKE_FLAVOUR_TABLE, cv, COLUMN_CUP_CAKE_ID + " =? ", args);
            if (success >= 1) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteFlavour(int id, String bakeryType) {
        SQLiteDatabase dbObj = this.getWritableDatabase();

        if(bakeryType.equals("Cake")) {
            String[] args = {String.valueOf(id)};
            int success = dbObj.delete(CAKE_FLAVOUR_TABLE, COLUMN_CAKE_ID + " =? ", args);
            if(success >= 1) {
                return true;
            }
        } else if(bakeryType.equals("Cheese Cake")) {
            String[] args = {String.valueOf(id)};
            int success = dbObj.delete(CHEESE_CAKE_FLAVOUR_TABLE, COLUMN_CHEESE_CAKE_ID + " =? ", args);
            if(success >= 1) {
                return true;
            }
        } else if (bakeryType.equals("Cup Cake")) {
            String[] args = {String.valueOf(id)};
            int success = dbObj.delete(CUP_CAKE_FLAVOUR_TABLE, COLUMN_CUP_CAKE_ID + " =? ", args);
            if(success >= 1) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("Range")
    public ArrayList<String> getCakeFlavour() {
        ArrayList<String> cakeFlavourList = new ArrayList<>();
        SQLiteDatabase dbObj = this.getReadableDatabase();

        Cursor cursor = dbObj.query(CAKE_FLAVOUR_TABLE, null, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            cakeFlavourList.add(cursor.getString(cursor.getColumnIndex(COLUMN_CAKE_FLAVOUR)));
            cursor.moveToNext();
        }

        return cakeFlavourList;
    }

    @SuppressLint("Range")
    public ArrayList<String> getCheeseCakeFlavour() {
        ArrayList<String> cheeseCakeFlavourList = new ArrayList<>();
        SQLiteDatabase dbObj = this.getReadableDatabase();

        Cursor cursor = dbObj.query(CHEESE_CAKE_FLAVOUR_TABLE, null, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            cheeseCakeFlavourList.add(cursor.getString(cursor.getColumnIndex(COLUMN_CHEESE_CAKE_FLAVOUR)));
            cursor.moveToNext();
        }
        return cheeseCakeFlavourList;
    }

    @SuppressLint("Range")
    public ArrayList<String> getCupCakeFlavour() {
        ArrayList<String> cupCakeFlavourList = new ArrayList<>();
        SQLiteDatabase dbObj = this.getReadableDatabase();

        Cursor cursor = dbObj.query(CUP_CAKE_FLAVOUR_TABLE, null, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            cupCakeFlavourList.add(cursor.getString(cursor.getColumnIndex(COLUMN_CUP_CAKE_FLAVOUR)));
            cursor.moveToNext();
        }

        return cupCakeFlavourList;
    }
}
