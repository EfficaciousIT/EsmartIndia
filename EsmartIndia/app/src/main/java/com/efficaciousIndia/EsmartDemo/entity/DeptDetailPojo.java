package com.efficaciousIndia.EsmartDemo.entity;

/**
 * Created by Rahul on 27,May,2020
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeptDetailPojo {

    @SerializedName("OnlineTimetable")
    @Expose
    private List<DeptOnlineTimetable> onlineTimetable = null;

    public List<DeptOnlineTimetable> getOnlineTimetable() {
        return onlineTimetable;
    }

    public void setOnlineTimetable(List<DeptOnlineTimetable> onlineTimetable) {
        this.onlineTimetable = onlineTimetable;
    }



}
