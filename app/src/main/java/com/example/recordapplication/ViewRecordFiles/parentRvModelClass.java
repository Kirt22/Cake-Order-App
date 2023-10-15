package com.example.recordapplication.ViewRecordFiles;

import java.util.ArrayList;

public class parentRvModelClass {
    String date;
    ArrayList<subRvModelClass> subRvModelClassArrayList;

    public parentRvModelClass(String date, ArrayList<subRvModelClass> subRvModelClassArrayList) {
        this.date = date;
        this.subRvModelClassArrayList = subRvModelClassArrayList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<subRvModelClass> getSubRvModelClassArrayList() {
        return subRvModelClassArrayList;
    }

    public void setSubRvModelClassArrayList(ArrayList<subRvModelClass> subRvModelClassArrayList) {
        this.subRvModelClassArrayList = subRvModelClassArrayList;
    }
}
