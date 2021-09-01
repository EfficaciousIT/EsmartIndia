package com.efficaciousIndia.EsmartDemo.entity;

/**
 * Created by Rahul on 20,May,2020
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SubjectDetailLibPojo {

    @SerializedName("LibraryDetail")
    @Expose
    private List<SubjLibraryDetail> libraryDetail = null;

    public List<SubjLibraryDetail> getLibraryDetail() {
        return libraryDetail;
    }

    public void setLibraryDetail(List<SubjLibraryDetail> libraryDetail) {
        this.libraryDetail = libraryDetail;
    }



}
