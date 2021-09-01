package com.efficaciousIndia.EsmartDemo.entity;

/**
 * Created by Rahul on 24,May,2020
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AssignBookDetailLibPojo {

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

        @SerializedName("intBook_assign_id")
        @Expose
        private Integer intBookAssignId;
        @SerializedName("intRollNo")
        @Expose
        private String intRollNo;
        @SerializedName("intstandard_id")
        @Expose
        private String intstandardId;
        @SerializedName("intDivision_id")
        @Expose
        private String intDivisionId;
        @SerializedName("intStudent_id")
        @Expose
        private String intStudentId;
        @SerializedName("intBookDetails_id")
        @Expose
        private String intBookDetailsId;
        @SerializedName("intBookID")
        @Expose
        private Integer intBookID;
        @SerializedName("vchCategory_name")
        @Expose
        private String vchCategoryName;
        @SerializedName("vchBookLanguage_name")
        @Expose
        private String vchBookLanguageName;
        @SerializedName("intBookLanguage_id")
        @Expose
        private Integer intBookLanguageId;
        @SerializedName("dtAssigned_Date")
        @Expose
        private String dtAssignedDate;
        @SerializedName("dtReturn_date")
        @Expose
        private String dtReturnDate;
        @SerializedName("vchStatus")
        @Expose
        private String vchStatus;
        @SerializedName("dtExpectedReturnedDate")
        @Expose
        private String dtExpectedReturnedDate;

        public Integer getIntBookAssignId() {
            return intBookAssignId;
        }

        public void setIntBookAssignId(Integer intBookAssignId) {
            this.intBookAssignId = intBookAssignId;
        }

        public String getIntstandardId() {
            return intstandardId;
        }

        public void setIntstandardId(String intstandardId) {
            this.intstandardId = intstandardId;
        }

        public String getIntDivisionId() {
            return intDivisionId;
        }

        public void setIntDivisionId(String intDivisionId) {
            this.intDivisionId = intDivisionId;
        }

        public String getIntStudentId() {
            return intStudentId;
        }

        public void setIntStudentId(String intStudentId) {
            this.intStudentId = intStudentId;
        }

        public String getIntBookDetailsId() {
            return intBookDetailsId;
        }

        public void setIntBookDetailsId(String intBookDetailsId) {
            this.intBookDetailsId = intBookDetailsId;
        }

        public Integer getIntBookID() {
            return intBookID;
        }

        public void setIntBookID(Integer intBookID) {
            this.intBookID = intBookID;
        }

        public String getVchCategoryName() {
            return vchCategoryName;
        }

        public void setVchCategoryName(String vchCategoryName) {
            this.vchCategoryName = vchCategoryName;
        }

        public String getVchBookLanguageName() {
            return vchBookLanguageName;
        }

        public void setVchBookLanguageName(String vchBookLanguageName) {
            this.vchBookLanguageName = vchBookLanguageName;
        }

        public Integer getIntBookLanguageId() {
            return intBookLanguageId;
        }

        public void setIntBookLanguageId(Integer intBookLanguageId) {
            this.intBookLanguageId = intBookLanguageId;
        }

        public String getDtAssignedDate() {
            return dtAssignedDate;
        }

        public void setDtAssignedDate(String dtAssignedDate) {
            this.dtAssignedDate = dtAssignedDate;
        }

        public String getDtReturnDate() {
            return dtReturnDate;
        }

        public void setDtReturnDate(String dtReturnDate) {
            this.dtReturnDate = dtReturnDate;
        }

        public String getVchStatus() {
            return vchStatus;
        }

        public void setVchStatus(String vchStatus) {
            this.vchStatus = vchStatus;
        }

        public String getDtExpectedReturnedDate() {
            return dtExpectedReturnedDate;
        }

        public void setDtExpectedReturnedDate(String dtExpectedReturnedDate) {
            this.dtExpectedReturnedDate = dtExpectedReturnedDate;
        }

        public String getIntRollNo() {
            return intRollNo;
        }

        public void setIntRollNo(String intRollNo) {
            this.intRollNo = intRollNo;
        }
    }
}
