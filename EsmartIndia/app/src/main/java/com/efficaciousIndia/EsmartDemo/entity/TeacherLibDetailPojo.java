package com.efficaciousIndia.EsmartDemo.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rahul on 27,May,2020
 */
public class TeacherLibDetailPojo {

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

    @SerializedName("intTeacherBook_assign_id")
    @Expose
    private Integer intTeacherBookAssignId;
    @SerializedName("vchDepartment_name")
    @Expose
    private String vchDepartmentName;
    @SerializedName("vchDesignation")
    @Expose
    private String vchDesignation;
    @SerializedName("intTeacher_id")
    @Expose
    private String intTeacherId;
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
    @SerializedName("StatusId")
    @Expose
    private Integer statusId;
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

    public Integer getIntTeacherBookAssignId() {
        return intTeacherBookAssignId;
    }

    public void setIntTeacherBookAssignId(Integer intTeacherBookAssignId) {
        this.intTeacherBookAssignId = intTeacherBookAssignId;
    }

    public String getVchDepartmentName() {
        return vchDepartmentName;
    }

    public void setVchDepartmentName(String vchDepartmentName) {
        this.vchDepartmentName = vchDepartmentName;
    }

    public String getVchDesignation() {
        return vchDesignation;
    }

    public void setVchDesignation(String vchDesignation) {
        this.vchDesignation = vchDesignation;
    }

    public String getIntTeacherId() {
        return intTeacherId;
    }

    public void setIntTeacherId(String intTeacherId) {
        this.intTeacherId = intTeacherId;
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

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
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

}



}
