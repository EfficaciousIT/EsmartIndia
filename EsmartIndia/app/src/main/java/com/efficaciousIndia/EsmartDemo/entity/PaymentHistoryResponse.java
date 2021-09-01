package com.efficaciousIndia.EsmartDemo.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentHistoryResponse {
    @SerializedName("MonthWiseFee")
    @Expose
    private List<MonthWiseFee> monthWiseFee = null;

    public List<MonthWiseFee> getMonthWiseFee() {
        return this.monthWiseFee;
    }

    public void setMonthWiseFee(List<MonthWiseFee> monthWiseFee2) {
        this.monthWiseFee = monthWiseFee2;
    }

    public class MonthWiseFee {
        @SerializedName("intStudFee_id")
        @Expose
        private Integer intStudFeeId;
        @SerializedName("Month")
        @Expose
        private String month;
        @SerializedName("Receiptlink")
        @Expose
        private String receiptlink;
        @SerializedName("Status")
        @Expose
        private String status;
        @SerializedName("Sum")
        @Expose
        private Integer sum;

        public MonthWiseFee() {
        }

        public String getMonth() {
            return this.month;
        }

        public void setMonth(String month2) {
            this.month = month2;
        }

        public String getStatus() {
            return this.status;
        }

        public void setStatus(String status2) {
            this.status = status2;
        }

        public Integer getSum() {
            return this.sum;
        }

        public void setSum(Integer sum2) {
            this.sum = sum2;
        }

        public Integer getIntStudFeeId() {
            return this.intStudFeeId;
        }

        public void setIntStudFeeId(Integer intStudFeeId2) {
            this.intStudFeeId = intStudFeeId2;
        }

        public String getReceiptlink() {
            return this.receiptlink;
        }

        public void setReceiptlink(String receiptlink2) {
            this.receiptlink = receiptlink2;
        }
    }
}
