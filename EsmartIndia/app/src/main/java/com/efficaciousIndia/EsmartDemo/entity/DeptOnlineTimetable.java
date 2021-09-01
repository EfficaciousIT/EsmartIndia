package com.efficaciousIndia.EsmartDemo.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rahul on 31,May,2020
 */
public class DeptOnlineTimetable {

    @SerializedName("intDepartment")
    @Expose
    private Integer intDepartment;
    @SerializedName("vchDepartment_name")
    @Expose
    private String vchDepartmentName;

    public Integer getIntDepartment() {
        return intDepartment;
    }

    public void setIntDepartment(Integer intDepartment) {
        this.intDepartment = intDepartment;
    }

    public String getVchDepartmentName() {
        return vchDepartmentName;
    }

    public void setVchDepartmentName(String vchDepartmentName) {
        this.vchDepartmentName = vchDepartmentName;
    }

    @Override
    public String toString() {
        return vchDepartmentName;
    }

    public DeptOnlineTimetable(Integer intDepartment, String vchDepartmentName) {
        this.intDepartment = intDepartment;
        this.vchDepartmentName = vchDepartmentName;
    }
}
