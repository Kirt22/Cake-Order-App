package com.example.recordapplication;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;

public class GenerateRequiredArrayList {

    public ArrayList<parentRvModelClass> generateArrayList(Context context) {

        ArrayList<parentRvModelClass> parentRvModelClassArrayList = new ArrayList<>();

        Cursor cursor = new databaseHelper(context).getDataForRV();
        try {
            cursor.moveToFirst();
            do {
                String date = cursor.getString(3);
                parentRvModelClass parentItem = new parentRvModelClass(date, setSubArrayList(date, context));
                parentRvModelClassArrayList.add(parentItem);
            } while (cursor.moveToNext());
        } catch (Exception e) {
            Log.e("ViewRecord", "onCreate: failed", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        parentRvModelClassArrayList = modifyArrayList(parentRvModelClassArrayList);

        return parentRvModelClassArrayList;
    }

    private ArrayList<parentRvModelClass> modifyArrayList(ArrayList<parentRvModelClass> parentRvModelClassArrayList) {

        HashSet<String> uniqueDates = new HashSet<>();
        ArrayList<parentRvModelClass> tempArrayList = new ArrayList<>();

        for (parentRvModelClass parentItem : parentRvModelClassArrayList) {
            String date = parentItem.getDate();
            if (uniqueDates.add(date)) {
                tempArrayList.add(parentItem);
            }
        }
        parentRvModelClassArrayList = tempArrayList;

        // Rearranging the parentRvModelClassArrayList in the desired format
        String currDate = getCurrDate();
        Log.d("ViewRecord", "onCreate: " + currDate);
        int currentIndex = findPosByDate(currDate, parentRvModelClassArrayList);
        Log.d("ViewRecord", "onCreate: " + currentIndex);
        // check before modification
        for (int i = 0; i < parentRvModelClassArrayList.size(); i++) {
            Log.d("ViewRecord", "onCreate: before modification" + parentRvModelClassArrayList.get(i).getDate());
        }

        ArrayList<parentRvModelClass> beforeList = new ArrayList<>();
        ArrayList<parentRvModelClass> afterList = new ArrayList<>();
        // Populating beforeList and afterList
        for (int i = 0; i < parentRvModelClassArrayList.size(); i++) {
            if (parentRvModelClassArrayList.get(i).getDate().compareTo(currDate) < 0) {
                beforeList.add(parentRvModelClassArrayList.get(i));
            } else if (parentRvModelClassArrayList.get(i).getDate().compareTo(currDate) > 0) {
                afterList.add(parentRvModelClassArrayList.get(i));
            }
        }

        // Sorting beforeList and afterList
        Collections.sort(beforeList, (b1, b2) -> b1.date.compareTo(b2.date));
        for (int i = 0; i < beforeList.size(); i++) {
            Log.d("ViewRecord", "onCreate: beforeList" + beforeList.get(i).getDate());
        }
        Collections.sort(afterList, (a1, a2) -> a1.date.compareTo(a2.date));
        for (int i = 0; i < afterList.size(); i++) {
            Log.d("ViewRecord", "onCreate: afterList" + afterList.get(i).getDate());
        }

        ArrayList<parentRvModelClass> tempArr = new ArrayList<>();

        // Concatenating beforeList, AfterList and the current date info in a tempArr
        for (int i = 0; i < beforeList.size(); i++) {
            tempArr.add(i, beforeList.get(i));
        }

        int currIdx = findPosByDate(currDate, parentRvModelClassArrayList);
        if (currIdx != -1) {
            tempArr.add(parentRvModelClassArrayList.get(currIdx));
        } else {
            Log.d("ViewRecord", "onCreate: Current date not found in parentRvModelClassArrayList");
            // If the current date does not exist, creating a new field in the parentRvModelClassArrayList that has current date but no data regarding it
            subRvModelClass tempSubObj = new subRvModelClass(null, null, null, null, 0, null);
            ArrayList<subRvModelClass> tempSubArrayList = new ArrayList<>();
            tempSubArrayList.add(tempSubObj);
            parentRvModelClass tempObj = new parentRvModelClass(currDate, tempSubArrayList);
            tempArr.add(tempObj);
        }
        for (int i = 0; i < afterList.size(); i++) {
            tempArr.add(afterList.get(i));
        }

        for (int i = 0; i < tempArr.size(); i++) {
            Log.d("ViewRecord", "onCreate: tempArr-" + tempArr.get(i).getDate());
        }

        return tempArr;
    }

    private ArrayList<subRvModelClass> setSubArrayList(String date, Context context) {
        Cursor cursor = new databaseHelper(context).getDataForRV();
        ArrayList<subRvModelClass> subItemArrayList = new ArrayList<>();
        try {
            cursor.moveToFirst();
            do {
                if (Objects.equals(date, cursor.getString(3))) {
                    subRvModelClass subItem = new subRvModelClass(
                            cursor.getString(1),
                            cursor.getString(6),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getLong(0),
                            cursor.getString(11)
                    );
                    subItemArrayList.add(subItem);
                }
            } while (cursor.moveToNext());
        } catch (Exception e) {
            Log.e("ViewRecord", "setSubArrayList: failed", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        for(int i=0; i<subItemArrayList.size(); i++) {
            Log.d("GenerateRequiredArrayList", "setSubArrayList: subItemArrayList-" + subItemArrayList.get(i).DeliveryDate);
        }

        return subItemArrayList;
    }

    private int findPosByDate(String currDate, ArrayList<parentRvModelClass> parentRvModelClassArrayList) {
        for (int i = 0; i < parentRvModelClassArrayList.size(); i++) {
            if (parentRvModelClassArrayList.get(i).getDate().equals(currDate)) {
                return i;
            }
        }
        return -1;
    }

    private String getCurrDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = Calendar.getInstance().getTime();
        return dateFormat.format(currentDate);
    }
}
