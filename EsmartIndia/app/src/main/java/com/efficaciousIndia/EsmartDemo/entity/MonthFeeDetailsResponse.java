package com.efficaciousIndia.EsmartDemo.entity;

/**
 * Created by Rahul on 13,July,2020
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MonthFeeDetailsResponse {

    @SerializedName("MonthFeeDetails")
    @Expose
    private List<MonthFeeDetail> monthFeeDetails = null;

    public List<MonthFeeDetail> getMonthFeeDetails() {
        return monthFeeDetails;
    }

    public void setMonthFeeDetails(List<MonthFeeDetail> monthFeeDetails) {
        this.monthFeeDetails = monthFeeDetails;
    }
    public class MonthFeeDetail {

        @SerializedName("intFeemaster_id")
        @Expose
        private Integer intFeemasterId;
        @SerializedName("FeeName")
        @Expose
        private String feeName;
        @SerializedName("Month")
        @Expose
        private String month;
        @SerializedName("vchfee")
        @Expose
        private String vchfee;
        @SerializedName("LateFee")
        @Expose
        private String lateFee;
        @SerializedName("ConcessionAmt")
        @Expose
        private String concessionAmt;
        @SerializedName("NetAmt")
        @Expose
        private String netAmt;
        @SerializedName("feestartdate")
        @Expose
        private String feestartdate;

        public Integer getIntFeemasterId() {
            return intFeemasterId;
        }

        public void setIntFeemasterId(Integer intFeemasterId) {
            this.intFeemasterId = intFeemasterId;
        }

        public String getFeeName() {
            return feeName;
        }

        public void setFeeName(String feeName) {
            this.feeName = feeName;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getVchfee() {
            return vchfee;
        }

        public void setVchfee(String vchfee) {
            this.vchfee = vchfee;
        }

        public String getLateFee() {
            return lateFee;
        }

        public void setLateFee(String lateFee) {
            this.lateFee = lateFee;
        }

        public String getConcessionAmt() {
            return concessionAmt;
        }

        public void setConcessionAmt(String concessionAmt) {
            this.concessionAmt = concessionAmt;
        }

        public String getNetAmt() {
            return netAmt;
        }

        public void setNetAmt(String netAmt) {
            this.netAmt = netAmt;
        }

        public String getFeestartdate() {
            return feestartdate;
        }

        public void setFeestartdate(String feestartdate) {
            this.feestartdate = feestartdate;
        }

    }
}
