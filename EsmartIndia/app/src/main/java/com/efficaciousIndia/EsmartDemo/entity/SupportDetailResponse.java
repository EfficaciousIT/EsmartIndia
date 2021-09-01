package com.efficaciousIndia.EsmartDemo.entity;

/**
 * Created by Rahul on 13,July,2020
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SupportDetailResponse {

    @SerializedName("APKVersion")
    @Expose
    private List<APKVersion> aPKVersion = null;

    public List<APKVersion> getAPKVersion() {
        return aPKVersion;
    }

    public void setAPKVersion(List<APKVersion> aPKVersion) {
        this.aPKVersion = aPKVersion;
    }
    public class APKVersion {

        @SerializedName("int_id")
        @Expose
        private Integer intId;
        @SerializedName("Phone_Number")
        @Expose
        private String phoneNumber;
        @SerializedName("EmailID")
        @Expose
        private String emailID;
        @SerializedName("FromTime")
        @Expose
        private String fromTime;
        @SerializedName("ToTime")
        @Expose
        private String toTime;
        @SerializedName("ContactPersonName")
        @Expose
        private String contactPersonName;
        @SerializedName("intSchool_id")
        @Expose
        private Integer intSchoolId;

        public Integer getIntId() {
            return intId;
        }

        public void setIntId(Integer intId) {
            this.intId = intId;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmailID() {
            return emailID;
        }

        public void setEmailID(String emailID) {
            this.emailID = emailID;
        }

        public String getFromTime() {
            return fromTime;
        }

        public void setFromTime(String fromTime) {
            this.fromTime = fromTime;
        }

        public String getToTime() {
            return toTime;
        }

        public void setToTime(String toTime) {
            this.toTime = toTime;
        }

        public String getContactPersonName() {
            return contactPersonName;
        }

        public void setContactPersonName(String contactPersonName) {
            this.contactPersonName = contactPersonName;
        }

        public Integer getIntSchoolId() {
            return intSchoolId;
        }

        public void setIntSchoolId(Integer intSchoolId) {
            this.intSchoolId = intSchoolId;
        }

    }
}
