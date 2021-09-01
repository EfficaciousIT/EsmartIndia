package com.efficaciousIndia.EsmartDemo.entity;

/**
 * Created by Rahul on 20,May,2020
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryDetailLibPojo {

    @SerializedName("LibraryDetail")
    @Expose
    private List<LibraryDetail> libraryDetail = null;

    public List<LibraryDetail> getLibraryDetail() {
        return libraryDetail;
    }

    public void setLibraryDetail(List<LibraryDetail> libraryDetail) {
        this.libraryDetail = libraryDetail;
    }


    public class LibraryDetail {

        @SerializedName("intCategory_id")
        @Expose
        private Integer intCategoryId;
        @SerializedName("vchCategory_name")
        @Expose
        private String vchCategoryName;
        @SerializedName("intschool_id")
        @Expose
        private Integer intschoolId;
        @SerializedName("bitActiveflg")
        @Expose
        private String bitActiveflg;

        public Integer getIntCategoryId() {
            return intCategoryId;
        }

        public void setIntCategoryId(Integer intCategoryId) {
            this.intCategoryId = intCategoryId;
        }

        public String getVchCategoryName() {
            return vchCategoryName;
        }

        public void setVchCategoryName(String vchCategoryName) {
            this.vchCategoryName = vchCategoryName;
        }

        public Integer getIntschoolId() {
            return intschoolId;
        }

        public void setIntschoolId(Integer intschoolId) {
            this.intschoolId = intschoolId;
        }

        public String getBitActiveflg() {
            return bitActiveflg;
        }

        public void setBitActiveflg(String bitActiveflg) {
            this.bitActiveflg = bitActiveflg;
        }

    }
}
