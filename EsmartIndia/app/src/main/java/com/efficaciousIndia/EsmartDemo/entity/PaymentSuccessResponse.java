package com.efficaciousIndia.EsmartDemo.entity;

/**
 * Created by Rahul on 17,July,2020
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentSuccessResponse {

    @SerializedName("PayFeeDetails")
    @Expose
    private List<PayFeeDetail> payFeeDetails = null;

    public List<PayFeeDetail> getPayFeeDetails() {
        return payFeeDetails;
    }

    public void setPayFeeDetails(List<PayFeeDetail> payFeeDetails) {
        this.payFeeDetails = payFeeDetails;
    }
    public class PayFeeDetail {

        @SerializedName("Status")
        @Expose
        private String status;

        public String getFeeId() {
            return FeeId;
        }

        public void setFeeId(String feeId) {
            FeeId = feeId;
        }

        @SerializedName("FeeId")
        @Expose
        private String FeeId;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
